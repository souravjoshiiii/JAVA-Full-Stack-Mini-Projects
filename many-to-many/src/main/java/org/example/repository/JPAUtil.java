package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory emf;

    static {

        try {

            emf = Persistence.createEntityManagerFactory("companyPU");

        } catch (Exception e) {

            throw new RuntimeException("Error creating EntityManagerFactory", e);

        }

    }

    public static EntityManager getEntityManager() {

        return emf.createEntityManager();

    }

    public static void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }

}
