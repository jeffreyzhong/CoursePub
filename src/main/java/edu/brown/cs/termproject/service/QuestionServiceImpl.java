package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.QuestionDao;
import edu.brown.cs.termproject.dao.QuestionUpvoteDao;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.QuestionUpvote;
import edu.brown.cs.termproject.model.Sentence;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.model.Video;
import edu.brown.cs.termproject.nlp.TfIdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

  private static final Double SIMILAR_THRESHOLD = 0.5;

  private QuestionDao questionDao;
  private QuestionUpvoteDao questionUpvoteDao;
  private TfIdf<Video, Sentence, Question> tfIdf;

  @Autowired
  public QuestionServiceImpl(QuestionDao questionDao,
                             QuestionUpvoteDao questionUpvoteDao) {
    this.questionDao = questionDao;
    this.questionUpvoteDao = questionUpvoteDao;
    this.tfIdf = tfIdf();
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
  public Question ofId(Integer id) {
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

  @Override
  @Transactional(readOnly = true)
  public Map<Question, Double> similar(Question question)
      throws IllegalArgumentException {
    if (question == null || question.getVideo() == null) {
      throw new IllegalArgumentException("Question or video cannot be null.");
    }

   return tfIdf.getScore(question.getVideo(), question.getTitle());
  }

  @Bean
  public TfIdf<Video, Sentence, Question> tfIdf() {
    return new TfIdf<>();
  }
}
