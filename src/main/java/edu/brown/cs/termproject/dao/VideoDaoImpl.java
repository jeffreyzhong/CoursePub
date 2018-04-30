package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Video;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class VideoDaoImpl implements VideoDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void add(Video video) {
    entityManager.persist(video);
  }

  @Override
  public Video ofId(Integer id) {
    return entityManager.find(Video.class, id);
  }
}
