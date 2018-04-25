package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.QuestionUpvote;
import edu.brown.cs.termproject.model.User;

public interface QuestionUpvoteService {

  QuestionUpvote add(User user, Question question);
}
