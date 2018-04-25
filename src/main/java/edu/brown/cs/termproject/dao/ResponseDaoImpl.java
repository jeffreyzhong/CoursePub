package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Response;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ResponseDaoImpl implements ResponseDao {

  @PersistenceContext
  private EntityManager entityManager;


  @Override
  public void add(Response response) {
    entityManager.persist(response);
  }
}
