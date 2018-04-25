package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Remark;
import edu.brown.cs.termproject.model.RemarkUpvote;
import edu.brown.cs.termproject.model.User;

public interface RemarkUpvoteService {

  RemarkUpvote add(User user, Remark remark);
}
