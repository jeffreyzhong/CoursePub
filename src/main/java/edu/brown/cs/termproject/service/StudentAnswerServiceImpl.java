package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.RegistrationDao;
import edu.brown.cs.termproject.dao.StudentAnswerDao;
import edu.brown.cs.termproject.dao.StudentAnswerUpvoteDao;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.StudentAnswer;
import edu.brown.cs.termproject.model.StudentAnswerUpvote;
import edu.brown.cs.termproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service
public class StudentAnswerServiceImpl implements StudentAnswerService {

  private StudentAnswerDao studentAnswerDao;
  private StudentAnswerUpvoteDao studentAnswerUpvoteDao;
  private RegistrationService registrationService;

  @Autowired
  public StudentAnswerServiceImpl(StudentAnswerDao studentAnswerDao,
                                  StudentAnswerUpvoteDao studentAnswerUpvoteDao,
                                  RegistrationService registrationService) {
    this.studentAnswerDao = studentAnswerDao;
    this.studentAnswerUpvoteDao = studentAnswerUpvoteDao;
    this.registrationService = registrationService;
  }

  @Override
  @Transactional(readOnly = false)
  public StudentAnswer add(User user, Question question, String body) {
    if (question.getStudentAnswer() != null) {
      throw new UnsupportedOperationException("Editing is not supported.");
    }

    StudentAnswer studentAnswer = new StudentAnswer();

    studentAnswer.setUser(user);
    studentAnswer.setQuestion(question);
    studentAnswer.setBody(body);
    studentAnswer.setPostTime(Calendar.getInstance());

    studentAnswerDao.add(studentAnswer);

    question.setStudentAnswer(studentAnswer);

    return studentAnswer;
  }

  @Override
  public StudentAnswerUpvote upvote(User user, StudentAnswer studentAnswer) {
    if (studentAnswerUpvoteDao.exists(user, studentAnswer)) {
      throw new IllegalArgumentException(
          String.format("%s has already upvoted %s.", user, studentAnswer));
    }

    StudentAnswerUpvote studentAnswerUpvote = new StudentAnswerUpvote();

    studentAnswerUpvote.setUser(user);
    studentAnswerUpvote.setPost(studentAnswer);

    studentAnswerUpvoteDao.add(studentAnswerUpvote);

    return studentAnswerUpvote;
  }
}
