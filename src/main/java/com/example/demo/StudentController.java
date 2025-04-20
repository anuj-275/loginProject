package com.example.demo;

import com.example.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

    @Autowired
    private StudentService service;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        Student student = service.login(email, password);
        if (student != null) {
            model.addAttribute("student", student);
            return "profile";
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }

    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("student", new Student());
        return "register"; // This should match the register.html filename (without .html)
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Student student, Model model) {
        // Save logic here
        service.register(student);
        model.addAttribute("message", "Registration successful! Please log in.");
        return "login";
    }
}

