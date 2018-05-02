package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.InstructorAnswer;
import edu.brown.cs.termproject.model.InstructorAnswerUpvote;
import org.springframework.stereotype.Repository;

@Repository
public class InstructorAnswerUpvoteDaoImpl
    extends AbstractUpvoteDaoImpl<InstructorAnswer, InstructorAnswerUpvote>
    implements InstructorAnswerUpvoteDao {

  @Override
  protected String upvoteClassSimpleName() {
    return "InstructorAnswerUpvote";
  }
}
