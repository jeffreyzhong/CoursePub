package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.termproject.model.Video;
import edu.brown.cs.termproject.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/instructor")
public class InstructorViewController {

  private VideoService videoService;

  @Autowired
  public InstructorViewController(VideoService videoService) {
    this.videoService = videoService;
  }

  @GetMapping(path = "/video/{id}")
  public ModelAndView video(@PathVariable("id") Integer id) {
    Video video = videoService.ofId(id);

    if (video == null) {
      throw new ResourceNotFoundException();
    }

    Map<String, Object> variables =
        ImmutableMap.of("title", "Instructor View", "videoId", id);

    return new ModelAndView("instructorViewStub", variables);
  }
}
