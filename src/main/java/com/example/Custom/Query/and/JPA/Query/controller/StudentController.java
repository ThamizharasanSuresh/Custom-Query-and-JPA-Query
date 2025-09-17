package com.example.Custom.Query.and.JPA.Query.controller;

import com.example.Custom.Query.and.JPA.Query.entity.Student;
import com.example.Custom.Query.and.JPA.Query.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentRepository studentRepo;

    @PostMapping
    public String addstudent(@RequestBody Student student){
         studentRepo.save(student);
         return "Added....";
    }

    // GET all students
    @GetMapping
    public List<Student> getAll() {
        return studentRepo.findAll();
    }

    @PutMapping("/{id}")
    public Student updstudent(@PathVariable Long id,@RequestBody Student student){
        Optional<Student>exist=studentRepo.findById(id);
        if(exist.isPresent()){
            Student st=exist.get();
            st.setName(student.getName());
            st.setEmail(student.getEmail());
            return studentRepo.save(st);
        }
        else {
            throw new RuntimeException("Student not found");
        }
    }

    @PutMapping("/{id}/name")
    public String updstd(@PathVariable Long id,@RequestParam String name){
        studentRepo.updateStudentNameById(id,name);
        return "Updated Student";
    }

    // JPA Query Example
    @GetMapping("/byEmail")
    public Student getByEmail(@RequestParam String email) {
        return studentRepo.findByEmail(email);
    }

    // Custom JPQL Query Example
    @GetMapping("/byDomain")
    public List<Student> getByDomain(@RequestParam String domain) {
        return studentRepo.getStudentsByEmailDomain(domain);
    }

    // Native SQL Example
    @GetMapping("/search")
    public List<Student> searchByName(@RequestParam String name) {
        return studentRepo.searchByNameNative(name);
    }
}
