package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.QuestionUpvote;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionUpvoteDaoImpl implements QuestionUpvoteDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void add(QuestionUpvote questionUpvote) {
    entityManager.persist(questionUpvote);
  }
}
