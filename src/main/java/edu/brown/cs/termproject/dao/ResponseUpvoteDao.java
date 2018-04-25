package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.ResponseUpvote;
import edu.brown.cs.termproject.model.User;

public interface ResponseUpvoteDao {

  void add(ResponseUpvote responseUpvote);

  boolean exists(User user, Response response);
}
