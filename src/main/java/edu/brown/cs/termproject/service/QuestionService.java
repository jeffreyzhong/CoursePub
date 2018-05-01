package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.QuestionUpvote;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.model.Video;

import java.util.Calendar;

public interface QuestionService
    extends AbstractUpvoteService<Question, QuestionUpvote> {

  Question add(User user, Calendar videoTime, String title, String body, Video video);

  Question ofId(Integer id);
}
