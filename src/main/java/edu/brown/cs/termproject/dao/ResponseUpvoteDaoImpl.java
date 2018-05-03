package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.ResponseUpvote;
import org.springframework.stereotype.Repository;

@Repository
public class ResponseUpvoteDaoImpl
    extends AbstractUpvoteDaoImpl<Response, ResponseUpvote>
    implements ResponseUpvoteDao {

  @Override
  protected String upvoteClassSimpleName() {
    return "ResponseUpvote";
  }
}
