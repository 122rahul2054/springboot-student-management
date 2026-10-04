# Spring Boot Student Management System

A RESTful **Student Management System** developed using **Spring Boot, Spring Data JPA, PostgreSQL, DTOs, Validation, and Global Exception Handling**.

This project demonstrates how to build a clean and structured CRUD REST API with features such as student creation, retrieval, updating, soft deletion, hard deletion, validation, duplicate email checking, and custom exception handling.

## 🚀 Features

* Create a new student
* Get student by ID
* Get all active students
* Update student details
* Soft delete student
* Hard delete student permanently
* DTO-based request and response handling
* Email validation
* Input validation
* Duplicate email checking
* Student not found exception handling
* Global exception handling
* PostgreSQL database integration
* Automatic created and updated timestamps
* RESTful API architecture

## 🛠️ Technologies Used

* Java 21
* Spring Boot 4.1.1
* Spring Web MVC
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* Postman
* IntelliJ IDEA
* Git & GitHub

## 📂 Project Structure

```text
src
└── main
    └── java
        └── com.jsp.crudDtoDemo
            │
            ├── CrudDtoDemoApplication.java
            │
            ├── controller
            │   └── StudentController.java
            │
            ├── dto
            │   ├── StudentRequestDto.java
            │   ├── StudentResponseDto.java
            │   ├── UpdateStudentRequestDto.java
            │   ├── UpdateStudentResponseDto.java
            │   ├── ExceptionResponseDto.java
            │   └── ValidationExceptionResponseDto.java
            │
            ├── entity
            │   └── Student.java
            │
            ├── exception
            │   ├── EmailAlreadyExistsException.java
            │   └── StudentNotFoundException.java
            │
            ├── exceptionhandler
            │   └── GlobalExceptionHandler.java
            │
            ├── repository
            │   └── StudentRepository.java
            │
            └── service
                └── StudentService.java
```

## 🗄️ Database Configuration

This project uses **PostgreSQL**.

Create a database:

```sql
CREATE DATABASE student_crud;
```

Configure your database in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.application.name=crudDtoDemo

spring.datasource.url=jdbc:postgresql://localhost:5432/student_crud
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

> Replace `YOUR_PASSWORD` with your PostgreSQL password. Do not upload real database credentials to GitHub.

## 🔗 API Endpoints

Base URL:

```text
http://localhost:8080/api/students
```

### 1. Create Student

```http
POST /api/students/save
```

Example request:

```json
{
    "name": "Rahul Singh",
    "age": 24,
    "email": "rahul@gmail.com",
    "rollNo": 101,
    "subject": "Java"
}
```

### 2. Get Student By ID

```http
GET /api/students/{id}
```

Example:

```http
GET /api/students/1
```

### 3. Get All Active Students

```http
GET /api/students
```

Only students whose:

```text
deleted = false
```

are returned.

### 4. Update Student

```http
PUT /api/students/{id}
```

Example:

```http
PUT /api/students/1
```

Request:

```json
{
    "name": "Rahul Singh Updated",
    "age": 25,
    "email": "rahul@gmail.com",
    "rollNo": 101,
    "subject": "Spring Boot"
}
```

### 5. Soft Delete

```http
DELETE /api/students/soft-delete/{id}
```

Example:

```http
DELETE /api/students/soft-delete/1
```

Soft delete does **not** remove the record from the database.

Instead:

```text
deleted = true
```

The record remains in the database but is excluded from normal GET operations.

### 6. Hard Delete

```http
DELETE /api/students/delete/{id}
```

Example:

```http
DELETE /api/students/delete/1
```

Hard delete permanently removes the student record from the database.

## ⚠️ Exception Handling

The project uses custom exceptions and a global exception handler.

### Student Not Found

Returns:

```http
404 NOT FOUND
```

Example response:

```json
{
    "timestamp": "2026-10-04T10:30:00",
    "statusCode": 404,
    "error": "Student Not Found",
    "message": "Student with id 999 not found",
    "path": "/api/students/999"
}
```

### Duplicate Email

Returns:

```http
409 CONFLICT
```

Example:

```json
{
    "timestamp": "2026-10-04T10:30:00",
    "statusCode": 409,
    "error": "Conflict",
    "message": "Student with email rahul@gmail.com already exists",
    "path": "/api/students/save"
}
```

### Validation Error

Returns:

```http
400 BAD REQUEST
```

Validation is implemented using:

```java
@NotBlank
@Email
@NotNull
@Min
```

## 🗑️ Soft Delete vs Hard Delete

| Feature          | Soft Delete                    | Hard Delete    |
| ---------------- | ------------------------------ | -------------- |
| Database record  | Remains                        | Removed        |
| `deleted` value  | `true`                         | Record removed |
| Can be recovered | Yes, if restore is implemented | No             |
| Permanent        | No                             | Yes            |
| Endpoint         | `/soft-delete/{id}`            | `/delete/{id}` |

## 🔄 Application Flow

```text
Client / Postman
       |
       v
StudentController
       |
       v
DTO Validation
       |
       v
StudentService
       |
       v
StudentRepository
       |
       v
PostgreSQL Database
```

Exception flow:

```text
Exception
    |
    v
GlobalExceptionHandler
    |
    v
ExceptionResponseDto
    |
    v
JSON Response
```

## ▶️ How to Run

### Step 1: Clone the repository

```bash
git clone https://github.com/122rahul2054/springboot-student-management.git
```

### Step 2: Open the project

Open the project in **IntelliJ IDEA** or any Java IDE.

### Step 3: Configure PostgreSQL

Create the database:

```sql
CREATE DATABASE student_crud;
```

Update your PostgreSQL username and password in `application.properties`.

### Step 4: Build the project

```bash
mvn clean install
```

### Step 5: Run the application

```bash
mvn spring-boot:run
```

Or run:

```text
CrudDtoDemoApplication.java
```

from IntelliJ IDEA.

### Step 6: Test APIs

Use **Postman** to test the REST APIs.

Base URL:

```text
http://localhost:8080/api/students
```

## 📌 Learning Objectives

This project was created to practice and demonstrate:

* Spring Boot REST API development
* CRUD operations
* DTO architecture
* Spring Data JPA
* PostgreSQL integration
* Bean validation
* Custom exceptions
* Global exception handling
* Soft delete and hard delete
* REST API testing using Postman
* Maven project management
* Git and GitHub

## 👨‍💻 Author

**Rahul Singh**

GitHub:
https://github.com/122rahul2054

## ⭐ Future Enhancements

* Student restore functionality
* Pagination and sorting
* Search students by name or subject
* JWT authentication and authorization
* Swagger/OpenAPI documentation
* React.js frontend
* Deployment to cloud

---

⭐ If you find this project useful, consider giving it a star!
