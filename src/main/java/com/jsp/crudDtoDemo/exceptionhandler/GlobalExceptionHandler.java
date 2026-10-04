package com.jsp.crudDtoDemo.exceptionhandler;

import com.jsp.crudDtoDemo.dto.ExceptionResponseDto;
import com.jsp.crudDtoDemo.dto.ValidationExceptionResponseDto;

import com.jsp.crudDtoDemo.exception.EmailAlreadyExistsException;
import com.jsp.crudDtoDemo.exception.StudentNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // =====================================================
    // EMAIL ALREADY EXISTS
    // =====================================================

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponseDto>
    handleEmailAlreadyExists(
            EmailAlreadyExistsException exception,
            HttpServletRequest request) {

        ExceptionResponseDto response =
                new ExceptionResponseDto(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        "Conflict",
                        exception.getMessage(),
                        request.getRequestURI()
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }


    // =====================================================
    // STUDENT NOT FOUND
    // =====================================================

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto>
    handleStudentNotFound(
            StudentNotFoundException exception,
            HttpServletRequest request) {

        ExceptionResponseDto response =
                new ExceptionResponseDto(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Student Not Found",
                        exception.getMessage(),
                        request.getRequestURI()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }


    // =====================================================
    // VALIDATION EXCEPTION
    // =====================================================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponseDto>
    handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        Map<String, String> errors =
                new HashMap<>();


        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {

                    errors.put(
                            error.getField(),
                            error.getDefaultMessage()
                    );

                });


        ValidationExceptionResponseDto response =
                new ValidationExceptionResponseDto(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation Failed",
                        "Please correct the validation errors",
                        request.getRequestURI(),
                        errors
                );


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    // =====================================================
    // GENERAL EXCEPTION
    // =====================================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto>
    handleGeneralException(
            Exception exception,
            HttpServletRequest request) {

        ExceptionResponseDto response =
                new ExceptionResponseDto(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        exception.getMessage(),
                        request.getRequestURI()
                );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}