-- ------------------------------
-- MySQL Quiz Database Setup
-- ------------------------------

-- Step 1: Start MySQL (if using CLI)
-- Open CMD and run:
-- mysql -u root -p

-- Step 2: Create and use the database
CREATE DATABASE IF NOT EXISTS java_quizdb;
USE java_quizdb;

-- Step 3: Create the questions table
CREATE TABLE IF NOT EXISTS questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    category VARCHAR(100),             -- e.g., GK, Programming
    question_text TEXT NOT NULL,       -- actual question
    option_a VARCHAR(255),
    option_b VARCHAR(255),
    option_c VARCHAR(255),
    option_d VARCHAR(255),
    correct_option VARCHAR(255)        -- full correct answer text (not just A/B/C/D)
);

-- Step 4: Load CSV data from MySQL secure upload folder
-- NOTE:
-- Ensure your file is copied to:
-- C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/quiz_questions.csv

-- Also, check if secure_file_priv is enabled:
-- SHOW VARIABLES LIKE 'secure_file_priv';

-- Step 5: Load the quiz questions into the table
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/quiz_questions.csv'
INTO TABLE questions
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(category, question_text, option_a, option_b, option_c, option_d, correct_option);
