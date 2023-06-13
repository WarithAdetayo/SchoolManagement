package org.example.sms.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.sms.Persistence;
import org.example.sms.entity.QAdmin;
import org.example.sms.entity.Admin;
import org.example.sms.entity.User;

public class AdminService {
    private final EntityManager entityManager;
    private final Logger logger;

    public AdminService(Persistence persistence) {
        entityManager = persistence.createEntityManager();
        logger = LogManager.getLogger(AdminService.class);
    }

    public Admin createAdmin(String username, String password, User creator) {
        Admin admin = new Admin(username);
        UserService.newUser(admin, password, creator);
        logger.info(String.format("New Admin Created: %s", admin));
        return  admin;
    }

    public void addAdmin(Admin admin) {
        entityManager.getTransaction().begin();
        entityManager.persist(admin);
        entityManager.getTransaction().commit();
        logger.info(String.format("Admin persisted in database: %s", admin));
    }

    public Admin addAdmin(String username, String password, User creator) {
        Admin exitingUser = findAdmin(username);
        if (exitingUser != null) {
            logger.error("Admin already exist");
            return null;
        }
        Admin admin = createAdmin(username, password, creator);
        addAdmin(admin);
        return admin;
    }

    public Admin findAdmin(String username) {
        QAdmin admin = QAdmin.admin;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        return queryFactory.selectFrom(admin)
                .where(admin.userId.eq(username))
                .fetchOne();
    }

    public void close() {
        entityManager.close();
    }
}
