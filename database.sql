CREATE DATABASE IF NOT EXISTS resume_db;
USE resume_db;

ALTER TABLE resume_history MODIFY COLUMN feedback LONGTEXT;

ALTER TABLE resume_history ADD COLUMN email VARCHAR(255);

ALTER TABLE resume_history ADD COLUMN skills TEXT;

ALTER TABLE resume_history ADD COLUMN ats_score INT DEFAULT 0;

ALTER TABLE resume_history ADD COLUMN keyword_score INT DEFAULT 0;

ALTER TABLE resume_history ADD COLUMN format_score INT DEFAULT 0;

ALTER TABLE resume_history ADD COLUMN experience_score INT DEFAULT 0;

ALTER TABLE resume_history ADD COLUMN jd_match_score INT DEFAULT 0;

ALTER TABLE resume_history ADD COLUMN verdict VARCHAR(50);

ALTER TABLE resume_history ADD COLUMN summary TEXT;

ALTER TABLE resume_history ADD COLUMN strengths TEXT;

ALTER TABLE resume_history ADD COLUMN weaknesses TEXT;

ALTER TABLE resume_history ADD COLUMN missing_keywords TEXT;

ALTER TABLE resume_history ADD COLUMN found_keywords TEXT;

ALTER TABLE resume_history ADD COLUMN sections_json TEXT;

ALTER TABLE resume_history ADD COLUMN rewrite_suggestions LONGTEXT;

ALTER TABLE resume_history ADD COLUMN immediate_actions TEXT;

ALTER TABLE resume_history ADD COLUMN strategic_actions TEXT;

ALTER TABLE resume_history ADD COLUMN ats_issues TEXT;

ALTER TABLE resume_history ADD COLUMN red_flags TEXT;

DESCRIBE resume_history;
SELECT * FROM resume_history;