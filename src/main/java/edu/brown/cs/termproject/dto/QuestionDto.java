package edu.brown.cs.termproject.dto;

import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.time.CalendarSerializer;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QuestionDto implements Dto<Question> {

  /* request */
  private String questionTimestamp;
  private String summary;
  private String detail;
  private Integer videoId;

  /* fill */
  private Long time;
  private Integer id;
  private Integer user;
  private Boolean resolved;
  private Integer upvotes;
  private String postTime;
  private String postDate;

  /* lazy */
  private List<ResponseDto> responses;

  public QuestionDto(Map<String, ?> values)
      throws IllegalArgumentException {
    try {
      this.questionTimestamp = (String) values.get("questionTimestamp");
      this.summary = (String) values.get("summary");
      this.detail = (String) values.get("detail");
      this.videoId = ((Double) values.get("videoId")).intValue();
    } catch (Throwable t) {
      throw new IllegalArgumentException(t);
    }
  }

  public QuestionDto(Question question) {
    this.questionTimestamp = "";
    this.videoId = question.getVideo().getId();
    this.summary = question.getTitle();
    this.detail = question.getBody();
    fill(question);
  }

  @Override
  public void fill(Question question) {
    Calendar cal = question.getPostTime();

    this.time = question.getVideoTime().getTimeInMillis() / 1000;
    this.id = question.getId();
    this.user = question.getUser().getId();
    this.resolved = false;
    this.upvotes = question.getUpvotes().size();
    this.postDate = CalendarSerializer.toDate(cal);
    this.postTime = CalendarSerializer.toTime(cal);
  }

  public void fillResponses(Question question) {
    this.responses = question.getResponses()
        .stream()
        .map(ResponseDto::new)
        .collect(Collectors.toList());
  }

  public String getQuestionTimestamp() {
    return questionTimestamp;
  }

  public String getSummary() {
    return summary;
  }

  public String getDetail() {
    return detail;
  }

  public Long getTime() {
    return time;
  }

  public Integer getId() {
    return id;
  }

  public Integer getUser() {
    return user;
  }

  public Boolean getResolved() {
    return resolved;
  }

  public Integer getUpvotes() {
    return upvotes;
  }

  public String getPostTime() {
    return postTime;
  }

  public String getPostDate() {
    return postDate;
  }

  public Integer getVideoId() {
    return videoId;
  }
}
