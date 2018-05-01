package edu.brown.cs.termproject.dto;

import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.time.CalendarSerializer;

import java.util.Calendar;
import java.util.Map;

public class ResponseDto implements Dto<Response> {

  /* request */
  private Integer questionId;
  private String detail;

  /* fill */
  private Integer id;
  private Integer userId;
  private String postDate;
  private String postTime;
  private Integer upvotes;

  public ResponseDto(Map<String, ?> values) throws IllegalArgumentException {
    try {
      this.questionId = ((Double) values.get("questionId")).intValue();
      this.detail = (String) values.get("detail");
    } catch (Throwable t) {
      throw new IllegalArgumentException(t);
    }
  }

  public ResponseDto(Response response) {
    this.questionId = response.getQuestion().getId();
    this.detail = response.getBody();
    fill(response);
  }

  @Override
  public void fill(Response response) {
    Calendar cal = response.getPostTime();

    this.id = response.getId();
    this.userId = response.getUser().getId();
    this.postDate = CalendarSerializer.toDate(cal);
    this.postTime = CalendarSerializer.toTime(cal);
    this.upvotes = response.getUpvotes().size();
  }

  public Integer getId() {
    return id;
  }

  public Integer getQuestionId() {
    return questionId;
  }

  public String getDetail() {
    return detail;
  }

  public Integer getUserId() {
    return userId;
  }

  public String getPostTime() {
    return postTime;
  }
}
