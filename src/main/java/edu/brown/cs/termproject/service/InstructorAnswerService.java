package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.InstructorAnswer;
import edu.brown.cs.termproject.model.InstructorAnswerUpvote;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.User;

public interface InstructorAnswerService
    extends AbstractUpvoteService<InstructorAnswer, InstructorAnswerUpvote> {

  InstructorAnswer add(User user, Question question, String body);
}
