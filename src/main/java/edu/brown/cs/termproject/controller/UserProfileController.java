package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping(path = "/user")
public class UserProfileController {

  private ApplicationContext applicationContext;

  @Autowired
  public UserProfileController(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @GetMapping
  public ModelAndView user(@RequestParam(value = "id") Integer id) {
    UserService userService =
        applicationContext.getBean("userServiceImpl", UserService.class);

    User user = userService.ofId(id);

    if (user == null) {
      throw new ResourceNotFoundException();
    }

    Map<String, Object> variables = ImmutableMap.of(
        "title", "User",
        "id", id,
        "words", String.format("My email is %s", user.getEmail())
    );
    return new ModelAndView("user", variables);
  }
}
