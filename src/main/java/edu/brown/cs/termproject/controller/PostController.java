package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import edu.brown.cs.termproject.autocorrect.Trie;
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
import edu.brown.cs.termproject.service.CourseService;
import edu.brown.cs.termproject.service.QuestionService;
import edu.brown.cs.termproject.service.RegistrationService;
import edu.brown.cs.termproject.service.UserService;
import edu.brown.cs.termproject.service.VideoService;
import edu.brown.cs.termproject.trie.TrieManager;
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
  private RegistrationService registrationService;
  private Trie trie;
  private Map<String,Course> nameToCourse;
  private Map<String,Set<Video>> courseToVideos;

  @Autowired
  public PostController(VideoService videoService,
                        QuestionService questionService, CourseService courseServ, RegistrationService registrationService) {
    this.videoService = videoService;
    this.questionService = questionService;
    this.registrationService = registrationService;
    trie = new Trie();
    nameToCourse = new HashMap<String,Course>();
    courseToVideos = new HashMap<String,Set<Video>>();
    this.courseService = courseServ;
    List<Course> courses = courseService.getAllCourses();
    for (Course c : courses) {
      trie.add(c.getName());
      nameToCourse.put(c.getName(), c);
      courseToVideos.put(Integer.toString(c.getId()), c.getVideos());
    }
 
    trie.getControl().setPrefix(true);
    trie.getControl().setLevenshtein(4);
    trie.getControl().setWhiteSpace(true);
    
  }
  
  @PostMapping(path = "/setup")
  @ResponseBody
  public String setup(ResponseRequest request) {
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
      TrieManager.insertVideoTranscript(id,tempMap);
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
  
  @PostMapping(path = "/homePageSearchSuggestions")
  @ResponseBody
  public String homePageSearchSuggestions(SearchSuggest searchSuggest) {
    String userInput = searchSuggest.getInput();
    userInput.toLowerCase();
    List<String> res = trie.courseCorrect(userInput);
    List<String> newRes = new ArrayList<String>();
    for (String word : res) { 
      newRes.add(Trie.getCaseInsensitive().get(word));
    }
    return GSON.toJson(newRes);
  }
  
  @PostMapping(path = "/homePageSearchSubmit")
  @ResponseBody
  public String homePageSearchSubmit(SearchSuggest searchSubmit, User user) {
    String userInput = searchSubmit.getInput();
    Map<String,Object> map = new HashMap<String,Object>();
    Course course = nameToCourse.get(userInput);
    boolean isInstructor = registrationService.isInstructor(user, course);
    Iterator<Video> videos = course.getVideos().iterator();
    map.put("courseName", userInput);
    List<List<String>> videoList = new ArrayList<List<String>>();
    while (videos.hasNext()) {
      List<String> tmp = new ArrayList<String>();
      Video video = videos.next();
      tmp.add(video.getUrl());
      tmp.add(Integer.toString(video.getId()));
      videoList.add(tmp);
    }
    map.put("courseInfo", videoList);
    map.put("isInstructor", isInstructor);
    return GSON.toJson(map);
  }
  
  @PostMapping(path = "/courseCatalog")
  @ResponseBody
  public String courseCatalog() {
    List<Course> courses = courseService.getAllCourses();
    List<Map<String,String>> ret = new ArrayList<Map<String,String>>();
    for (Course c : courses) {
      Map<String,String> map = new HashMap<String,String>();
      map.put("courseName", c.getName());
      map.put("courseId", Integer.toString(c.getId()));
      map.put("courseThumbnail", c.getVideos().iterator().next().getUrl());
      ret.add(map);
    }
    return GSON.toJson(ret);
  }
  
  @PostMapping(path = "/getCourseVideos")
  @ResponseBody
  public String getCourseVideos(CourseVideo courseVideo, User user) {
    String courseId = courseVideo.getCourseId();
    
    boolean isInstructor = registrationService.isInstructor(user, courseService.ofId(Integer.valueOf(courseId)));
    
    System.out.println("IDDDDD: " + courseId);
    Iterator<Video> videoSet = courseToVideos.get(courseId).iterator();
    List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
    while (videoSet.hasNext()) {
      Video vid = videoSet.next();
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("videoUrl", vid.getUrl());
      map.put("videoId", Integer.toString(vid.getId()));
      ret.add(map);
    }
    Map<String,Object> isInstruct = new HashMap<String,Object>();
    isInstruct.put("isInstructor", isInstructor);
    ret.add(isInstruct);
    return GSON.toJson(ret);
  }

  private static class EmptyRequest {

  }
  
  private static class CourseVideo {
    private String courseId;
    
    public String getCourseId() {
      return courseId;
    }
    
    public void setCourseId(String id) {
      this.courseId = id;
    }
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

    public void setEnd(Double end) {
      this.end = end;
    }

    public void setStart(Double start) {
      this.start = start;
    }

    public void setWord(String word) {
      this.word = word;
    }
  }
}
