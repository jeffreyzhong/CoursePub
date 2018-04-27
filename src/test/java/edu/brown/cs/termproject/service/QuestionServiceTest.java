package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.QuestionUpvote;
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
public class QuestionServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private CourseService courseService;

  @Autowired
  private VideoService videoService;

  @Autowired
  private QuestionService questionService;

  @Test
  public void testAdd() {
    String title = "a question", body = "this is body";
    Calendar calendar = Calendar.getInstance();

    User user = userService.add("jj@cs.brown.edu");
    Course course = courseService.add("cs32");
    Video video = videoService.add("https://google.com", course);
    Question question = questionService.add(user, calendar, title, body, video);

    assertEquals(question.getUser(), user);
    assertEquals(question.getVideo(), video);
    assertEquals(question.getTitle(), title);
    assertEquals(question.getBody(), body);
    assertEquals(question.getVideoTime(), calendar);
    assertNotNull(question.getId());
    assertTrue(video.getQuestions().contains(question));
    assertTrue(question.getResponses().isEmpty());
  }

  @Test
  public void testUpvote() {
    String title = "a question", body = "this is body";
    Calendar calendar = Calendar.getInstance();

    User user1 = userService.add("jj@cs.brown.edu"),
        user2 = userService.add("twd@cs.brown.edu");
    Course course = courseService.add("cs32");
    Video video = videoService.add("https://google.com", course);
    Question question = questionService.add(user1, calendar, title, body, video);
    QuestionUpvote questionUpvote = questionService.upvote(user2, question);

    assertEquals(questionUpvote.getUser(), user2);
    assertEquals(questionUpvote.getPost(), question);
    assertNotNull(questionUpvote.getId());
    assertTrue(question.getUpvotes().contains(questionUpvote));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpvoteTwice() {
    String title = "a question", body = "this is body";
    Calendar calendar = Calendar.getInstance();

    User user1 = userService.add("jj@cs.brown.edu"),
        user2 = userService.add("twd@cs.brown.edu");
    Course course = courseService.add("cs32");
    Video video = videoService.add("https://google.com", course);
    Question question = questionService.add(user1, calendar, title, body, video);
    questionService.upvote(user2, question);
    questionService.upvote(user2, question);
  }
}
