package com.example.resume.service;

import com.example.resume.entity.Resume;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ResumeService {
    Resume uploadResume(String studentName, String role, String email,
                        String jobDescription, MultipartFile file) throws Exception;
    List<Resume> getAllResumes();
}