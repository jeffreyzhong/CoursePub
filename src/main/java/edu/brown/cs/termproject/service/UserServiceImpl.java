package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.UserDao;
import edu.brown.cs.termproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private UserDao userDao;

  @Autowired
  public UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public List<User> getAllUsers() {
    return userDao.getAllUsers();
  }

  @Override
  public void add(@NotNull User user) {
    userDao.add(user);
  }
}
