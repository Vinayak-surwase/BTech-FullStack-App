package com.example.resume.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.resume.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}