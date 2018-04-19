package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserDaoImpl implements UserDao {

  private SessionFactory sessionFactory;

  @Autowired
  public UserDaoImpl(EntityManagerFactory entityManagerFactory) {
    this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
  }

  @Override
  public List<User> getAllUsers() {
    Session session = sessionFactory.openSession();
    String hql = "FROM User";
    Query<User> query = session.createQuery(hql);
    return query.list();
  }
}
