package edu.brown.cs.termproject.model;

import edu.brown.cs.termproject.collect.PickySet;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "remark")
public class Remark {

  @Id
  @Column(name = "id")
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(
      name = "uuid",
      strategy = "uuid2"
  )
  private String id;

  private Post post;

  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL
  )
  @PrimaryKeyJoinColumn(
      name = "videoId",
      referencedColumnName = "id"
  )
  private Video video;

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "remark",
      fetch = FetchType.EAGER
  )
  private Set<RemarkUpvote> remarkUpvotes = new PickySet<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Video getVideo() {
    return video;
  }

  public void setVideo(Video video) {
    video.addRemark(this);
    this.video = video;
  }

  public Date getTime() {
    return post.getTime();
  }

  public void setTime(Date time) {
    post.setTime(time);
  }

  public Date getPostTime() {
    return post.getPostTime();
  }

  public void setPostTime(Date postTime) {
    post.setPostTime(postTime);
  }

  public String getTitle() {
    return post.getTitle();
  }

  public void setTitle(String title) {
    post.setTitle(title);
  }

  public String getBody() {
    return post.getBody();
  }

  public void setBody(String body) {
    post.setBody(body);
  }

  public User getUser() {
    return post.getUser();
  }

  public void setUser(User user) {
    post.setUser(user);
  }

  public void addRemarkUpvote(RemarkUpvote remarkUpvote) {
    remarkUpvotes.add(remarkUpvote);
  }

  public void removeRemarkUpvote(RemarkUpvote remarkUpvote) {
    remarkUpvotes.remove(remarkUpvote);
  }

  @Override
  public String toString() {
    return String.format("{remark: {id: %s, title: %s,},}", id, getTitle());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || id == null) {
      return false;
    }
    if (!(obj instanceof Remark)) {
      throw new UnsupportedOperationException(
          "Comparison with object of a different class is undefined.");
    }

    Remark other = (Remark) obj;
    return id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(Remark.class, id);
  }
}
