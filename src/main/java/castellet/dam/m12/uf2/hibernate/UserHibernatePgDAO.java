package castellet.dam.m12.uf2.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;


public class UserHibernatePgDAO {

    private final SessionFactory sessionFactory;

    public UserHibernatePgDAO(SessionFactory sessionFactory) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        this.sessionFactory = sessionFactory;
    }

    // Guardar un usuari
    public void add(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    // Obtenir tots els usuaris
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.getResultList();
        }
    }

    // Buscar usuaris per nom
    public List<User> getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE name LIKE :name", User.class);
            query.setParameter("name", "%" + name + "%");
            return query.getResultList();
        }
    }
}