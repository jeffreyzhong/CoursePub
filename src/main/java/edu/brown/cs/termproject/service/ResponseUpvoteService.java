package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.ResponseUpvote;
import edu.brown.cs.termproject.model.User;

public interface ResponseUpvoteService {

  ResponseUpvote add(User user, Response response);
}
