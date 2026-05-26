package com.example.resume.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resume_history")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "role")
    private String role;

    @Column(name = "email")
    private String email;

    @Column(name = "score")
    private Integer score;

    // Individual scores
    @Column(name = "ats_score")
    private Integer atsScore;

    @Column(name = "keyword_score")
    private Integer keywordScore;

    @Column(name = "format_score")
    private Integer formatScore;

    @Column(name = "experience_score")
    private Integer experienceScore;

    @Column(name = "jd_match_score")
    private Integer jdMatchScore;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_content_type")
    private String fileContentType;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

    @Lob
    @Column(name = "cv_file", columnDefinition = "LONGBLOB")
    private byte[] cvFile;

    @Lob
    @Column(name = "feedback", columnDefinition = "LONGTEXT")
    private String feedback;

    @Column(name = "skills", columnDefinition = "TEXT")
    private String skills;

    @Column(name = "verdict")
    private String verdict;

    @Lob
    @Column(name = "strengths", columnDefinition = "TEXT")
    private String strengths;

    @Lob
    @Column(name = "weaknesses", columnDefinition = "TEXT")
    private String weaknesses;

    @Lob
    @Column(name = "missing_keywords", columnDefinition = "TEXT")
    private String missingKeywords;

    @Lob
    @Column(name = "found_keywords", columnDefinition = "TEXT")
    private String foundKeywords;

    @Lob
    @Column(name = "sections_json", columnDefinition = "TEXT")
    private String sectionsJson;

    @Lob
    @Column(name = "rewrite_suggestions", columnDefinition = "LONGTEXT")
    private String rewriteSuggestions;

    @Lob
    @Column(name = "immediate_actions", columnDefinition = "TEXT")
    private String immediateActions;

    @Lob
    @Column(name = "strategic_actions", columnDefinition = "TEXT")
    private String strategicActions;

    @Lob
    @Column(name = "ats_issues", columnDefinition = "TEXT")
    private String atsIssues;

    @Lob
    @Column(name = "red_flags", columnDefinition = "TEXT")
    private String redFlags;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    // ===== GETTERS & SETTERS =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public Integer getAtsScore() { return atsScore; }
    public void setAtsScore(Integer atsScore) { this.atsScore = atsScore; }

    public Integer getKeywordScore() { return keywordScore; }
    public void setKeywordScore(Integer keywordScore) { this.keywordScore = keywordScore; }

    public Integer getFormatScore() { return formatScore; }
    public void setFormatScore(Integer formatScore) { this.formatScore = formatScore; }

    public Integer getExperienceScore() { return experienceScore; }
    public void setExperienceScore(Integer experienceScore) { this.experienceScore = experienceScore; }

    public Integer getJdMatchScore() { return jdMatchScore; }
    public void setJdMatchScore(Integer jdMatchScore) { this.jdMatchScore = jdMatchScore; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFileContentType() { return fileContentType; }
    public void setFileContentType(String fileContentType) { this.fileContentType = fileContentType; }

    public LocalDateTime getUploadTime() { return uploadTime; }
    public void setUploadTime(LocalDateTime uploadTime) { this.uploadTime = uploadTime; }

    public byte[] getCvFile() { return cvFile; }
    public void setCvFile(byte[] cvFile) { this.cvFile = cvFile; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getVerdict() { return verdict; }
    public void setVerdict(String verdict) { this.verdict = verdict; }

    public String getStrengths() { return strengths; }
    public void setStrengths(String strengths) { this.strengths = strengths; }

    public String getWeaknesses() { return weaknesses; }
    public void setWeaknesses(String weaknesses) { this.weaknesses = weaknesses; }

    public String getMissingKeywords() { return missingKeywords; }
    public void setMissingKeywords(String missingKeywords) { this.missingKeywords = missingKeywords; }

    public String getFoundKeywords() { return foundKeywords; }
    public void setFoundKeywords(String foundKeywords) { this.foundKeywords = foundKeywords; }

    public String getSectionsJson() { return sectionsJson; }
    public void setSectionsJson(String sectionsJson) { this.sectionsJson = sectionsJson; }

    public String getRewriteSuggestions() { return rewriteSuggestions; }
    public void setRewriteSuggestions(String rewriteSuggestions) { this.rewriteSuggestions = rewriteSuggestions; }

    public String getImmediateActions() { return immediateActions; }
    public void setImmediateActions(String immediateActions) { this.immediateActions = immediateActions; }

    public String getStrategicActions() { return strategicActions; }
    public void setStrategicActions(String strategicActions) { this.strategicActions = strategicActions; }

    public String getAtsIssues() { return atsIssues; }
    public void setAtsIssues(String atsIssues) { this.atsIssues = atsIssues; }

    public String getRedFlags() { return redFlags; }
    public void setRedFlags(String redFlags) { this.redFlags = redFlags; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}