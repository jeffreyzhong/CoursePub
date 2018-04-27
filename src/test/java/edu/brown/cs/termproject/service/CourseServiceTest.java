package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Course;
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
public class CourseServiceTest {

  @Autowired
  private CourseService courseService;

  @Test
  public void testAdd() {
    String name = "cs32";

    Course course = courseService.add(name);

    assertNotNull(course);
    assertEquals(course.getName(), name);
    assertNotNull(course.getId());
    assertTrue(course.getVideos().isEmpty());
  }

  @Test
  public void testOfId() {
    String name = "cs32";

    Course course = courseService.add(name);

    assertEquals(course, courseService.ofId(course.getId()));
  }
}
