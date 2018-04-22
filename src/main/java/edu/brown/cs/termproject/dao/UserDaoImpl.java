package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

  @PersistenceContext
  private EntityManager entityManager;

  public UserDaoImpl() {

  }

  @Override
  public List<User> getAllUsers() {
    System.out.println(entityManager.find(User.class, 1));
    String ql = "FROM User";
    return entityManager.createQuery(ql).getResultList();
  }

  @Override
  public void add(User user) {
    entityManager.persist(user);
  }

  @Override
  public boolean hasEmail(String email) {
    String ql = "SELECT u FROM User u WHERE u.email = ?1";
    List ret =
        entityManager.createQuery(ql).setParameter(1, email).getResultList();

    return ret.size() > 0;
  }

  @Override
  public User ofId(Integer id) {
    return entityManager.find(User.class, id);
  }
}
