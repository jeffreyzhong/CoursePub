package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.User;

import java.util.List;

public interface UserDao {

  List<User> getAllUsers();

  void add(User user);

  boolean hasEmail(String email);

  User ofId(Integer id);

  User ofEmail(String email);
}
