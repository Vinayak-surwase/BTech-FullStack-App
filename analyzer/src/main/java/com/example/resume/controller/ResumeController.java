package com.example.resume.controller;

import com.example.resume.entity.Resume;
import com.example.resume.service.impl.ResumeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "http://localhost:4200") // safer than "*"
public class ResumeController {

    @Autowired
    private ResumeServiceImpl service;

    // ==============================
    // UPLOAD + ANALYZE RESUME
    // ==============================
    @PostMapping("/upload")
    public ResponseEntity<Resume> uploadResume(
            @RequestParam("studentName") String studentName,
            @RequestParam("role") String role,
            @RequestParam("email") String email,
            @RequestParam(value = "jobDescription", required = false, defaultValue = "") String jobDescription,
            @RequestParam("file") MultipartFile file
    ) {

        try {
            Resume resume = service.uploadResume(
                    studentName,
                    role,
                    email,
                    jobDescription,
                    file
            );

            return ResponseEntity.ok(resume);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // ==============================
    // GET ALL RESUMES
    // ==============================
    @GetMapping("/all")
    public ResponseEntity<List<Resume>> getAllResumes() {
        return ResponseEntity.ok(service.getAllResumes());
    }
}