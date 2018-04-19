package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.User;

import java.util.List;

public interface UserDao {

  List<User> getAllUsers();
}
