package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.termproject.model.Video;
import edu.brown.cs.termproject.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class StudentViewController {

  private ApplicationContext applicationContext;

  @Autowired
  public StudentViewController(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @GetMapping(path = "/video")
  public ModelAndView lecture(@RequestParam(value = "id") Integer id) {
    VideoService videoService =
        applicationContext.getBean("videoServiceImpl", VideoService.class);
    Video video = videoService.ofId(id);

    if (video == null) {
      throw new ResourceNotFoundException();
    }

    Map<String, Object> variables = ImmutableMap.of(
        "title", "Video",
        "id", id,
        "words", String.format("this question has %d remarks and %d questions",
            video.getRemarks().size(), video.getQuestions().size())
    );

    return new ModelAndView("video", variables);
  }
}
