package DAO;

import exception.FaultlException;
import model.EntityUtil;
import model.Goal;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class GoalDAO {

    private static GoalDAO instance;
    protected EntityManager entityManager;

    public static GoalDAO getInstance() {
        if (instance == null) {
            instance = new GoalDAO();
        }
        return instance;
    }

    private GoalDAO() {
        entityManager = EntityUtil.getEntityManager();
    }

    public void persist(Goal goal) throws Exception {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(goal);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new Exception();
        }
    }

    public void merge(Goal goal) throws FaultlException {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(goal);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new FaultlException();
        }
    }

    public void delete(Goal goal) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(goal);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
    }

    public Goal findById(int id) {
        Goal goal = null;
        try {
            goal = entityManager.find(Goal.class, id);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return goal;
    }

//    @SuppressWarnings("unchecked")
    public List<Goal> findAll() {
        return entityManager.createQuery("select g from Goal g order by g.sequence", Goal.class).getResultList();
    }

    public Goal findByGoalName(String goalName) {
        Query query = entityManager.createQuery("select g from Goal g where goalName= :gN", Goal.class);
        query.setParameter("gN", goalName);
        entityManager.createQuery("select g from Goal g where goalName= :gN", Goal.class);
        return (Goal) query.getSingleResult();
    }

    public Goal findBySequence(int sequence) {
        Query query = entityManager.createQuery("select g from Goal g where sequence= :s", Goal.class);
        query.setParameter("s", sequence);
        entityManager.createQuery("select g from Goal g where sequence= :s", Goal.class);
        return (Goal) query.getSingleResult();
    }
    public int findMaxSeq() {
        Query query = entityManager.createQuery("select max(g.sequence) from Goal g");
        return query.getSingleResult() != null ? (int) query.getSingleResult() : -1;
    }

    public List<Goal> findAllActive() {
        return entityManager.createQuery("select g from Goal g where active=true order by g.sequence", Goal.class).getResultList();
    }

    public List<Goal> findAllInactive() {
        return entityManager.createQuery("select g from Goal g where active=false order by g.sequence", Goal.class).getResultList();
    }
    public void closeEntityManager() {
        entityManager.close();
    }
}


