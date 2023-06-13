package org.example.sms.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.sms.Persistence;
import org.example.sms.dto.CourseDTO;
import org.example.sms.entity.Course;
import org.example.sms.entity.QCourse;
import org.example.sms.entity.QTeacher;
import org.example.sms.entity.Teacher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseService {
    private final EntityManager entityManager;
    private final Logger logger;

    public CourseService(Persistence persistence) {
        entityManager = persistence.createEntityManager();
        logger = LogManager.getLogger(CourseService.class);
    }

    public Course createCourse(String courseName, String courseDescription, String courseShortName) {
        Course course = new Course();
        course.setCourseShortCode(courseShortName);
        course.setCourseName(courseName);
        course.setCourseDescription(courseDescription);
        course.setDateCreated(new Date());
        logger.info("New Course Created!!!");
        return course;
    }

    public void addCourse(Course course) {
        entityManager.getTransaction().begin();
        entityManager.persist(course);
        entityManager.getTransaction().commit();
        logger.info("Course Persisted into Database");
    }

    public Course addCourse(String courseName, String courseDescription, String courseShortName) {
        Course course = createCourse(courseName, courseDescription, courseShortName);
        addCourse(course);
        return course;
    }

    public List<CourseDTO> getAllCourses() {
        QCourse course = QCourse.course;

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<Course> courses = queryFactory.selectFrom(course)
                .fetch();
        return toCourseDTO(courses);
    }

    public static CourseDTO toCourseDTO(Course course) {
        return new CourseDTO(
                course.getCourseShortCode(),
                course.getCourseName(),
                course.getCourseDescription()
        );
    }

    public static List<CourseDTO> toCourseDTO(List<Course> courses) {
        List<CourseDTO>coursesDTO = new ArrayList<>();

        for (Course course : courses) {
            coursesDTO.add(toCourseDTO(course));
        }
        return coursesDTO;
    }

    public Course findCourseByCode(String courseShortCode) {
        QCourse course = QCourse.course;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        return queryFactory.selectFrom(course)
                .where(course.courseShortCode.eq(courseShortCode))
                .fetchOne();
    }

    public void close() {
        entityManager.close();
    }
}
