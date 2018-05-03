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
@RequestMapping("/test")
public class TestController {

  VideoService videoService;

  @Autowired
  public TestController(VideoService videoService) {
    this.videoService = videoService;
  }

  @GetMapping(path = "/{which}/{id}")
  public ModelAndView lecture(@PathVariable("which") String which,
                              @PathVariable("id") Integer id) {
    Video video = videoService.ofId(id);

    if (video == null) {
      throw new ResourceNotFoundException();
    }

    Map<String, Object> variables = ImmutableMap.of(
        "title", "Video",
        "videoId", id,
        "videoUrl", video.getUrl(),
        "which", which
    );

    return new ModelAndView("videoStub", variables);
  }
}
