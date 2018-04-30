package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.QuestionUpvote;
import edu.brown.cs.termproject.model.User;

public interface QuestionUpvoteDao {

  void add(QuestionUpvote questionUpvote);

  boolean exists(User user, Question question);
}
