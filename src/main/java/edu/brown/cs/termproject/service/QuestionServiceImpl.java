package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.QuestionDao;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
public class QuestionServiceImpl implements QuestionService {

  private QuestionDao questionDao;

  @Autowired
  public QuestionServiceImpl(QuestionDao questionDao) {
    this.questionDao = questionDao;
  }

  @Override
  @Transactional(readOnly = false)
  public Question add(User user, Date time, String title, String body,
                      Video video) {
    Question question = new Question();

    question.setUser(user);
    question.setTime(time);
    question.setTitle(title);
    question.setBody(body);
    question.setVideo(video);

    questionDao.add(question);

    return question;
  }

  @Override
  @Transactional(readOnly = true)
  public Question ofId(String id) {
    return questionDao.ofId(id);
  }
}
