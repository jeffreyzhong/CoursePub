package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserService {

  List<User> getAllUsers();

  void add(@NotNull User user);
}
