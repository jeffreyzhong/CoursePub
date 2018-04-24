package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.ResponseUpvote;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ResponseUpvoteDaoImpl implements ResponseUpvoteDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void add(ResponseUpvote responseUpvote) {
    entityManager.persist(responseUpvote);
  }
}
