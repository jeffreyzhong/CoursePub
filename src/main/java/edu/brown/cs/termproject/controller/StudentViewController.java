package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.termproject.model.Video;
import edu.brown.cs.termproject.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class StudentViewController {

  private VideoService videoService;

  @Autowired
  public StudentViewController(VideoService videoService) {
    this.videoService = videoService;
  }

  @GetMapping(path = "/video/{id}")
  public ModelAndView lecture(@PathVariable("id") Integer id) {
    Video video = videoService.ofId(id);

    if (video == null) {
      throw new ResourceNotFoundException();
    }

    Map<String, Object> variables = ImmutableMap.of(
        "title", "Video",
        "videoId", id,
        "words", String.format("this question has %d remarks and %d questions",
            video.getRemarks().size(), video.getQuestions().size())
    );

    return new ModelAndView("video", variables);
  }
}
