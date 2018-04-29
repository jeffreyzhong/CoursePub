package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Remark;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RemarkDaoImpl implements RemarkDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void add(Remark remark) {
    entityManager.persist(remark);
  }
}
