package org.example.sms.service;

import org.example.sms.Persistence;
import org.example.sms.entity.Teacher;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TeacherServiceTest {
    private static Persistence persistence;
    private static TeacherService teacherService;

    @BeforeAll
    static void setUp() {
        persistence = Persistence.getInstance("SchoolManagementTest");
        teacherService = new TeacherService(persistence);
    }

    @AfterAll
    static void tearDown() {
        teacherService.close();
        persistence.close();
    }

    @Test
    void createTeacher() {
        // Test create Teacher by a null user
        Teacher teacher = teacherService.createTeacher("TeachID", "TeacherPassword", null);
        assertNotNull(teacher);         // Must not return a null object
        assertNull(teacher.getId());    // Must not have a Database ID yet (since it is not persisted yet)
        assertNotNull(teacher.getDateCreated());    // Must have a date created
        assertEquals("TeachID", teacher.getEmploymentId());     // Must have the natural ID
        assertTrue(UserService.checkPassword(teacher, "TeacherPassword"));  // Password check should work

        // Test create Teacher by another Teacher user
        Teacher teacher2 = teacherService.createTeacher("Teach2ID", "password", teacher);
        assertNotNull(teacher2);
        assertNotNull(teacher2.getCreatedBy());     // Must have a createdBy user
        assertSame(teacher, teacher2.getCreatedBy());
    }

    @Test
    void addTeacher() {
        // Create and Persist New Teacher into database
        Teacher teacher = teacherService.addTeacher("TeachID", "password", null);
        assertNotNull(teacher);
        assertNotNull(teacher.getId());
        Teacher t = teacherService.findTeacher(teacher.getEmploymentId());
        assertNotNull(t);
        assertSame(teacher, t);

        // Try creating an exiting Teacher
        Teacher teacher1 = teacherService.addTeacher("TeachID", "passw", null);
        assertNull(teacher1);     // Must be null
    }
}