package com.example.iss.repo;

import com.example.iss.domain.Angajat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Objects;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class RepoHBAngajati implements RepoAngajati{

    private SessionFactory sessionFactory;

    public RepoHBAngajati(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }



    @Override
    public void save(Angajat angajat) {
        //HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(angajat));
    }

    @Override
    public void delete(Integer id) {
        /*HibernateUtils.getSessionFactory().inTransaction(session -> {
            Angajat angajat = session.createQuery("from Angajat where id=?1", Angajat.class)
                    .setParameter(1, id)
                    .uniqueResult();
            System.out.println("In delete am gasit angajatul " + angajat);
            if (angajat != null) {
                session.remove(angajat);
                session.flush();
            }
        });*/
    }

    @Override
    public Angajat findOne(Integer id) {
        /*try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Angajat where id=:idA", Angajat.class)
                    .setParameter("idA", id)
                    .getSingleResultOrNull();
        }*/
        return null;
    }

    @Override

    public void update(Integer id, Angajat angajat) {
        Transaction transact = null;
        String nume = angajat.getNume();
        System.out.println("Attempting to update employee: " + angajat.getNume() + " with status: " + angajat.getStatus());
        try (Session session = sessionFactory.openSession()) {
            try {
                transact = session.beginTransaction();
                String sql = "UPDATE Angajat SET status = :status, oraLogare = :ora_logare WHERE nume = :nume";
                Query query = session.createQuery(sql);
                query.setParameter("status", angajat.getStatus());
                query.setParameter("ora_logare", angajat.getOraLogare()); // Use the method parameter `id`

                query.setParameter("nume", nume); // Use the method parameter `id`

                int result = query.executeUpdate();
                if (result > 0) {
                    System.out.println("Successfully updated employee with id " + id);
                } else {
                    System.out.println("No employee found with id " + id);
                }
                transact.commit();
            } catch (Exception e) {
                System.err.println("Error during update: " + e.getMessage());
                if (transact != null) {
                    transact.rollback();
                    System.err.println("Transaction rolled back due to error.");
                }
                e.printStackTrace(); // Print stack trace for debugging
            }
        } catch (Exception e) {
            System.err.println("Error opening session: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    @Override
    public Iterable<Angajat> getAll() {
        System.out.println("toti angajatii:");
        try (var session = sessionFactory.openSession()) {
            return session.createQuery("SELECT A FROM Angajat A", Angajat.class).list();
        } catch (Exception e) {
            System.out.println("eroare la getAll angajati: " + e.getMessage());
            e.printStackTrace(); // Pentru a afișa stack trace-ul complet al erorii
            return List.of();
        }
    }


}



