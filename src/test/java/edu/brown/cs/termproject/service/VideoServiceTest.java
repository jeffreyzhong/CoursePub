package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Video;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VideoServiceTest {

  @Autowired
  private VideoService videoService;

  @Autowired
  private CourseService courseService;

  @Test
  public void testAdd() {
    String url = "https://google.com";
    Course course = courseService.add("CS32");
    Video video = videoService.add(url, course);

    assertNotNull(video);
    assertNotNull(video.getId());
    assertEquals(video.getCourse(), course);
    assertEquals(url, video.getUrl());
    assertTrue(video.getRemarks().isEmpty());
    assertTrue(video.getQuestions().isEmpty());
  }

  @Test
  public void testOfId() {
    String url = "https://google.com";
    Course course = courseService.add("CS32");
    Video video = videoService.add(url, course);

    assertEquals(video, videoService.ofId(video.getId()));
  }
}
