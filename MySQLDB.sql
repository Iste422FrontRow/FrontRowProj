CREATE DATABASE MySQLDB;
USE MySQLDB;
CREATE TABLE STUDENT (
	StudentSSN VARCHAR(35),
	StudentName VARCHAR(35) NOT NULL,
CONSTRAINT STUDENT_PK PRIMARY KEY (StudentSSN)
);

CREATE TABLE FACULTY (
	FacultyName VARCHAR(1),
	FacSSN VARCHAR(1),
);

CREATE TABLE COURSES (
	Grade DOUBLE,
	Number INT,
);


