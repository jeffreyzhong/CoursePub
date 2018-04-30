package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.QuestionDao;
import edu.brown.cs.termproject.dao.QuestionUpvoteDao;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.QuestionUpvote;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service
public class QuestionServiceImpl implements QuestionService {

  private QuestionDao questionDao;
  private QuestionUpvoteDao questionUpvoteDao;

  @Autowired
  public QuestionServiceImpl(QuestionDao questionDao,
                             QuestionUpvoteDao questionUpvoteDao) {
    this.questionDao = questionDao;
    this.questionUpvoteDao = questionUpvoteDao;
  }

  @Override
  @Transactional(readOnly = false)
  public Question add(User user, Calendar videoTime, String title, String body,
                      Video video) {
    Question question = new Question();

    question.setUser(user);
    question.setVideoTime(videoTime);
    question.setTitle(title);
    question.setBody(body);
    question.setVideo(video);
    question.setPostTime(Calendar.getInstance());

    questionDao.add(question);

    video.addQuestion(question);

    return question;
  }

  @Override
  @Transactional(readOnly = false)
  public Question ofId(String id) {
    return questionDao.ofId(id);
  }

  @Override
  @Transactional(readOnly = false)
  public QuestionUpvote upvote(User user, Question question)
      throws IllegalArgumentException {
    if (questionUpvoteDao.exists(user, question)) {
      throw new IllegalArgumentException(
          String.format("%s has already upvoted %s.", user, question));
    }

    QuestionUpvote questionUpvote = new QuestionUpvote();
    questionUpvote.setUser(user);
    questionUpvote.setPost(question);

    questionUpvoteDao.add(questionUpvote);

    question.addUpvote(questionUpvote);

    return questionUpvote;
  }
}
