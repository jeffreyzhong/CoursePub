package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.QuestionUpvote;
import edu.brown.cs.termproject.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class QuestionUpvoteDaoImpl implements QuestionUpvoteDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void add(QuestionUpvote questionUpvote) {
    entityManager.persist(questionUpvote);
  }

  @Override
  public boolean exists(User user, Question question) {
    String ql =
        "SELECT COUNT(*) FROM QuestionUpvote WHERE user = ?1 AND post = ?2";
    Query query = entityManager.createQuery(ql);
    query.setParameter(1, user);
    query.setParameter(2, question);

    Long ret = (Long) query.getSingleResult();
    assert (ret <= 1);

    return ret > 0;
  }
}
