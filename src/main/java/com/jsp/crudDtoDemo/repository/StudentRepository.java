package com.jsp.crudDtoDemo.repository;

import com.jsp.crudDtoDemo.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Student> findByIdAndDeletedFalse(Long id);

    List<Student> findAllByDeletedFalse();
}