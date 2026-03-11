package org.capgi;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppTest {

    @Test
    void persistenceUnitShouldCreateAndStoreEntity() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("personPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Person p = new Person("Test User", 29, "Pune");
            em.persist(p);
            em.getTransaction().commit();

            assertNotNull(p.getId());
            Person stored = em.find(Person.class, p.getId());
            assertNotNull(stored);
            assertEquals("Test User", stored.getName());
        } finally {
            em.close();
            emf.close();
        }
    }
}
