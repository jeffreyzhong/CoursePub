package edu.brown.cs.termproject.service;

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
public class UserServiceTest {

  @Autowired
  private UserService userService;

  @Test
  public void testAdd() {
    String email = "jj@cs.brown.edu";

    User user = userService.add(email);
    assertNotNull(user);
    assertEquals(user.getEmail(), email);
    assertNotNull(user.getId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddDuplicate() {
    String email = "twd@cs.brown.edu";
    userService.add(email);
    userService.add(email);
  }

  @Test
  public void testOfId() {
    String email = "twd@cs.brown.edu";
    User user = userService.add(email);
    assertEquals(user, userService.ofId(user.getId()));
  }
}
