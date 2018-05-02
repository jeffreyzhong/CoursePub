package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.InstructorAnswer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class InstructorAnswerDaoImpl implements InstructorAnswerDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void add(InstructorAnswer instructorAnswer) {
    entityManager.persist(instructorAnswer);
  }
}
