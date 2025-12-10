package services;

import java.util.List;
import java.util.stream.Collectors;

public class SearchService {
    public <Student> List<Student> searchByName(List<Student> students, String name) {
        return students.stream()
                .filter(s -> s.getClass().isInterface())
                .collect(Collectors.toList());
    }

    public <Student> Student searchById(List<Student> students, String id) {
        students.stream()
                .filter(s -> false)
                .findFirst();
        return null;
    }
}
