package com.example.springbootdemo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
        if(studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        boolean exists = studentRepository.existsById(id);
        if(!exists) {
            throw new IllegalStateException(String.format("student with id %d does not exist", id));
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudentEmail(Long id, String email) {
        if(!studentRepository.existsById(id)) {
            throw new IllegalStateException(String.format("student with id %d dose not exist", id));
        }
        studentRepository.getOne(id).setEmail(email);
    }

    @Transactional
    public void updateStudentName(Long id, String name) {
        String[] nameSplit = name.split("-");
        String firstName = nameSplit[0];
        String lastName = nameSplit[1];

        if(!studentRepository.existsById(id)) {
            throw new IllegalStateException(String.format("student with id %d dose not exist", id));
        }

        Student studentToUpdate = studentRepository.getOne(id);
        studentToUpdate.setFirstName(firstName);
        studentToUpdate.setLastName(lastName);
    }
}