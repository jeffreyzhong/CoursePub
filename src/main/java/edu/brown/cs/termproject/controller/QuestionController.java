package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.Video;
import edu.brown.cs.termproject.service.QuestionService;
import edu.brown.cs.termproject.service.ResponseService;
import edu.brown.cs.termproject.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuestionController {

  private static final Gson GSON = new Gson();

  private VideoService videoService;
  private QuestionService questionService;
  private ResponseService responseService;

  @Autowired
  public QuestionController(VideoService videoService,
                            QuestionService questionService,
                            ResponseService responseService) {
    this.videoService = videoService;
    this.questionService = questionService;
    this.responseService = responseService;
  }

  @PostMapping(path = "/question")
  @ResponseBody
  public String question(QuestionRequest request) {
    Video video = videoService.ofId(request.getId());

    if (video == null) {
      throw new ResourceNotFoundException();
    }

    ImmutableList.Builder<Object> ret = ImmutableList.builder();
    for (Question question : video.getQuestions()) {
      ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();

      builder.put("id", question.getId());
      builder.put("time", question.getVideoTime().getTimeInMillis() / 1000);
      builder.put("summary", question.getTitle());
      builder.put("user", question.getUser().getId());
      builder.put("resolved", false);
      builder.put("detail", question.getBody());
      builder.put("upvotes", question.getUpvotes().size());

      ret.add(builder.build());
    }

    return GSON.toJson(ret.build());
  }

  private static class QuestionRequest {

    private Integer id;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }
  }
}
