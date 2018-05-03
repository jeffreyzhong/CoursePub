package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.InstructorAnswer;
import edu.brown.cs.termproject.model.InstructorAnswerUpvote;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.model.Video;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class InstructorAnswerServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private CourseService courseService;

  @Autowired
  private VideoService videoService;

  @Autowired
  private QuestionService questionService;

  @Autowired
  private InstructorAnswerService instructorAnswerService;

  @Autowired
  private RegistrationService registrationService;

  @Test
  public void testAdd() {
    Course course = courseService.ofId(1);
    User user = userService.add("jj@cs.brown.edu");
    registrationService.add(user, course, 1);
    Video video = videoService.add("https://www.apple.com", course);
    Question question = questionService.add(user, Calendar.getInstance(),
        "title", "body", video);
    String body = "hello";

    InstructorAnswer instructorAnswer = instructorAnswerService.add(user, question, body);

    assertEquals(user, instructorAnswer.getUser());
    assertEquals(question, instructorAnswer.getQuestion());
    assertEquals(instructorAnswer, question.getInstructorAnswer());
    assertNotNull(instructorAnswer.getId());
  }

  @Test
  public void testUpvote() {
    Course course = courseService.ofId(1);
    User user = userService.add("jj@cs.brown.edu");
    registrationService.add(user, course, 1);
    Video video = videoService.add("https://www.apple.com", course);
    Question question = questionService.add(user, Calendar.getInstance(),
        "title", "body", video);
    String body = "hello";

    InstructorAnswer instructorAnswer = instructorAnswerService.add(user, question, body);
    InstructorAnswerUpvote instructorAnswerUpvote = instructorAnswerService.upvote(user, instructorAnswer);

    assertEquals(user, instructorAnswerUpvote.getUser());
    assertEquals(instructorAnswer, instructorAnswerUpvote.getPost());
    assertNotNull(instructorAnswerUpvote.getId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpvoteTwice() {
    Course course = courseService.ofId(1);
    User user = userService.add("jj@cs.brown.edu");
    registrationService.add(user, course, 1);
    Video video = videoService.add("https://www.apple.com", course);
    Question question = questionService.add(user, Calendar.getInstance(),
        "title", "body", video);
    String body = "hello";

    InstructorAnswer instructorAnswer = instructorAnswerService.add(user, question, body);
    instructorAnswerService.upvote(user, instructorAnswer);
    instructorAnswerService.upvote(user, instructorAnswer);
  }
}
