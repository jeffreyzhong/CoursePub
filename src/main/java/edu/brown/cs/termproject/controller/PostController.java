package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import edu.brown.cs.termproject.autocorrect.Trie;
import edu.brown.cs.termproject.dto.QuestionDto;
import edu.brown.cs.termproject.dto.ResponseDto;
import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.model.Video;
import edu.brown.cs.termproject.pageRank.PageRank;
import edu.brown.cs.termproject.pageRank.PageRankNode;
import edu.brown.cs.termproject.service.CourseService;
import edu.brown.cs.termproject.service.QuestionService;
import edu.brown.cs.termproject.service.UserService;
import edu.brown.cs.termproject.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class PostController {

  private static final Gson GSON = new Gson();

  private VideoService videoService;
  private QuestionService questionService;
  private CourseService courseService;
  private Trie trie;
  private Map<String,Course> nameToCourse;

  @Autowired
  public PostController(VideoService videoService,
                        QuestionService questionService, CourseService courseServ) {
    this.videoService = videoService;
    this.questionService = questionService;
    trie = new Trie();
    nameToCourse = new HashMap<String,Course>();
    this.courseService = courseServ;
    List<Course> courses = courseService.getAllCourses();
    for (Course c : courses) {
      trie.add(c.getName());
      nameToCourse.put(c.getName(), c);
      
    }
 
    trie.getControl().setPrefix(true);
    trie.getControl().setLevenshtein(4);
    trie.getControl().setWhiteSpace(true);
    
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
  
  @PostMapping(path = "/homePageSearchSuggestions")
  @ResponseBody
  public String homePageSearchSuggestions(SearchSuggest searchSuggest) {
    String userInput = searchSuggest.getInput();
    
    List<String> res = trie.courseCorrect(userInput);

    return GSON.toJson(res);
  }
  
  @PostMapping(path = "/homePageSearchSubmit")
  @ResponseBody
  public String homePageSearchSubmit(SearchSuggest searchSubmit) {
    String userInput = searchSubmit.getInput();
    System.out.println("INPUTTTT: " + userInput);
    userInput.toLowerCase();
    userInput = Trie.getCaseInsensitive().get(userInput);
    Course course = nameToCourse.get(userInput);
    Iterator<Video> videos = course.getVideos().iterator();
    List<String> videoURLs = new ArrayList<String>();
    videoURLs.add(userInput);
    while (videos.hasNext()) {
      videoURLs.add(videos.next().getUrl());
    }
    return GSON.toJson(videoURLs);
  }

  private static class EmptyRequest {

  }
  
  private static class SearchSuggest {
    private String input;
    
    public String getInput() {
      return input;
    }
    
    public void setInput(String iP) {
      this.input = iP;
    }
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
