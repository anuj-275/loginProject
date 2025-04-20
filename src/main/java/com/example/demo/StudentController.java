package com.example.demo;

import com.example.demo.entity.Student;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/")
    public String showLoginPage(Model model) {
        model.addAttribute("student", new Student());
        return "login"; // assuming login.html is your login page
    }
    @GetMapping("/login")
    public String loginForm(Model model) {
//        model.addAttribute("student", new Student());
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

        if (service.present(student)) {
            model.addAttribute("error", "User already exists. Try with a different email.");
            return "register"; // Stay on the registration page
        }
        service.register(student);
        model.addAttribute("message", "Registration successful! Please log in.");
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // clear session
        return "redirect:/login"; // redirect to login page
    }

}

