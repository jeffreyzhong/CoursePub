package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Remark;
import edu.brown.cs.termproject.model.RemarkUpvote;
import edu.brown.cs.termproject.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class RemarkUpvoteDaoImpl implements RemarkUpvoteDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void add(RemarkUpvote remarkUpvote) {
    entityManager.persist(remarkUpvote);
  }

  @Override
  public boolean exists(User user, Remark remark) {
    String ql =
        "SELECT COUNT(*) FROM RemarkUpvote WHERE user = ?1 AND post = ?2";
    Query query = entityManager.createQuery(ql);
    query.setParameter(1, user);
    query.setParameter(2, remark);

    Long ret = (Long) query.getSingleResult();
    assert (ret <= 1);

    return ret > 0;
  }
}
