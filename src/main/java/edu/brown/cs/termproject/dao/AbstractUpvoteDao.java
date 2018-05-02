package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.AbstractPost;
import edu.brown.cs.termproject.model.AbstractUpvote;
import edu.brown.cs.termproject.model.User;

public interface AbstractUpvoteDao<T extends AbstractPost<? extends U>,
    U extends AbstractUpvote<? extends T>> {

  void add(U upvote);

  boolean exists(User user, T post);
}
