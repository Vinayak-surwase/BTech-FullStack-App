package com.example.resume.service;

import com.example.resume.config.GroqConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GroqService {

    @Autowired
    private GroqConfig config;

    public String analyzeResume(String resumeText, String role, String jobDescription) {
        System.out.println("=================================");
        System.out.println(" STEP 2: Requesting AI Analysis ");
        System.out.println("=================================");

        String prompt = buildPrompt(resumeText, role, jobDescription);

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.groq.com/openai/v1/chat/completions";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(config.getApiKey());

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "llama-3.3-70b-versatile");
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 4096);

            JSONArray messages = new JSONArray();
            JSONObject systemMsg = new JSONObject();
            systemMsg.put("role", "system");
            systemMsg.put("content", "You are an expert resume analyzer. Return ONLY valid JSON, no markdown, no extra text.");
            messages.put(systemMsg);

            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            userMsg.put("content", prompt);
            messages.put(userMsg);

            requestBody.put("messages", messages);

            HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

            System.out.println("=================================");
            System.out.println(" STEP 2: Calling GROQ API ");
            System.out.println("=================================");

            String response = restTemplate.postForObject(url, entity, String.class);

            System.out.println("=================================");
            System.out.println(" GROQ RESPONSE RECEIVED ");
            System.out.println("=================================");
            System.out.println(response);

            JSONObject responseJson = new JSONObject(response);
            String content = responseJson
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            System.out.println("=================================");
            System.out.println(" RAW AI RESPONSE ");
            System.out.println("=================================");
            System.out.println(content);

            // Clean and return
            String cleaned = content.trim()
                    .replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();

            System.out.println("=================================");
            System.out.println(" CLEANED AI RESPONSE ");
            System.out.println("=================================");
            System.out.println(cleaned);

            return cleaned;

        } catch (Exception e) {
            e.printStackTrace();
            return getDefaultResponse();
        }
    }

    private String buildPrompt(String resumeText, String role, String jobDescription) {
        String roleContext = getRoleContext(role);
        
        return """
                Analyze this resume for a %s position.
                
                Resume Text:
                %s
                
                Job Description (if provided):
                %s
                
                Role Requirements:
                %s
                
                Return ONLY a JSON object with this EXACT structure (no markdown, no extra text):
                {
                  "overall_score": 75,
                  "ats_score": 80,
                  "keyword_score": 70,
                  "format_score": 85,
                  "experience_score": 65,
                  "jd_match_score": 75,
                  "verdict": "Good",
                  "summary": "Strong technical skills with room for improvement in quantified achievements",
                  "strengths": "Strong Java and Spring Boot expertise||Good project structure||Clear technical skills",
                  "weaknesses": "Lacks quantified achievements||Missing LinkedIn/GitHub links||Could improve action verbs",
                  "missing_keywords": "Microservices||Docker||Kubernetes||CI/CD||AWS||Unit Testing",
                  "found_keywords": "Java||Spring Boot||REST API||MySQL||Git",
                  "sections_json": "{\\"has_summary\\":true,\\"has_skills\\":true,\\"has_experience\\":true,\\"has_education\\":true,\\"has_certifications\\":false,\\"has_projects\\":true,\\"has_linkedin\\":false,\\"has_github\\":false,\\"has_quantified_achievements\\":false,\\"has_action_verbs\\":true}",
                  "rewrite_suggestions": "[{\\"original\\":\\"Worked on backend development\\",\\"improved\\":\\"Architected and developed 15+ RESTful APIs serving 10K+ daily requests, reducing response time by 40%% through query optimization\\"},{\\"original\\":\\"Used Spring Boot for projects\\",\\"improved\\":\\"Built microservices architecture using Spring Boot, implementing Circuit Breaker pattern and reducing system downtime by 60%%\\"}]",
                  "immediate_actions": "Add quantified metrics to all achievements||Add LinkedIn and GitHub profile URLs||Rewrite bullet points with action verbs||Add 2-3 line professional summary",
                  "strategic_actions": "Build 2-3 portfolio projects with live demos||Obtain AWS/Spring certification||Contribute to open source projects||Write technical blog posts",
                  "ats_issues": "Missing keywords: Docker, Kubernetes||No LinkedIn/GitHub links||Inconsistent date formatting||Skills not grouped by category",
                  "red_flags": ""
                }
                
                CRITICAL RULES:
                - Use || as separator for arrays (not commas in text)
                - sections_json must be valid escaped JSON string
                - rewrite_suggestions must be valid JSON array
                - All scores 0-100
                - verdict: Excellent/Good/Average/Fair/Poor
                - Return ONLY the JSON object, nothing else
                """.formatted(role, resumeText, jobDescription.isEmpty() ? "Not provided" : jobDescription, roleContext);
    }

    private String getRoleContext(String role) {
        return switch (role.toLowerCase()) {
            case "frontend" -> "React, Angular, Vue.js, TypeScript, HTML5, CSS3, Responsive Design, UI/UX, REST APIs";
            case "backend" -> "Java, Spring Boot, Node.js, Python, REST APIs, Microservices, Databases, SQL, NoSQL, Redis";
            case "fullstack" -> "Frontend + Backend + DevOps: React, Angular, Java, Spring Boot, Node.js, Docker, AWS, CI/CD";
            case "data-analyst" -> "SQL, Python, Excel, Power BI, Tableau, Statistics, Data Visualization, A/B Testing";
            case "data-scientist" -> "Python, R, Machine Learning, TensorFlow, PyTorch, Statistics, SQL, Data Mining";
            case "devops" -> "Docker, Kubernetes, Jenkins, GitLab CI, AWS, Azure, Terraform, Ansible, Linux";
            case "cloud-engineer" -> "AWS, Azure, GCP, Terraform, CloudFormation, Networking, Security, Cost Optimization";
            default -> "Programming, Problem Solving, Communication, Teamwork, Agile, Git";
        };
    }

    private String getDefaultResponse() {
        return """
                {
                  "overall_score": 50,
                  "ats_score": 50,
                  "keyword_score": 50,
                  "format_score": 50,
                  "experience_score": 50,
                  "jd_match_score": 50,
                  "verdict": "Fair",
                  "summary": "Analysis completed with limited data",
                  "strengths": "Resume uploaded successfully",
                  "weaknesses": "Unable to fully analyze resume",
                  "missing_keywords": "",
                  "found_keywords": "",
                  "sections_json": "{\\"has_summary\\":false,\\"has_skills\\":false,\\"has_experience\\":false,\\"has_education\\":false,\\"has_certifications\\":false,\\"has_projects\\":false,\\"has_linkedin\\":false,\\"has_github\\":false,\\"has_quantified_achievements\\":false,\\"has_action_verbs\\":false}",
                  "rewrite_suggestions": "[]",
                  "immediate_actions": "Review resume format||Add contact information||List key skills",
                  "strategic_actions": "Build portfolio projects||Get relevant certifications||Network on LinkedIn",
                  "ats_issues": "Resume needs restructuring",
                  "red_flags": ""
                }
                """;
    }
}