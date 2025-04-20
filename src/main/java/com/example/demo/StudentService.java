package com.example.demo;

import com.example.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repo;

    public Student register(Student student) {
        return repo.save(student);
    }

    public Student login(String email, String password) {
        Student student = repo.findByEmail(email);
        if (student != null && student.getPassword().equals(password)) {
            return student;
        }
        return null;
    }

    public boolean present(Student student){
        return repo.findByEmail(student.getEmail())==null? false:true;
    }
}

