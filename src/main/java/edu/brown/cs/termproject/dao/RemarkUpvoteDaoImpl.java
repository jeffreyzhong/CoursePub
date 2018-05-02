package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Remark;
import edu.brown.cs.termproject.model.RemarkUpvote;
import org.springframework.stereotype.Repository;

@Repository
public class RemarkUpvoteDaoImpl
    extends AbstractUpvoteDaoImpl<Remark, RemarkUpvote>
    implements RemarkUpvoteDao {

  @Override
  protected String upvoteClassSimpleName() {
    return "RemarkUpvote";
  }
}
