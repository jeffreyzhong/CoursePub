package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dto.QuestionDto;
import edu.brown.cs.termproject.dto.ResponseDto;
import edu.brown.cs.termproject.dto.UpvoteDto;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
  public void newQuestion(User user, QuestionDto questionDto) {

  }

  @Override
  public void newAnswer(User user, ResponseDto responseDto)
      throws IllegalArgumentException {
    Question question = questionService.ofId(responseDto.getQuestionId());

    if (question == null) {
      throw new IllegalArgumentException(String.format(
          "Question of id %d is not found.", responseDto.getQuestionId()));
    }

    Response response = responseService.add(user, question,
        responseDto.getDetail());

    responseDto.fill(response);
  }

  @Override
  public void upvote(User user, UpvoteDto upvoteDto) {

  }
}
