package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.User;

import java.util.Map;

public interface SocketService {

  Map<String, Object> newQuestion(User user, Map<String, ?> payload);

  Map<String, Object> newAnswer(User user, Map<String, ?> payload);

  Map<String, Object> upvote(User user, Map<String, ?> payload);
}
