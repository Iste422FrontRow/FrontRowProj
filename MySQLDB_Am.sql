CREATE DATABASE MySQLDB_Am;
USE MySQLDB_Am;
CREATE TABLE STUDENT (
	StudentSSN VARCHAR(1),
	StudentName VARCHAR(1),
);

CREATE TABLE FACULTY (
	FacSSN VARCHAR(1),
	FacultyName VARCHAR(1),
CONSTRAINT FACULTY_PK PRIMARY KEY (FacSSN)
);

CREATE TABLE COURSES (
	Grade VARCHAR(1),
	Number VARCHAR(1),
);


