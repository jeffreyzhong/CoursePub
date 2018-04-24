package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.QuestionDao;
import edu.brown.cs.termproject.dao.QuestionUpvoteDao;
import edu.brown.cs.termproject.dao.UserDao;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.QuestionUpvote;
import edu.brown.cs.termproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionUpvoteServiceImpl implements QuestionUpvoteService {

  private QuestionUpvoteDao questionUpvoteDao;

  @Autowired
  public QuestionUpvoteServiceImpl(QuestionUpvoteDao questionUpvoteDao) {
    this.questionUpvoteDao = questionUpvoteDao;
  }

  @Override
  @Transactional(readOnly = false)
  public QuestionUpvote add(User user, Question question) {
    QuestionUpvote questionUpvote = new QuestionUpvote();

    questionUpvote.setUser(user);
    questionUpvote.setQuestion(question);

    questionUpvoteDao.add(questionUpvote);

    return questionUpvote;
  }
}
