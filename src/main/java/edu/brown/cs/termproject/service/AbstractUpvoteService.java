package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.AbstractPost;
import edu.brown.cs.termproject.model.AbstractUpvote;
import edu.brown.cs.termproject.model.User;

public interface AbstractUpvoteService<T extends AbstractPost,
    U extends AbstractUpvote<? extends T>> {

  U upvote(User user, T post);
}
