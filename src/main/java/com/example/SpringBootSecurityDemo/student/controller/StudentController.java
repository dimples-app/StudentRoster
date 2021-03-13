package com.example.SpringBootSecurityDemo.student.controller;

import com.example.SpringBootSecurityDemo.student.client.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "James Clark"),
            new Student(2, "Ana Wood"),
            new Student(3, "Maria Smith")
    );

    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {
        return STUDENTS.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst().orElseThrow(() -> new IllegalStateException("Student " + studentId + "Does not Exist"));
    }

}
