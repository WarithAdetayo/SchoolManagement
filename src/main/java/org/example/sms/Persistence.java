package org.example.sms;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Persistence {
    private static Persistence instance;
    private final EntityManagerFactory entityManagerFactory;

    private Persistence(String persistence_unit) {
        entityManagerFactory = jakarta.persistence.Persistence
                .createEntityManagerFactory(persistence_unit);
    }

    public static Persistence getInstance(String persistence_unit) {
        if (instance == null) {
            instance = new Persistence(persistence_unit);
        }
        return instance;
    }

    public EntityManager createEntityManager() {
        return this.entityManagerFactory.createEntityManager();
    }

    public void close() {
        this.entityManagerFactory.close();
    }
}
