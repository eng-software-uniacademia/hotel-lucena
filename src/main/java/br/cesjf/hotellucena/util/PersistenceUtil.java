package br.cesjf.hotellucena.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.eclipse.persistence.sessions.Session;

public class PersistenceUtil {

    private static final String PERSISTENCE_UNIT_NAME = "HotelLucena";
    private static EntityManagerFactory factory;
    private static final ThreadLocal<EntityManager> MANAGER = new ThreadLocal<>();
    private static Session session;

    private PersistenceUtil() { }

    static {
        if (factory == null) {
            try {
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            } catch (Exception e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    public static EntityManager getEntityManager() {
        EntityManager em = MANAGER.get();

        if (em == null) {
            em = factory.createEntityManager();
            MANAGER.set(em);
        }
        return em;
    }

    public static void closeEntityManager() {
        EntityManager em = MANAGER.get();

        if (em != null) {
            em.close();
        }
        MANAGER.remove();
    }

    public static Session getSession() {
        if (session == null) {
            session = (Session) getEntityManager().getDelegate();
        }
        return session;
    }
}
