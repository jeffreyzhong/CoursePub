package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.QuestionUpvote;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionUpvoteDaoImpl
    extends AbstractUpvoteDaoImpl<Question, QuestionUpvote>
    implements QuestionUpvoteDao {

  @Override
  protected String upvoteClassSimpleName() {
    return "QuestionUpvote";
  }
}
