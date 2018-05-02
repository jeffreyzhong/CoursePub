package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Registration;
import edu.brown.cs.termproject.model.User;

public interface RegistrationService {

  Registration add(User user, Course course, Integer type);

  boolean isInstructor(User user, Course course);
}
