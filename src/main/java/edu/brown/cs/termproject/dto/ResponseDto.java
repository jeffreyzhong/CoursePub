package edu.brown.cs.termproject.dto;

import edu.brown.cs.termproject.model.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ResponseDto {

  private static final SimpleDateFormat dateFormat =
      new SimpleDateFormat("yyyy-MM-dd");
  private static final SimpleDateFormat timeFormat =
      new SimpleDateFormat("hh:mm:ss");

  private String id;
  private String questionId;
  private String detail;
  private Integer userId;
  private String postDate;
  private String postTime;
  private Integer upvotes;

  public ResponseDto(Map<String, ?> values) {

  }

  public ResponseDto(Response response) {
    Date time = response.getPostTime().getTime();

    this.id = response.getId();
    this.questionId = response.getQuestion().getId();
    this.detail = response.getBody();
    this.userId = response.getUser().getId();
    this.postDate = dateFormat.format(time);
    this.postTime = timeFormat.format(time);
    this.upvotes = response.getUpvotes().size();
  }

  public String getId() {
    return id;
  }

  public String getQuestionId() {
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
