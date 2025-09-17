package com.example.Custom.Query.and.JPA.Query.repository;

import com.example.Custom.Query.and.JPA.Query.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    //  JPA Derived Queries
    Student findByEmail(String email);
    List<Student> findByNameContaining(String keyword);

    //  Custom Queries with @Query
    @Query("SELECT s FROM Student s WHERE s.email LIKE %:domain")
    List<Student> getStudentsByEmailDomain(String domain);

    @Query(value = "SELECT * FROM student WHERE name ILIKE %:name%", nativeQuery = true)
    List<Student> searchByNameNative(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.name = :name WHERE s.id = :id")
    int updateStudentNameById(Long id, String name);
}