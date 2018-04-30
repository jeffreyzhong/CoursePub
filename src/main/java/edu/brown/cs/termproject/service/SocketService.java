package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dto.QuestionDto;
import edu.brown.cs.termproject.dto.ResponseDto;
import edu.brown.cs.termproject.dto.UpvoteDto;
import edu.brown.cs.termproject.model.User;


public interface SocketService {

  QuestionDto newQuestion(User user, QuestionDto questionDto);

  ResponseDto newAnswer(User user, ResponseDto responseDto);

  UpvoteDto upvote(User user, UpvoteDto upvoteDto);
}
