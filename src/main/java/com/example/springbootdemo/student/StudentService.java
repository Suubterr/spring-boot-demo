package com.example.springbootdemo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        boolean exists = studentRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException(String.format("student with id %d does not exist", id));
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long studentId, String studentEmail, String studentName) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException(
                String.format("Student id %d does not exist", studentId)
        ));

        if (studentName != null && studentName.length() > 0 &&
                !Objects.equals(studentName, student.getFirstName() + '-' + student.getLastName())) {
            student.setFirstName(studentName.split("-")[0]);
            student.setLastName(studentName.split("-")[1]);
        }

        if (studentEmail != null && studentEmail.length() > 0) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(studentEmail);
            if(studentOptional.isPresent()) {
                throw new IllegalStateException(String.format("email %s is taken", studentEmail));
            }
            student.setEmail(studentEmail);
        }
    }
}