CREATE DATABASE MySQLDB;
USE MySQLDB;
CREATE TABLE STUDENT (
	StudentSSN VARCHAR(1),
	StudentName VARCHAR(1),
);

CREATE TABLE COURSES (
	Grade VARCHAR(1),
	Number VARCHAR(1),
);

CREATE TABLE FACULTY (
	FacultyName VARCHAR(1),
	FacSSN VARCHAR(1),
CONSTRAINT FACULTY_FK1 FOREIGN KEY(FacultyName) REFERENCES COURSES(Grade),
CONSTRAINT FACULTY_FK2 FOREIGN KEY(FacSSN) REFERENCES COURSES(Grade)
);


