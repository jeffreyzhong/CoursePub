package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.User;

import java.util.List;

public interface UserService {

  List<User> getAllUsers();

  User add(String email);

  User ofId(Integer id);

  User getOrAdd(String email);
}
