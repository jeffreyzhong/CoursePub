package edu.brown.cs.termproject.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
  
  /**
   * Controller class for Spring Boot.
   *
   * @author yqin
   */
  @Controller
  class CourseController {

    @GetMapping(path = "/courses", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView landing() {
      return new ModelAndView("courseCatalog");
    }
  }


