package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.InstructorAnswerDao;
import edu.brown.cs.termproject.dao.InstructorAnswerUpvoteDao;
import edu.brown.cs.termproject.model.InstructorAnswer;
import edu.brown.cs.termproject.model.InstructorAnswerUpvote;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service
public class InstructorAnswerServiceImpl implements InstructorAnswerService {

  private InstructorAnswerDao instructorAnswerDao;
  private InstructorAnswerUpvoteDao instructorAnswerUpvoteDao;
  private RegistrationService registrationService;

  @Autowired
  public InstructorAnswerServiceImpl(InstructorAnswerDao instructorAnswerDao,
                                     InstructorAnswerUpvoteDao instructorAnswerUpvoteDao,
                                     RegistrationService registrationService) {
    this.instructorAnswerDao = instructorAnswerDao;
    this.instructorAnswerUpvoteDao = instructorAnswerUpvoteDao;
    this.registrationService = registrationService;
  }

  @Override
  @Transactional(readOnly = false)
  public InstructorAnswer add(User user, Question question, String body)
      throws UnsupportedOperationException, IllegalArgumentException {
    if (question.getInstructorAnswer() != null) {
      throw new UnsupportedOperationException("Editing is not supported.");
    }

    if (!registrationService.isInstructor(user, question.getVideo().getCourse())) {
      throw new IllegalArgumentException("%s is not authorized to add answer.");
    }

    InstructorAnswer instructorAnswer = new InstructorAnswer();

    instructorAnswer.setUser(user);
    instructorAnswer.setQuestion(question);
    instructorAnswer.setBody(body);
    instructorAnswer.setPostTime(Calendar.getInstance());

    instructorAnswerDao.add(instructorAnswer);

    question.setInstructorAnswer(instructorAnswer);

    return instructorAnswer;
  }

  @Override
  @Transactional(readOnly = false)
  public InstructorAnswerUpvote upvote(User user,
                                       InstructorAnswer instructorAnswer) {
    if (instructorAnswerUpvoteDao.exists(user, instructorAnswer)) {
      throw new IllegalArgumentException(
          String.format("%s has already upvoted %s.", user, instructorAnswer));
    }

    InstructorAnswerUpvote instructorAnswerUpvote = new InstructorAnswerUpvote();

    instructorAnswerUpvote.setUser(user);
    instructorAnswerUpvote.setPost(instructorAnswer);

    instructorAnswerUpvoteDao.add(instructorAnswerUpvote);

    return instructorAnswerUpvote;
  }
}
