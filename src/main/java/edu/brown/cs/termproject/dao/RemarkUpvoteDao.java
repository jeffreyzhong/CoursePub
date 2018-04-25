package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Remark;
import edu.brown.cs.termproject.model.RemarkUpvote;
import edu.brown.cs.termproject.model.User;

public interface RemarkUpvoteDao {

  void add(RemarkUpvote remarkUpvote);

  boolean exists(User user, Remark remark);
}
