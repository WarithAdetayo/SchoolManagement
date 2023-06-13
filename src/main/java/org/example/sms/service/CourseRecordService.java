package org.example.sms.service;

import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.sms.Persistence;
import org.example.sms.entity.Course;
import org.example.sms.entity.CourseRecord;
import org.example.sms.entity.Student;

import java.util.Date;

public class CourseRecordService {
    private final EntityManager entityManager;
    private final Logger logger;

    public CourseRecordService(Persistence persistence) {
        entityManager = persistence.createEntityManager();
        logger = LogManager.getLogger(CourseService.class);
    }

    public CourseRecord createCourseRecord(Course course, Student student) {
        CourseRecord courseRecord = new CourseRecord();
        courseRecord.setStudent(student);
        courseRecord.setCourse(course);
        courseRecord.setDateCreated(new Date());
        return courseRecord;
    }

    public void addCourseRecord(CourseRecord record) {
        entityManager.getTransaction().begin();
        entityManager.persist(record);
        entityManager.getTransaction().commit();
    }

    public CourseRecord addCourseRecord(Course course, Student student) {
        CourseRecord courseRecord = createCourseRecord(course, student);
        addCourseRecord(courseRecord);
        return courseRecord;
    }

    public void close() {
        entityManager.close();
    }
}
