package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.ResponseUpvote;
import edu.brown.cs.termproject.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class ResponseUpvoteDaoImpl implements ResponseUpvoteDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void add(ResponseUpvote responseUpvote) {
    entityManager.persist(responseUpvote);
  }

  @Override
  public boolean exists(User user, Response response) {
    String ql =
        "SELECT COUNT(*) FROM ResponseUpvote WHERE user = ?1 AND post = ?2";
    Query query = entityManager.createQuery(ql);
    query.setParameter(1, user);
    query.setParameter(2, response);

    Long ret = (Long) query.getSingleResult();
    assert (ret <= 1);

    return ret > 0;
  }
}
