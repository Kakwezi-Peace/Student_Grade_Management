
# Student Grade Management System – README
# Overview

The Student Grade Management System is a Java console application that helps manage students, subjects, and grades. It allows you to:

Add students (Regular or Honors).

Record grades for core and elective subjects.

View all students and their details.

Generate grade reports with averages and pass/fail status.

Check honors eligibility for honors students.

This project demonstrates object‑oriented programming concepts such as:

Inheritance (RegularStudent and HonorsStudent extend Student)

Encapsulation (private fields with getters/setters)

Composition (Student has Grade, Grade has Subject)

Polymorphism (different behavior for Regular vs Honors students)

#  Project Structure
Code
src/
 ├── Main.java              # Entry point with menu and flows
 ├── Student.java           # Base class for all students
 ├── RegularStudent.java    # Subclass with passing grade = 50%
 ├── HonorsStudent.java     # Subclass with passing grade = 60% + honors check
 ├── Subject.java           # Base class for subjects
 ├── CoreSubject.java       # Core subject (Math, English, Science)
 ├── ElectiveSubject.java   # Elective subject (Music, Art, PE)
 ├── Grade.java             # Represents a grade for a student in a subject
 ├── StudentManager.java    # Handles adding, finding, and listing students
 ├── GradeManager.java      # Handles recording grades and calculating averages

 # How to Run
Clone the repository:

bash
git clone https://github.com/Kakwezi-Peace/Student_Grade_Management.git
Navigate into the project folder:

bash
cd Student_Grade_Management/src
Compile the project:

bash
javac *.java
Run the program:

bash
java Main
# Features
Add Student Enter student details and choose type (Regular or Honors).

View Students Displays all students with their IDs and details.

Record Grade Select a student, choose subject type (Core/Elective), and record grade.

View Grade Report Shows averages, pass/fail status, and honors eligibility.

 Example Flow
Code
==== STUDENT GRADE MANAGEMENT ====
1. Add Student
2. View Students
3. Record Grade
4. View Grade Report
5. Exit
Add a student → system generates unique ID (STU001, STU002, …).

Record grade → confirm before saving.

View report → shows averages and status.

# Concepts Demonstrated
Inheritance: RegularStudent and HonorsStudent inherit from Student.

Encapsulation: Student details are private, accessed via methods.

Composition: Student has grades, Grade has subjects.

Polymorphism: isPassing() behaves differently for Regular vs Honors students.

# Notes
Passing grade:

Regular Student → 50%

Honors Student → 60% + honors eligibility check

Grades must be between 0 and 100.

Honors eligibility requires average ≥ 80% across all subjects.
