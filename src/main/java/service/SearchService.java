package service;

import grade.GradeManager;
import model.Student;
import student.StudentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchService {

    public List<Student> searchById(StudentManager sm, String id) {
        List<Student> result = new ArrayList<>();
        Student s = sm.findStudent(id);
        if (s != null) result.add(s);
        return result;
    }

    public List<Student> searchByName(StudentManager sm, String partialName) {
        List<Student> result = new ArrayList<>();
        String query = partialName.toLowerCase(Locale.ROOT);
        for (Student s : sm.getAllStudents()) {
            if (s.getName().toLowerCase(Locale.ROOT).contains(query)) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Student> searchByGradeRange(StudentManager sm, GradeManager gm, double min, double max) {
        List<Student> result = new ArrayList<>();
        for (Student s : sm.getAllStudents()) {
            double avg = s.calculateAverageGrade(gm);
            if (avg >= min && avg <= max) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Student> searchByType(StudentManager sm, String type) {
        List<Student> result = new ArrayList<>();
        for (Student s : sm.getAllStudents()) {
            if (s.getStudentType().equalsIgnoreCase(type)) {
                result.add(s);
            }
        }
        return result;
    }
}
