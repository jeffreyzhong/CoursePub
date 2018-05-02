package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.termproject.model.Registration;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.model.Video;
import edu.brown.cs.termproject.service.RegistrationService;
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
  private RegistrationService registrationService;

  @Autowired
  public InstructorViewController(VideoService videoService,
                                  RegistrationService registrationService) {
    this.videoService = videoService;
    this.registrationService = registrationService;
  }

  @GetMapping(path = "/video/{id}")
  public ModelAndView video(@PathVariable("id") Integer id, User user) {
    Video video = videoService.ofId(id);

    if (video == null) {
      throw new ResourceNotFoundException();
    }

    try {
      Registration registration =
          registrationService.get(user, video.getCourse());

      if (registration.getType() != 1) {
        throw new UserNotFoundException();
      }

    } catch (IllegalArgumentException e) {
      throw new UserNotFoundException();
    }

    Map<String, Object> variables = ImmutableMap.of(
        "title", "Instructor View",
        "videoId", id,
        "videoUrl", video.getUrl()
    );

    return new ModelAndView("instructorView", variables);
  }
}
