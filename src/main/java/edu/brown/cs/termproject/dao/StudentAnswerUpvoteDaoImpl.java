package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.StudentAnswer;
import edu.brown.cs.termproject.model.StudentAnswerUpvote;

public class StudentAnswerUpvoteDaoImpl
    extends AbstractUpvoteDaoImpl<StudentAnswer, StudentAnswerUpvote>
    implements StudentAnswerUpvoteDao {

  @Override
  protected String upvoteClassSimpleName() {
    return "StudentAnswerUpvote";
  }
}
