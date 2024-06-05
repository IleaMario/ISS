package com.example.iss.repo;

import com.example.iss.domain.Sarcina;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class RepoHBSarcini implements RepoSarcini {

    private final SessionFactory sessionFactory;

    public RepoHBSarcini(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Sarcina sarcina) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(sarcina);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Sarcina sarcina = session.get(Sarcina.class, id);
            if (sarcina != null) {
                session.delete(sarcina);
                transaction.commit();
            } else {
                System.out.println("No sarcina found with id " + id);
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Sarcina findOne(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Sarcina.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Integer id, Sarcina sarcina) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Sarcina existingSarcina = session.get(Sarcina.class, id);
            if (existingSarcina != null) {
                existingSarcina.setDescriere(sarcina.getDescriere());
                existingSarcina.setId_angajat(sarcina.getId_angajat());
                session.update(existingSarcina);
                transaction.commit();
            } else {
                System.out.println("No sarcina found with id " + id);
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<Sarcina> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Sarcina", Sarcina.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
