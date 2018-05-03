package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.StudentAnswer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class StudentAnswerDaoImpl implements StudentAnswerDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void add(StudentAnswer studentAnswer) {
    entityManager.persist(studentAnswer);
  }
}
