package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.Video;
import edu.brown.cs.termproject.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuestionController {


  private static final Gson GSON = new Gson();

  private VideoService videoService;

  @Autowired
  public QuestionController(VideoService videoService) {
    this.videoService = videoService;
  }

  @PostMapping(path = "/question")
  @ResponseBody
  public String question(InstructorQuestionRequest request) {
    Video video = videoService.ofId(request.getId());

    if (video == null) {
      throw new ResourceNotFoundException();
    }

    ImmutableList.Builder<Object> ret = ImmutableList.builder();
    for (Question question : video.getQuestions()) {
      ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();

      builder.put("id", question.getId());
      builder.put("time", question.getTime().getTimeInMillis() / 1000);
      builder.put("summary", question.getTitle());
      builder.put("user", question.getUser().getId());
      builder.put("resolved", false);
      builder.put("detail", question.getBody());
      builder.put("upvotes", question.getUpvotes().size());

      ret.add(builder.build());
    }

    return GSON.toJson(ret.build());
  }

  private static class InstructorQuestionRequest {
    private Integer id;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }
  }
}
