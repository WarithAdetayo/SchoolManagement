package org.example.sms;

import com.google.common.collect.FluentIterable;
import org.example.sms.entity.*;
import org.example.sms.service.*;
import org.example.sms.util.JsonString;
import org.example.sms.util.Permission;
import org.example.sms.util.Response;
import org.example.sms.util.ResponseManager;

import java.util.List;

public class BackendController {
    private static BackendController instance;
    private final Persistence persistence;
    private User signedInUser;
    private final AdminService adminService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final CourseRecordService courseRecordService;

    private BackendController() {
        persistence = Persistence.getInstance("SchoolManagement");
        adminService = new AdminService(persistence);
        studentService = new StudentService(persistence);
        teacherService = new TeacherService(persistence);
        courseService = new CourseService(persistence);
        courseRecordService = new CourseRecordService(Persistence);
    }

    public static BackendController getInstance() {
        if (instance == null) {
            instance = new BackendController();
        }
        return instance;
    }

    // General User Activity
    // Sign In, By Admin, By Student and By Teacher
    // Sign out
    // Sign up, by Admin only (i.e. Register an Admin Account to manage the program)
    public Response<Boolean> adminSignIn(String username, String password) {
        Admin admin = adminService.findAdmin(username);
        return signInUser(admin, password);
    }

    public Response<Boolean> studentSignIn(String matricNumber, String password) {
        Student student = studentService.findStudent(matricNumber);
        return  signInUser(student, password);
    }

    public Response<Boolean> teacherSignIn(String teacherId, String password) {
        Teacher teacher = teacherService.findTeacher(teacherId);
        return  signInUser(teacher, password);
    }

    public Response<Boolean> signInUser(User user, String password) {

        if (user != null) {
            if (UserService.checkPassword(user, password)) {
                this.signedInUser = user;
                return  new Response<>(200, "Sign in successfully", true);
            }
        }
        return new Response<>(400, "Incorrect Username or Password", false);
    }

    public Response<Boolean> changePassword(String oldPassword, String newPassword) {
        if (Permission.userIsNotSignedIn(this.signedInUser)) {
            return ResponseManager.notSignedInMessage(false);
        }

        if (!UserService.checkPassword(this.signedInUser, oldPassword)) {
            return new Response<>(400, "Incorrect old password", false);
        }

        UserService.setPassword(this.signedInUser, newPassword);
        UserService.modifyUser(this.signedInUser, this.signedInUser);
        return new Response<>(200, "Password Changed Successfully", true);
    }

    public Response<Boolean> signOutUser() {
        if (Permission.userIsSignedIn(this.signedInUser)) {
            this.signedInUser = null;
            return ResponseManager.okMessage("User Signed out successfully", true);
        }
        return ResponseManager.badRequestMessage("User not signed in", false);
    }

    // Admin Activities
    // Add Course, Add Teacher, Add Student
    public Response<Boolean> addCourse(String courseName, String courseDescription, String courseShortName) {
        if (Permission.userIsNotAdmin(this.signedInUser))
            return ResponseManager.notAuthorisedMessage(false);
        courseService.addCourse(courseName, courseDescription, courseShortName);
        return ResponseManager.createdMessage("Course Created!", true);
    }

    public Response<Boolean> addStudent(String matricNumber, String firstName, String lastName) {
        // Adds New Student and make their lastname as password
        // Student will be required to change it on first login

        if (Permission.userIsNotAdmin(this.signedInUser))
            return ResponseManager.notAuthorisedMessage(false);

        Student student = studentService.createStudent(matricNumber, lastName, this.signedInUser);
        studentService.modifyStudent(student, firstName, lastName, null, this.signedInUser);
        return ResponseManager.createdMessage("Student Created!", true);
    }

    public Response<Boolean> addTeacher(String teacherID, String teacherName, String courseShortName) {
        // Adds New Teacher, their ID as password
        // Will be prompted to change it on first login

        if (Permission.userIsNotAdmin(this.signedInUser))
            return ResponseManager.notAuthorisedMessage(false);

        Teacher teacher = teacherService.addTeacher(teacherID, teacherID, this.signedInUser);
        teacherService.modifyTeacher(teacher, teacherName, this.signedInUser);
        return ResponseManager.createdMessage("Teacher Created!", true);
    }

    // Student Activities
    // Register Course, View Course Records

    public Response<String> getAllAvailableCourse() {
        if (Permission.userIsNotSignedIn(this.signedInUser))
            return ResponseManager.notSignedInMessage(null);
        String str = JsonString.toJsonString(courseService.getAllCourses());
        return ResponseManager.okMessage(str);
    }

    public Response<Boolean> registerCourse(String registrationData) {
        if (Permission.userIsNotStudent(this.signedInUser))
            return ResponseManager.notSignedInMessage(null);
        List<String> toBeRegisteredCourses = JsonString.toObject(registrationData, List.class);

        assert toBeRegisteredCourses != null;
        if (toBeRegisteredCourses.size() > 7)
            return ResponseManager.badRequestMessage("You cannot register more than 7 courses", false);
        for (String courseCode : toBeRegisteredCourses) {
            Course c = courseService.findCourseByCode(courseCode);
            if (c == null)
                return ResponseManager.badRequestMessage("Unknown course code: " + courseCode, false);
            c
        }
        return ResponseManager.okMessage("Courses Registered", true);
    }

    public void getCourseRecords() {
    }

    // Teacher Activities
    // View guidance's (Students), Update Student Score records

    public void getStudentGuidanceList() {}

    public void updateStudentRecord(String studentMatricNumber, String courseCode) {}

    private void shutDown() {
        studentService.close();
        adminService.close();
        teacherService.close();
        courseService.close();
        courseRecordService.close();
        persistence.close();
    }
}
