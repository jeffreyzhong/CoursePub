package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.AbstractPost;
import edu.brown.cs.termproject.model.AbstractUpvote;
import edu.brown.cs.termproject.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public abstract class AbstractUpvoteDaoImpl<T extends AbstractPost<? extends U>,
    U extends AbstractUpvote<? extends T>> implements AbstractUpvoteDao<T, U> {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void add(U upvote) {
    entityManager.persist(upvote);
  }

  @Override
  public boolean exists(User user, T post) {
    String ql = String.format(
        "SELECT COUNT(e) FROM %s e WHERE e.user = ?1 AND e.post = ?2",
        upvoteClassSimpleName());
    Query query = entityManager.createQuery(ql);
    query.setParameter(1, user);
    query.setParameter(2, post);

    Long ret = (Long) query.getSingleResult();
    assert (ret <= 1);

    return ret > 0;
  }

  protected abstract String upvoteClassSimpleName();
}
