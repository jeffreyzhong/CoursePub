package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Registration;
import edu.brown.cs.termproject.model.User;

public interface RegistrationDao {

  void add(Registration registration);

  boolean exists(User user, Course course);
}
