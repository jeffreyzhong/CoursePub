package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.RemarkUpvote;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RemarkUpvoteDaoImpl implements RemarkUpvoteDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void add(RemarkUpvote remarkUpvote) {
    entityManager.persist(remarkUpvote);
  }
}
