package edu.brown.cs.termproject.dto;

import edu.brown.cs.termproject.model.Question;

import java.util.Map;

public class QuestionDto {

  private String id;
  private Integer user;
  private Long time;
  private String summary;
  private String detail;
  private Boolean resolved;
  private Integer upvotes;

  public QuestionDto(Map<String, ?> values)
      throws IllegalArgumentException {
    try {
      this.user = Integer.valueOf((String) values.get("user"));
      this.time = Long.valueOf((String) values.get("time"));
      this.summary = (String) values.get("summary");
      this.detail = (String) values.get("detail");
    } catch (Throwable t) {
      throw new IllegalArgumentException(t);
    }
  }

  public QuestionDto(Question question) {
    this.id = question.getId();
    this.user = question.getUser().getId();
    this.time = question.getVideoTime().getTimeInMillis() / 1000;
    this.summary = question.getTitle();
    this.detail = question.getBody();
    this.resolved = false;
    this.upvotes = question.getUpvotes().size();
  }

}
