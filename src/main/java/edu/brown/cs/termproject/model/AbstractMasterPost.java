package edu.brown.cs.termproject.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

@MappedSuperclass
public abstract class AbstractMasterPost<T extends AbstractUpvote>
    extends AbstractPost<T> {

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(
      name = "videoId",
      referencedColumnName = "id"
  )
  private Video video;

  @Column(name = "title")
  private String title;

  @Column(name = "videoTime")
  @Temporal(value = TemporalType.TIMESTAMP)
  private Calendar videoTime;

  public Video getVideo() {
    return video;
  }

  public void setVideo(Video video) {
    this.video = video;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Calendar getVideoTime() {
    return videoTime;
  }

  public void setVideoTime(Calendar videoTime) {
    this.videoTime = videoTime;
  }

  @Override
  public String toString() {
    return String.format("{%s: {id: %s, videoId: %d, videoTime: %s, postTime: %s,},}",
        getTableName(), getId(), video.getId(), videoTime, getPostTime());
  }
}
