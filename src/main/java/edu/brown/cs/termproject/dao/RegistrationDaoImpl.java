package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Registration;
import edu.brown.cs.termproject.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class RegistrationDaoImpl implements RegistrationDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void add(Registration registration) {
    entityManager.persist(registration);
  }

  @Override
  public boolean exists(User user, Course course) {
    String ql =
        "SELECT COUNT(*) FROM Registration WHERE user = ?1 AND course = ?2";
    Query query = entityManager.createQuery(ql);
    query.setParameter(1, user);
    query.setParameter(2, course);

    Long ret = (Long) query.getSingleResult();
    assert (ret <= 1);

    return ret > 0;
  }

  @Override
  public Registration get(User user, Course course) {
    String ql = "SELECT r FROM Registration r WHERE user = ?1 AND course = ?2";
    Query query = entityManager.createQuery(ql);
    query.setParameter(1, user);
    query.setParameter(2, course);

    Registration registration = (Registration) query.getSingleResult();

    return registration;
  }
}
