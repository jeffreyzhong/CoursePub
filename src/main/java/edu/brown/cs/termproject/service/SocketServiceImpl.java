package edu.brown.cs.termproject.service;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.termproject.dto.QuestionDto;
import edu.brown.cs.termproject.dto.ResponseDto;
import edu.brown.cs.termproject.dto.UpvoteDto;
import edu.brown.cs.termproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = false)
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
  public QuestionDto newQuestion(User user, QuestionDto questionDto) {
    return null;
  }

  @Override
  public ResponseDto newAnswer(User user, ResponseDto responseDto) {
    return null;
  }

  @Override
  public UpvoteDto upvote(User user, UpvoteDto upvoteDto) {
    return null;
  }
}
