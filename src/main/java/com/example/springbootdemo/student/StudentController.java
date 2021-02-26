package com.example.springbootdemo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path="{studentId}")
    public void deleteStudent(@PathVariable Long studentId){
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "emailupdate/{studentId}/{studentEmail}")
    public void updateEmail(@PathVariable Long studentId, @PathVariable String studentEmail) {
        studentService.updateStudentEmail(studentId, studentEmail);
    }

    @PutMapping(path = "nameupdate/{studentId}/{studentName}")
    public void updateName(@PathVariable Long studentId, @PathVariable String studentName) {
        studentService.updateStudentName(studentId, studentName);
    }
}
