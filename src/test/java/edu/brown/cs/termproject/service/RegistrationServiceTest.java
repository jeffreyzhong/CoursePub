package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Registration;
import edu.brown.cs.termproject.model.User;
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
public class RegistrationServiceTest {

  @Autowired
  private RegistrationService registrationService;

  @Autowired
  private CourseService courseService;

  @Autowired
  private UserService userService;

  @Test
  public void testAdd() {
    Course course = courseService.add("cs32");
    User user = userService.add("jj@cs.brown.edu");

    assertTrue(user.getRegistrations().isEmpty());
    assertTrue(course.getRegistrations().isEmpty());

    Registration registration = registrationService.add(user, course, 0);

    assertEquals(registration.getCourse(), course);
    assertEquals(registration.getUser(), user);
    assertTrue(user.getRegistrations().contains(registration));
    assertTrue(course.getRegistrations().contains(registration));
    assertNotNull(registration.getId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddDuplicate() {
    Course course = courseService.add("cs32");
    User user = userService.add("jj@cs.brown.edu");
    registrationService.add(user, course, 0);
    registrationService.add(user, course, 1);
  }
}
