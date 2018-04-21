package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserDaoImpl implements UserDao {

  @PersistenceContext
  private EntityManager entityManager;

  public UserDaoImpl() {

  }

  @Override
  public List<User> getAllUsers() {
    System.out.println(entityManager.find(User.class, 1));
    String hql = "FROM User";
    return entityManager.createQuery(hql).getResultList();
  }

  @Override
  @Transactional(readOnly = false)
  public void add(User user) {
    entityManager.persist(user);
  }
}
