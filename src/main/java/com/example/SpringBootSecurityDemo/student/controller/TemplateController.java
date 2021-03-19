package com.example.SpringBootSecurityDemo.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String getLogInView() {
        return "login";
    }

    @GetMapping("courses")
    public String getStudentCourses() {
        return "courses";
    }
}
