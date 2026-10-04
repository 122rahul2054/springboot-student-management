package com.jsp.crudDtoDemo.controller;

import com.jsp.crudDtoDemo.dto.StudentRequestDto;
import com.jsp.crudDtoDemo.dto.StudentResponseDto;
import com.jsp.crudDtoDemo.dto.UpdateStudentRequestDto;
import com.jsp.crudDtoDemo.dto.UpdateStudentResponseDto;

import com.jsp.crudDtoDemo.exception.StudentNotFoundException;
import com.jsp.crudDtoDemo.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    // =====================================================
    // CREATE
    // =====================================================

    @PostMapping("/save")
    public ResponseEntity<StudentResponseDto> saveStudent(
            @Valid @RequestBody StudentRequestDto requestDto) {

        StudentResponseDto response =
                studentService.saveStudent(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // =====================================================
    // GET BY ID
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(
            @PathVariable Long id) {

        StudentResponseDto response =
                studentService.getStudentById(id);

        return ResponseEntity.ok(response);
    }


    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping
    public ResponseEntity<List<StudentResponseDto>>
    getAllStudents() {

        List<StudentResponseDto> students =
                studentService.getAllStudents();

        return ResponseEntity.ok(students);
    }


    // =====================================================
    // UPDATE
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<UpdateStudentResponseDto>
    updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStudentRequestDto requestDto) {

        UpdateStudentResponseDto response =
                studentService.updateStudent(id, requestDto);

        return ResponseEntity.ok(response);
    }

    // =====================================================
// SOFT DELETE
// =====================================================

    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<String> softDeleteStudent(
            @PathVariable Long id) {

        String message =
                studentService.softDeleteStudent(id);

        return ResponseEntity.ok(message);
    }


// =====================================================
// HARD DELETE
// =====================================================

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable Long id) {

        String message =
                studentService.deleteStudent(id);

        return ResponseEntity.ok(message);
    }

}