package edu.brown.cs.termproject.service;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.termproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SocketServiceImpl implements SocketService {

  private QuestionService questionService;
  private ResponseService responseService;

  @Autowired
  public SocketServiceImpl(QuestionService questionService,
                           ResponseService responseService) {
    this.questionService = questionService;
    this.responseService = responseService;
  }

  @Override
  public Map<String, Object> newQuestion(User user, Map<String, ?> payload) {
    return ImmutableMap.of("message", "NYI");
  }

  @Override
  public Map<String, Object> newAnswer(User user, Map<String, ?> payload) {
    return ImmutableMap.of("message", "NYI");
  }

  @Override
  public Map<String, Object> upvote(User user, Map<String, ?> payload) {
    return ImmutableMap.of("message", "NYI");
  }
}
