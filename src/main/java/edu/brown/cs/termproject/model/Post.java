package edu.brown.cs.termproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public abstract class Post {

  @Column(name = "time")
  @Temporal(value = TemporalType.TIME)
  private Date time;

  @Column(name = "postTime")
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date postTime;

  @Column(name = "title")
  private String title;

  @Column(name = "body")
  private String body;

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(
      name = "userId",
      referencedColumnName = "id"
  )
  private User user;

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public Date getPostTime() {
    return postTime;
  }

  public void setPostTime(Date postTime) {
    this.postTime = postTime;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
