package org.example.sms.service;

import org.example.sms.Persistence;
import org.example.sms.entity.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    private static Persistence persistence;
    private static StudentService studentService;

    @BeforeAll
    static void setUp() {
        persistence = Persistence.getInstance("SchoolManagementTest");
        studentService = new StudentService(persistence);
    }

    @AfterAll
    static void tearDown() {
        studentService.close();
        persistence.close();
    }

    @Test
    void createStudent() {
        Student student = studentService.createStudent("matricNum", "StudentPassword", null);
        assertNotNull(student);
        assertNull(student.getId());
        assertNotNull(student.getDateCreated());
        assertEquals("matricNum", student.getMatricNumber());
        assertTrue(UserService.checkPassword(student, "StudentPassword"));
    }

    @Test
    void addStudent() {
        Student student = studentService.addStudent("MatricN", "password", null);

        assertNotNull(student);
        assertNotNull(student.getId());

        Student s = studentService.findStudent(student.getMatricNumber());
        assertNotNull(s);
        assertSame(student, s);

        Student student1 = studentService.addStudent("MatricN", "passw", null);
        assertNull(student1);
    }
}