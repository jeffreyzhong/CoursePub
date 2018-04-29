package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.ResponseUpvote;
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
public class ResponseServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private CourseService courseService;

  @Autowired
  private VideoService videoService;

  @Autowired
  private QuestionService questionService;

  @Autowired
  private ResponseService responseService;

  @Test
  public void testAdd() {
    String title = "a question", questionBody = "this is body",
        responseBody = "hello";
    Calendar calendar = Calendar.getInstance();

    User user1 = userService.add("jj@cs.brown.edu"),
        user2 = userService.add("twd@cs.brown.edu");
    Course course = courseService.add("cs32");
    Video video = videoService.add("https://google.com", course);
    Question question = questionService.add(user1, calendar, title, questionBody, video);
    Response response = responseService.add(user2, question, responseBody);

    assertEquals(response.getUser(), user2);
    assertEquals(response.getBody(), responseBody);
    assertNotNull(response.getId());
    assertTrue(response.getUpvotes().isEmpty());
    assertTrue(question.getResponses().contains(response));
  }

  @Test
  public void testUpvote() {
    String title = "a question", questionBody = "this is body",
        responseBody = "hello";
    Calendar calendar = Calendar.getInstance();

    User user1 = userService.add("jj@cs.brown.edu"),
        user2 = userService.add("twd@cs.brown.edu");
    Course course = courseService.add("cs32");
    Video video = videoService.add("https://google.com", course);
    Question question = questionService.add(user1, calendar, title, questionBody, video);
    Response response = responseService.add(user2, question, responseBody);
    ResponseUpvote responseUpvote = responseService.upvote(user1, response);

    assertEquals(responseUpvote.getUser(), user1);
    assertEquals(responseUpvote.getPost(), response);
    assertNotNull(responseUpvote.getId());
    assertTrue(response.getUpvotes().contains(responseUpvote));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpvoteTwice() {
    String title = "a question", questionBody = "this is body",
        responseBody = "hello";
    Calendar calendar = Calendar.getInstance();

    User user1 = userService.add("jj@cs.brown.edu"),
        user2 = userService.add("twd@cs.brown.edu");
    Course course = courseService.add("cs32");
    Video video = videoService.add("https://google.com", course);
    Question question = questionService.add(user1, calendar, title, questionBody, video);
    Response response = responseService.add(user2, question, responseBody);
    responseService.upvote(user1, response);
    responseService.upvote(user1, response);
  }
}
