package org.example.sms.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.sms.Persistence;
import org.example.sms.entity.QStudent;
import org.example.sms.entity.Student;
import org.example.sms.entity.User;

import java.util.Date;

public class StudentService {
    private final EntityManager entityManager;
    private final Logger logger;

    public StudentService(Persistence persistence) {
        entityManager = persistence.createEntityManager();
        logger = LogManager.getLogger(StudentService.class);
    }

    public Student createStudent(String matricNumber, String password, User creator) {
        Student student = new Student(matricNumber);
        UserService.newUser(student, password, creator);
        logger.info(String.format("New Student Created: %s", student));
        return  student;
    }

    public void addStudent(Student student) {
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
        logger.info(String.format("Student persisted in database: %s", student));
    }

    public Student addStudent(String matricNumber, String password, User creator) {
        Student exitingUser = findStudent(matricNumber);
        if (exitingUser != null) {
            logger.error("Student already exist");
            return null;
        }
        Student student = createStudent(matricNumber, password, creator);
        addStudent(student);
        return student;
    }

    public Student findStudent(String matricNumber) {
        QStudent student = QStudent.student;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        return queryFactory.selectFrom(student)
                .where(student.userId.eq(matricNumber))
                .fetchOne();
    }

    public void modifyStudent(Student student, String firstName, String lastName, Date dateOfBirth, User modifier) {
        entityManager.getTransaction().begin();
        if (firstName != null)
            student.setFirstName(firstName);
        if (lastName != null)
            student.setLastName(lastName);
        if (dateOfBirth != null)
            student.setDateOfBirth(dateOfBirth);
        UserService.modifyUser(student, modifier);
        entityManager.getTransaction().commit();

        logger.info("Student data modified");

    }

    public void close() {
        entityManager.close();
    }
}
