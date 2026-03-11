package org.capgi;

import jakarta.persistence.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("personPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Person p1 = new Person("Sourav", 22, "Delhi");
            Person p2 = new Person("Rahul", 25, "Mumbai");
            em.persist(p1);
            em.persist(p2);
            tx.commit();

            System.out.println("Persons inserted\n");

            Person person = em.find(Person.class, p1.getId());
            System.out.println("Find by ID:");
            System.out.println(person + "\n");

            tx.begin();
            em.createQuery("update CityPerson p set p.age = :age where p.id = :id")
                    .setParameter("age", 30)
                    .setParameter("id", p1.getId())
                    .executeUpdate();
            tx.commit();

            System.out.println("After Update:");
            System.out.println(em.find(Person.class, p1.getId()) + "\n");

            List<Person> list = em.createQuery("from CityPerson", Person.class).getResultList();
            System.out.println("All Persons:");
            list.forEach(System.out::println);
            System.out.println();

            List<Person> filtered = em.createQuery("from CityPerson p where p.city = :city", Person.class)
                    .setParameter("city", "Delhi")
                    .getResultList();
            System.out.println("Filter by City (Delhi):");
            filtered.forEach(System.out::println);
            System.out.println();

            List<Person> sorted = em.createQuery("from CityPerson p order by p.age desc", Person.class)
                    .getResultList();
            System.out.println("Sorted by Age:");
            sorted.forEach(System.out::println);
            System.out.println();

            List<Person> nativeList = em.createNativeQuery("SELECT * FROM persons", Person.class).getResultList();
            System.out.println("Native Query:");
            nativeList.forEach(System.out::println);
            System.out.println();

            tx.begin();
            Person toDelete = em.find(Person.class, p2.getId());
            if (toDelete != null) {
                em.remove(toDelete);
            }
            tx.commit();

            System.out.println("After Delete:");
            em.createQuery("from CityPerson", Person.class).getResultList().forEach(System.out::println);
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
            emf.close();
        }
    }
}
