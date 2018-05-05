package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import edu.brown.cs.termproject.dto.QuestionDto;
import edu.brown.cs.termproject.dto.ResponseDto;
import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.model.Video;
import edu.brown.cs.termproject.pageRank.PageRank;
import edu.brown.cs.termproject.pageRank.PageRankNode;
import edu.brown.cs.termproject.service.QuestionService;
import edu.brown.cs.termproject.service.UserService;
import edu.brown.cs.termproject.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

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
  
  @PostMapping(path = "/setup")
  @ResponseBody
  public String setup(QuestionRequest request) {
    Video video = videoService.ofId(request.getId());

    if (video == null) {
      throw new ResourceNotFoundException();
    }

    ImmutableList.Builder<String> ret = ImmutableList.builder();
    ret.add(video.getUrl());

    return GSON.toJson(ret.build());
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

  @PostMapping(path = "/related")
  @ResponseBody
  public String related(EmptyRequest request, User user)
      throws ClassNotFoundException {

    Class c =
        Class.forName("edu.brown.cs.termproject.model.Course");

    PageRank pr = new PageRank(user);
    List<PageRankNode> result = pr.getTopResult(c,3);

    List<Course> courses = (List<Course>)(Object)result;
    
    ImmutableList.Builder<String> ret = ImmutableList.builder();
    for (Course course : courses) {
      Video video = course.getVideos().iterator().next();
      ret.add(Integer.toString(video.getId()));
      ret.add(video.getUrl());
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

  private static class EmptyRequest {

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
