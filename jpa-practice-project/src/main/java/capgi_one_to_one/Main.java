package capgi_one_to_one;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("personPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Pan pan = new Pan("ABCD123");
            em.persist(pan);
            Person person = new Person("Rahul");
            person.setPan(pan);
            em.persist(person);
            em.getTransaction().commit();

            System.out.println("Person & PAN Saved");
            Person fetchedPerson = em.find(Person.class, person.getId());
            System.out.println(fetchedPerson.getName());
            System.out.println(fetchedPerson.getPan().getPanNumber());

            em.getTransaction().begin();
            Human human = new Human("Sourav");
            Passport passport = new Passport("IND123");
            human.setPassport(passport);
            em.persist(passport);
            em.persist(human);
            em.getTransaction().commit();

            Human fetchedHuman = em.find(Human.class, human.getId());
            System.out.println(fetchedHuman.getName());
            System.out.println(fetchedHuman.getPassport().getPassportNumber());

            Passport fetchedPassport = em.find(Passport.class, passport.getId());
            System.out.println("Passport belongs to: " + fetchedPassport.getHuman().getName());
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
            emf.close();
        }
    }
}
