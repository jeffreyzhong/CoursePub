package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Registration;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional(readOnly = false)
public class RegistrationDaoImpl implements RegistrationDao {

  @PersistenceContext
  private EntityManager entityManager;


  @Override
  public void add(Registration registration) {
    entityManager.persist(registration);
  }
}
