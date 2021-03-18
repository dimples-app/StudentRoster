package com.example.SpringBootSecurityDemo.student.client;

/**
 *  Student class
 */
public class Student {

    private  final Integer studentId;
    private final String studentName;

    /**
     * Student Construction
     * @param studentId
     * @param studentName
     */
    public Student(Integer studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    /**
     * Get Student Id
     * @return studentId
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * Get student name
     * @return studentname
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * To string method
     * @return student
     */
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
