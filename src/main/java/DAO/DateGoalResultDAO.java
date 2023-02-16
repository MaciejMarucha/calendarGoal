package DAO;

import model.DateGoalResult;
import model.EntityUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

public class DateGoalResultDAO {

    private static DateGoalResultDAO instance;
    protected EntityManager entityManager;

    private DateGoalResultDAO() {
        entityManager = EntityUtil.getEntityManager();
    }

    public static DateGoalResultDAO getInstance() {
        if (instance == null) {
            instance = new DateGoalResultDAO();
        }

        return instance;
    }

    public void persist(DateGoalResult dateGoalResult) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(dateGoalResult);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(DateGoalResult dateGoalResult) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(dateGoalResult);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
    }

    public void delete(DateGoalResult dateGoalResult) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(dateGoalResult);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public DateGoalResult findById(int id) {
        DateGoalResult dateGoalResult = null;
        try {
            dateGoalResult = entityManager.find(DateGoalResult.class, id);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return dateGoalResult;
    }

    //skasowac tę adnotację
//    @SuppressWarnings("unchecked")
    public List<DateGoalResult> findAll() {
        return entityManager.createQuery("select d from DateGoalResult d", DateGoalResult.class).getResultList();
    }

    public List<DateGoalResult> findAllByGoalId(Integer goalId) {
        Query query = entityManager.createQuery("select d from DateGoalResult d where d.goal.id = :gi order by localDate", DateGoalResult.class);
        query.setParameter("gi", goalId);

        return query.getResultList();
    }

    public List<DateGoalResult> findByLocalDateAndGoalId(LocalDate localDate, Integer goalId) {
        Query query = entityManager.createQuery("select d from DateGoalResult d where d.goal.id = :goalId and d.localDate = :ld", DateGoalResult.class);
        query.setParameter("ld", localDate);
        query.setParameter("goalId", goalId);

        return query.getResultList();
    }

    public List<DateGoalResult> findAllByGoalIdAndLocalDateBetween(Integer goalId, LocalDate localDate1, LocalDate localDate2) {
        Query query = entityManager.createQuery("select d from DateGoalResult d where d.goal.id = :goalId and d.localDate between :ld1 and :ld2 order by localDate", DateGoalResult.class);
        query.setParameter("goalId", goalId);
        query.setParameter("ld1", localDate1);
        query.setParameter("ld2", localDate2);

        return query.getResultList();
    }

    public LocalDate selectLastDateForGoalId(Integer goalId) {
        Query query = entityManager.createQuery(
                "SELECT max(d.localDate) FROM DateGoalResult d WHERE d.goal.id = :goalId");
        query.setParameter("goalId", goalId);
        return (LocalDate) query.getSingleResult();
    }

    public void closeEntityManager() {
        entityManager.close();
    }
}
