package edu.brown.cs.termproject.dto;

import edu.brown.cs.termproject.model.Remark;

import java.util.Map;

public class RemarkDto {

  private Integer id;
  private Integer user;
  private Long time;
  private String summary;
  private String detail;
  private Integer upvotes;

  public RemarkDto(Remark remark) {
    this.id = remark.getId();
    this.user = remark.getUser().getId();
    this.time = remark.getVideoTime().getTimeInMillis() / 1000;
    this.summary = remark.getTitle();
    this.detail = remark.getBody();
    this.upvotes = remark.getUpvotes().size();
  }
}
