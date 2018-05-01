package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.time.CalendarSerializer;
import edu.brown.cs.termproject.dto.QuestionDto;
import edu.brown.cs.termproject.dto.ResponseDto;
import edu.brown.cs.termproject.dto.UpvoteDto;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;


@Service
@Transactional(readOnly = false)
public class SocketServiceImpl implements SocketService {

  private QuestionService questionService;
  private ResponseService responseService;
  private VideoService videoService;

  @Autowired
  public SocketServiceImpl(QuestionService questionService,
                           ResponseService responseService,
                           VideoService videoService) {
    this.questionService = questionService;
    this.responseService = responseService;
    this.videoService = videoService;
  }

  @Override
  public void newQuestion(User user, QuestionDto questionDto)
      throws IllegalArgumentException {
    Calendar videoTime =
        CalendarSerializer.toCalendar(questionDto.getQuestionTimestamp());
    Video video = videoService.ofId(questionDto.getVideoId());

    if (video == null) {
      throw new IllegalArgumentException(String.format(
          "Video of id %d is not found.", questionDto.getVideoId()));
    }

    Question question = questionService.add(user, videoTime,
        questionDto.getSummary(), questionDto.getDetail(), video);
    questionDto.fill(question);
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
