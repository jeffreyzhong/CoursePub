package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import java.util.Map;

/**
 * Controller class for Spring Boot.
 *
 * @author yqin
 */
@Controller
class Controllers {

  @GetMapping(path = "/")
  public RedirectView root() {
    return new RedirectView("/hello");
  }

  @GetMapping(path = "/hello")
  public ModelAndView hello() {
    Map<String, Object> variables = ImmutableMap.of("title", "Hello World");

    return new ModelAndView("hello", variables);
  }

  @GetMapping(path = "/user")
  public ModelAndView user(@RequestParam(value = "id") Integer id) {
    Map<String, Object> variables = ImmutableMap.of("title", "User", "id", id);

    return new ModelAndView("user", variables);
  }

  @GetMapping(path = "/lecture")
  public ModelAndView lecture(@RequestParam(value = "id") Integer id) {
    Map<String, Object> variables =
        ImmutableMap.of("title", "Lecture", "id", id);

    return new ModelAndView("lecture", variables);
  }

  @GetMapping(path = "/instructor")
  public ModelAndView instructor(@RequestParam(value = "id") Integer id) {
    Map<String, Object> variables =
        ImmutableMap.of("title", "Instructor View", "id", id);

    return new ModelAndView("instructor", variables);
  }

  @PostMapping(path = "/instructor/question")
  public String question(@RequestParam(value = "id") Integer id) {
    return "";
  }
}
