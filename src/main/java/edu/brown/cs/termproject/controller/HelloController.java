package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

/**
 * Controller class for Spring Boot.
 *
 * @author yqin
 */
@Controller
class HelloController {

  @GetMapping(path = "/")
  public RedirectView root() {
    return new RedirectView("/hello");
  }

  @GetMapping(path = "/hello", produces = MediaType.TEXT_HTML_VALUE)
  public ModelAndView hello() {
    Map<String, Object> variables = ImmutableMap.of("title", "Hello World");

    return new ModelAndView("homePage", variables);
  }
}
