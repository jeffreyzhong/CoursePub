package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.UserDao;
import edu.brown.cs.termproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private UserDao userDao;

  @Autowired
  public UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> getAllUsers() {
    return userDao.getAllUsers();
  }

  @Override
  @Transactional(readOnly = false)
  public User add(String email) {
    if (userDao.hasEmail(email)) {
      throw new IllegalArgumentException("The email has already been taken.");
    }

    User user = new User();
    user.setEmail(email);

    userDao.add(user);

    return user;
  }

  @Override
  @Transactional(readOnly = true)
  public User ofId(Integer id) {
    return userDao.ofId(id);
  }
}
