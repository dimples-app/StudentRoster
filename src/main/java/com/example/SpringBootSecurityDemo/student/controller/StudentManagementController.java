package com.example.SpringBootSecurityDemo.student.controller;

import com.example.SpringBootSecurityDemo.student.client.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 *  Created Rest Api
 */

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "James Clark"),
            new Student(2, "Ana Wood"),
            new Student(3, "Maria Smith")
    );

    /**
     * Get list of students
     * @return
     */
    @GetMapping
    public static List<Student> getSTUDENTS() {
        return STUDENTS;
    }

    /**
     * Registrered students
     * @param student
     */
    @PostMapping
    public void registeredNewStudent( @RequestBody Student student) {
        System.out.println(student);
    }

    /**
     * Delete student
     * @param studentId
     */
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable Integer studentId) {
        System.out.println(studentId);
    }

    /**
     * Update student
     * @param studentId
     * @param student
     */
    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable Integer studentId, @RequestBody Student student) {
        System.out.println(String.format("%s %s", studentId, student));
    }


}

