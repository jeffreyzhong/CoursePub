package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import edu.brown.cs.termproject.dto.QuestionDto;
import edu.brown.cs.termproject.dto.ResponseDto;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.Video;
import edu.brown.cs.termproject.service.QuestionService;
import edu.brown.cs.termproject.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PostController {

  private static final Gson GSON = new Gson();

  private VideoService videoService;
  private QuestionService questionService;

  @Autowired
  public PostController(VideoService videoService,
                        QuestionService questionService) {
    this.videoService = videoService;
    this.questionService = questionService;
  }

  @PostMapping(path = "/question")
  @ResponseBody
  public String question(QuestionRequest request) {
    Video video = videoService.ofId(request.getId());

    if (video == null) {
      throw new ResourceNotFoundException();
    }

    ImmutableList.Builder<QuestionDto> ret = ImmutableList.builder();
    for (Question question : video.getQuestions()) {
      ret.add(new QuestionDto(question));
    }

    return GSON.toJson(ret.build());
  }

  @PostMapping(path = "/response")
  @ResponseBody
  public String response(ResponseRequest request) {
    Question question = questionService.ofId(request.getId());

    if (question == null) {
      throw new ResourceNotFoundException();
    }

    List<Response> responses = question.getResponses();
    ImmutableList.Builder<ResponseDto> builder = ImmutableList.builder();
    for (Response response : responses) {
      builder.add(new ResponseDto(response));
    }

    return GSON.toJson(builder.build());
  }

  private static class ResponseRequest {

    private Integer id;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }
  }

  private static class QuestionRequest extends ResponseRequest {
  }
}
