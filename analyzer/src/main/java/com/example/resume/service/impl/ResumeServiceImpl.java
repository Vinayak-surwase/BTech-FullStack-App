package com.example.resume.service.impl;

import com.example.resume.entity.Resume;
import com.example.resume.repository.ResumeRepository;
import com.example.resume.service.GroqService;
import com.example.resume.service.ResumeService;

import org.apache.tika.Tika;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository repo;

    @Autowired
    private GroqService groqService;

    private final Tika tika = new Tika();

    @Override
    public Resume uploadResume(String studentName,
                               String role,
                               String email,
                               String jobDescription,
                               MultipartFile file) throws Exception {

        // ==============================
        // STEP 1: EXTRACT FILE TEXT
        // ==============================
        System.out.println("=================================");
        System.out.println(" STEP 1: Extracting Resume Text ");
        System.out.println("=================================");

        String content;

        try (InputStream inputStream = file.getInputStream()) {

            content = tika.parseToString(inputStream);
        }

        if (content == null || content.isBlank()) {

            throw new Exception("Resume file is empty or unreadable.");
        }

        // ==============================
        // STEP 2: AI ANALYSIS
        // ==============================
        System.out.println("=================================");
        System.out.println(" STEP 2: Requesting AI Analysis ");
        System.out.println("=================================");

        String rawResponse =
                groqService.analyzeResume(
                        content,
                        role,
                        jobDescription
                );

        // ==============================
        // CLEAN JSON RESPONSE
        // ==============================
        String cleanedJson = rawResponse.trim();

        if (cleanedJson.contains("```")) {

            cleanedJson = cleanedJson
                    .replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();
        }

        System.out.println("=================================");
        System.out.println(" CLEANED AI RESPONSE ");
        System.out.println("=================================");
        System.out.println(cleanedJson);

        // ==============================
        // STEP 3: SAVE DATA
        // ==============================
        System.out.println("=================================");
        System.out.println(" STEP 3: Saving To Database ");
        System.out.println("=================================");

        Resume resume = new Resume();

        resume.setStudentName(studentName);
        resume.setRole(role);
        resume.setEmail(email);

        resume.setFileName(file.getOriginalFilename());
        resume.setFileContentType(file.getContentType());

        resume.setCvFile(file.getBytes());

        resume.setUploadTime(LocalDateTime.now());

        // Save Full AI Response
        resume.setFeedback(cleanedJson);

        try {

            JSONObject json = new JSONObject(cleanedJson);

            // ==============================
            // BASIC FIELDS
            // ==============================
            resume.setScore(
                    json.optInt(
                            "overall_score",
                            json.optInt("score", 0)
                    )
            );

            resume.setVerdict(
                    json.optString(
                            "verdict",
                            "Processed"
                    )
            );

            resume.setSummary(
                    json.optString(
                            "summary",
                            "Resume analyzed successfully."
                    )
            );

            // ==============================
            // OPTIONAL PROFESSIONAL FIELDS
            // ==============================
            resume.setStrengths(
                    json.optString("strengths", "")
            );

            resume.setWeaknesses(
                    json.optString("weaknesses", "")
            );

            resume.setSkills(
                    json.optString("skills", "")
            );

            resume.setAtsScore(
                    json.optInt("ats_score", 0)
            );

            resume.setKeywordScore(
                    json.optInt("keyword_score", 0)
            );

            resume.setFormatScore(
                    json.optInt("format_score", 0)
            );

            resume.setExperienceScore(
                    json.optInt("experience_score", 0)
            );

            resume.setJdMatchScore(
                    json.optInt("jd_match_score", 0)
            );

        } catch (Exception e) {

            System.err.println("=================================");
            System.err.println(" JSON PARSE ERROR ");
            System.err.println("=================================");
            System.err.println(e.getMessage());

            resume.setScore(0);

            resume.setVerdict("JSON Parse Error");

            resume.setSummary(
                    "Failed to parse AI response."
            );
        }

        // ==============================
        // SAVE & RETURN
        // ==============================
        Resume savedResume = repo.save(resume);

        System.out.println("=================================");
        System.out.println(" RESUME SAVED SUCCESSFULLY ");
        System.out.println("=================================");

        return savedResume;
    }

    @Override
    public List<Resume> getAllResumes() {

        return repo.findAll();
    }
}