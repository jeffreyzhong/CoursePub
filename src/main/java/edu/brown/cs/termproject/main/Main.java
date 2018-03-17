package edu.brown.cs.termproject.main;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Main class of the project.
 *
 * @author yqin
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Main {

  /**
   * Starts execution of all codes.
   *
   * @author yqin
   * @param args arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
}

/**
 * Controller class for Spring Boot.
 *
 * @author yqin
 */
@Controller
class MainController {

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
  public ModelAndView user(@RequestParam(value = "id") String id) {
    Map<String, Object> variables = ImmutableMap.of("title", "User", "id", id);

    return new ModelAndView("user", variables);
  }

  @GetMapping(path = "/lecture")
  public ModelAndView lecture(@RequestParam(value = "id") String id) {
    Map<String, Object> variables =
        ImmutableMap.of("title", "Lecture", "id", id);

    return new ModelAndView("lecture", variables);
  }
}
