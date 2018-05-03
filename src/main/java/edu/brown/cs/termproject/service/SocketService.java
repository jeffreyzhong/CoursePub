package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dto.AnswerDto;
import edu.brown.cs.termproject.dto.QuestionDto;
import edu.brown.cs.termproject.dto.ResponseDto;
import edu.brown.cs.termproject.dto.UpvoteDto;
import edu.brown.cs.termproject.model.User;


public interface SocketService {

  void newQuestion(User user, QuestionDto questionDto);

  void newResponse(User user, ResponseDto responseDto);

  void upvote(User user, UpvoteDto upvoteDto);

  void instructorAnswer(User user, AnswerDto answerDto);

  void studentAnswer(User user, AnswerDto answerDto);
}
