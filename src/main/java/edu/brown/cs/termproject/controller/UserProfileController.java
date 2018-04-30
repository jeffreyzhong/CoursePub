package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class UserProfileController {

  private UserService userService;

  @Autowired
  public UserProfileController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(path = "/user/{id}")
  public ModelAndView user(@PathVariable("id") Integer id) {
    User user = userService.ofId(id);

    if (user == null) {
      throw new ResourceNotFoundException();
    }

    Map<String, Object> variables = ImmutableMap.of(
        "title", "User",
        "id", id,
        "words", String.format("My email is %s", user.getEmail())
    );
    return new ModelAndView("userProfileStub", variables);
  }
}
