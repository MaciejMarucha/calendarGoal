package DAO;

import model.MaxSuccessNumber;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class MaxSuccessNumberDAO {
    private static MaxSuccessNumberDAO instance;
    protected EntityManager entityManager;

    public static MaxSuccessNumberDAO getInstance() {
        if (instance == null) {
            instance = new MaxSuccessNumberDAO();
        }

        return instance;
    }

    private MaxSuccessNumberDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("calendargoals");

        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public void persist(MaxSuccessNumber maxSuccessNumber) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(maxSuccessNumber);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(MaxSuccessNumber maxSuccessNumber) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(maxSuccessNumber);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
    }

    public void delete(MaxSuccessNumber maxSuccessNumber) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(maxSuccessNumber);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
    }

    public MaxSuccessNumber findById(int id) {
        MaxSuccessNumber maxSuccessNumber = null;
        try {
            maxSuccessNumber = entityManager.find(MaxSuccessNumber.class, id);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return maxSuccessNumber;
    }
    //skasowac tę adnotację
//    @SuppressWarnings("unchecked")
    public List<MaxSuccessNumber> findAll() {
        return entityManager.createQuery("FROM " + MaxSuccessNumber.class.getName()).getResultList();
    }

    public void closeEntityManager() {
        entityManager.close();
    }
}
