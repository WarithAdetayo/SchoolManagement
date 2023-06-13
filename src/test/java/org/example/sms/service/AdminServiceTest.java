package org.example.sms.service;

import org.example.sms.Persistence;
import org.example.sms.entity.Admin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {
    private static Persistence persistence;
    private static AdminService adminService;

    @BeforeAll
    static void setUp() {
        persistence = Persistence.getInstance("SchoolManagementTest");
        adminService = new AdminService(persistence);
    }

    @AfterAll
    static void tearDown() {
        adminService.close();
        persistence.close();
    }

    @Test
    void createAdmin() {
        Admin admin = adminService.createAdmin("username", "AdminPassword", null);
        assertNotNull(admin);
        assertNull(admin.getId());
        assertNotNull(admin.getDateCreated());
        assertEquals("username", admin.getUsername());
        assertTrue(UserService.checkPassword(admin, "AdminPassword"));
    }

    @Test
    void addAdmin() {
        Admin admin = adminService.addAdmin("userName", "password", null);

        assertNotNull(admin);
        assertNotNull(admin.getId());

        Admin s = adminService.findAdmin(admin.getUsername());
        assertNotNull(s);
        assertSame(admin, s);

        Admin admin1 = adminService.addAdmin("userName", "passw", null);
        assertNull(admin1);
    }
}