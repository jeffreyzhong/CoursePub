package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.model.Video;

import java.util.Calendar;
import java.util.Date;

public interface QuestionService {

  Question add(User user, Date time, String title, String body, Video video);

  Question ofId(String id);
}