package model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityUtil {

    private static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

}
