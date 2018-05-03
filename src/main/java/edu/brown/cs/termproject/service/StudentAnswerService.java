package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.StudentAnswer;
import edu.brown.cs.termproject.model.StudentAnswerUpvote;
import edu.brown.cs.termproject.model.User;

public interface StudentAnswerService
    extends AbstractUpvoteService<StudentAnswer, StudentAnswerUpvote> {

  StudentAnswer add(User user, Question question, String body);
}
