package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.User;

public interface ResponseService {

  Response add(User user, Question question, String body);
}
