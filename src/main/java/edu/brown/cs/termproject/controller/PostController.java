package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import edu.brown.cs.termproject.dto.QuestionDto;
import edu.brown.cs.termproject.dto.ResponseDto;
import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.Sentence;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.model.Video;
import edu.brown.cs.termproject.pageRank.PageRank;
import edu.brown.cs.termproject.pageRank.PageRankNode;
import edu.brown.cs.termproject.service.QuestionService;
import edu.brown.cs.termproject.service.UserService;
import edu.brown.cs.termproject.service.VideoService;
import edu.brown.cs.termproject.trie.TrieManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  @PostMapping(path = "/searchTranscript")
  @ResponseBody
  public String question(TranscriptRequest request) {
    Integer id = request.getId();
    if (!TrieManager.hasTrie(id)) {
      Video tempVideo = videoService.ofId(id);
      Set<Sentence> sentences = tempVideo.getSentences();
      Map<String, Double> tempMap = new HashMap<>();
      for(Sentence s:sentences){
        Long c = s.getVideoTime().getTimeInMillis();
        tempMap.put(s.getWords(), (double)c/1000);
      }
      TrieManager.insertVideoTranscript(1,tempMap);
    }
    List<String> result = TrieManager.getWordTimeList(request.getWord(),
        id, request.getStart(), request.getEnd());
    ImmutableList.Builder<String> ret = ImmutableList.builder();
    for (String st:result) {
      ret.add(st);
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

    System.out.println(result);
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

  private static class TranscriptRequest extends ResponseRequest{
    private Double start;
    private Double end;
    private String word;

    public Double getEnd() {
      return end;
    }

    public String getWord() {
      return word;
    }

    public Double getStart() {
      return start;
    }
  }
}
