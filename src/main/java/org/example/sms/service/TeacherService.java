package org.example.sms.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.sms.Persistence;
import org.example.sms.entity.QTeacher;
import org.example.sms.entity.Teacher;
import org.example.sms.entity.User;

public class TeacherService {
    private final EntityManager entityManager;
    private final Logger logger;
    public TeacherService(Persistence persistence) {
        entityManager = persistence.createEntityManager();
        logger = LogManager.getLogger(TeacherService.class);
    }

    public Teacher createTeacher(String employmentId, String password, User creator) {
        Teacher teacher = new Teacher(employmentId);
        UserService.newUser(teacher, password, creator);
        logger.info(String.format("New Teacher Created: %s", teacher));
        return teacher;
    }

    public void addTeacher(Teacher teacher) {
        entityManager.getTransaction().begin();
        entityManager.persist(teacher);
        entityManager.getTransaction().commit();
        logger.info(String.format("Teacher persisted in database: %s", teacher));
    }

    public Teacher addTeacher(String employmentId, String password, User creator) {
        Teacher exitingUser = findTeacher(employmentId);
        if (exitingUser != null) {
            logger.error("Teacher already exist");
            return null;
        }
        Teacher teacher = createTeacher(employmentId, password, creator);
        addTeacher(teacher);
        return teacher;
    }

    public void modifyTeacher(Teacher teacher, String name, User modifier) {
        entityManager.getTransaction().begin();
        teacher.setName(name);
        UserService.modifyUser(teacher, modifier);
        entityManager.getTransaction().commit();
    }

    public Teacher findTeacher(String employmentId) {
        QTeacher teacher = QTeacher.teacher;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        return queryFactory.selectFrom(teacher)
                .where(teacher.userId.eq(employmentId))
                .fetchOne();
    }

    public void close() {
        entityManager.close();
    }
}
