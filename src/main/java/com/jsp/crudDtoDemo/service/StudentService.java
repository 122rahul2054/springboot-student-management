package com.jsp.crudDtoDemo.service;

import com.jsp.crudDtoDemo.dto.StudentRequestDto;
import com.jsp.crudDtoDemo.dto.StudentResponseDto;
import com.jsp.crudDtoDemo.dto.UpdateStudentRequestDto;
import com.jsp.crudDtoDemo.dto.UpdateStudentResponseDto;

import com.jsp.crudDtoDemo.entity.Student;

import com.jsp.crudDtoDemo.exception.EmailAlreadyExistsException;
import com.jsp.crudDtoDemo.exception.StudentNotFoundException;

import com.jsp.crudDtoDemo.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    // =====================================================
    // CREATE STUDENT
    // =====================================================

    public StudentResponseDto saveStudent(
            StudentRequestDto requestDto) {

        // Check duplicate email
        if (studentRepository.existsByEmail(
                requestDto.getEmail())) {

            throw new EmailAlreadyExistsException(
                    "Student with email "
                            + requestDto.getEmail()
                            + " already exists"
            );
        }


        Student student = new Student();

        student.setName(requestDto.getName());
        student.setAge(requestDto.getAge());
        student.setEmail(requestDto.getEmail());

        student.setRollNo(
                Integer.parseInt(requestDto.getRollNo())
        );

        student.setSubject(requestDto.getSubject());

        // Soft delete
        student.setDeleted(false);


        LocalDateTime now = LocalDateTime.now();

        student.setCreatedAt(now);
        student.setUpdatedAt(now);


        Student savedStudent =
                studentRepository.save(student);


        return convertToStudentResponse(savedStudent);
    }


    // =====================================================
    // GET STUDENT BY ID
    // =====================================================

    public StudentResponseDto getStudentById(Long id) {

        Student student =
                studentRepository
                        .findByIdAndDeletedFalse(id)
                        .orElseThrow(() ->
                                new StudentNotFoundException(
                                        "Student with id "
                                                + id
                                                + " not found"
                                )
                        );

        return convertToStudentResponse(student);
    }


    // =====================================================
    // GET ALL STUDENTS
    // =====================================================

    public List<StudentResponseDto> getAllStudents() {

        List<Student> students =
                studentRepository.findAllByDeletedFalse();

        return students
                .stream()
                .map(this::convertToStudentResponse)
                .toList();
    }


    // =====================================================
    // UPDATE STUDENT
    // =====================================================

    public UpdateStudentResponseDto updateStudent(
            Long id,
            UpdateStudentRequestDto requestDto) {


        // Find active student
        Student student =
                studentRepository
                        .findByIdAndDeletedFalse(id)
                        .orElseThrow(() ->
                                new StudentNotFoundException(
                                        "Student with id "
                                                + id
                                                + " not found"
                                )
                        );


        // Check whether email belongs to another student
        if (studentRepository.existsByEmailAndIdNot(
                requestDto.getEmail(),
                id)) {

            throw new EmailAlreadyExistsException(
                    "Email "
                            + requestDto.getEmail()
                            + " already belongs to another student"
            );
        }


        // Update fields
        student.setName(requestDto.getName());
        student.setAge(requestDto.getAge());
        student.setEmail(requestDto.getEmail());
        student.setRollNo(requestDto.getRollNo());
        student.setSubject(requestDto.getSubject());

        student.setUpdatedAt(LocalDateTime.now());


        Student updatedStudent =
                studentRepository.save(student);


        return convertToUpdateResponse(updatedStudent);
    }


    // =====================================================
// SOFT DELETE
// =====================================================

    public String softDeleteStudent(Long id) {

        Student student =
                studentRepository
                        .findByIdAndDeletedFalse(id)
                        .orElseThrow(() ->
                                new StudentNotFoundException(
                                        "Student with id "
                                                + id
                                                + " not found"
                                )
                        );

        student.setDeleted(true);
        student.setUpdatedAt(LocalDateTime.now());

        studentRepository.save(student);

        return "Student with id "
                + id
                + " soft deleted successfully";
    }


// =====================================================
// HARD DELETE
// =====================================================

    public String deleteStudent(Long id) {

        Student student =
                studentRepository.findById(id)
                        .orElseThrow(() ->
                                new StudentNotFoundException(
                                        "Student with id "
                                                + id
                                                + " not found"
                                )
                        );

        studentRepository.delete(student);

        return "Student with id "
                + id
                + " permanently deleted successfully";
    }


    // =====================================================
    // ENTITY -> STUDENT RESPONSE DTO
    // =====================================================

    private StudentResponseDto convertToStudentResponse(
            Student student) {

        return new StudentResponseDto(
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getEmail(),
                student.getRollNo(),
                student.getSubject(),
                student.getDeleted(),
                student.getCreatedAt(),
                student.getUpdatedAt()
        );
    }


    // =====================================================
    // ENTITY -> UPDATE RESPONSE DTO
    // =====================================================

    private UpdateStudentResponseDto convertToUpdateResponse(
            Student student) {

        return new UpdateStudentResponseDto(
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getEmail(),
                student.getRollNo(),
                student.getSubject(),
                student.getUpdatedAt()
        );
    }
}