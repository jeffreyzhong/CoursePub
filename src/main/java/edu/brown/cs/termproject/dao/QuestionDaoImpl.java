package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionDaoImpl implements QuestionDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void add(Question question) {
    entityManager.persist(question);
  }

  @Override
  public Question ofId(String id) {
    return entityManager.find(Question.class, id);
  }
}
