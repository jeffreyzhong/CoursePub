package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Remark;
import edu.brown.cs.termproject.model.RemarkUpvote;
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
public class RemarkServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private CourseService courseService;

  @Autowired
  private VideoService videoService;

  @Autowired
  private RemarkService remarkService;

  @Test
  public void testAdd() {
    String title = "a remark", body = "this is body";
    Calendar calendar = Calendar.getInstance();

    User user = userService.add("jj@cs.brown.edu");
    Course course = courseService.add("cs32");
    Video video = videoService.add("https://google.com", course);
    Remark remark = remarkService.add(user, calendar, title, body, video);

    assertEquals(remark.getUser(), user);
    assertEquals(remark.getVideo(), video);
    assertEquals(remark.getTitle(), title);
    assertEquals(remark.getBody(), body);
    assertEquals(remark.getTime(), calendar);
    assertNotNull(remark.getId());
    assertTrue(video.getRemarks().contains(remark));
  }

  @Test
  public void testUpvote() {
    String title = "a question", body = "this is body";
    Calendar calendar = Calendar.getInstance();

    User user1 = userService.add("jj@cs.brown.edu"),
        user2 = userService.add("twd@cs.brown.edu");
    Course course = courseService.add("cs32");
    Video video = videoService.add("https://google.com", course);
    Remark remark = remarkService.add(user1, calendar, title, body, video);
    RemarkUpvote remarkUpvote = remarkService.upvote(user2, remark);

    assertEquals(remarkUpvote.getUser(), user2);
    assertEquals(remarkUpvote.getPost(), remark);
    assertNotNull(remarkUpvote.getId());
    assertTrue(remark.getUpvotes().contains(remarkUpvote));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpvoteTwice() {
    String title = "a question", body = "this is body";
    Calendar calendar = Calendar.getInstance();

    User user1 = userService.add("jj@cs.brown.edu"),
        user2 = userService.add("twd@cs.brown.edu");
    Course course = courseService.add("cs32");
    Video video = videoService.add("https://google.com", course);
    Remark remark = remarkService.add(user1, calendar, title, body, video);
    remarkService.upvote(user2, remark);
    remarkService.upvote(user2, remark);
  }
}
