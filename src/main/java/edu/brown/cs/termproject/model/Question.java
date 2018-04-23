package edu.brown.cs.termproject.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "question")
public class Question extends Post {

  @Id
  @Column(name = "id")
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(
      name = "uuid",
      strategy = "uuid2"
  )
  private String id;

  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL
  )
  @PrimaryKeyJoinColumn(
      name = "videoId",
      referencedColumnName = "id"
  )
  private Video video;

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
    video.addQuestion(this);
    this.video = video;
  }

  @Override
  public String toString() {
    return String.format("{question: {id: %s, title: %s,},}", id, getTitle());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || id == null) {
      return false;
    }
    if (!(obj instanceof Question)) {
      throw new UnsupportedOperationException(
          "Comparison with object of a different class is undefined.");
    }

    Question other = (Question) obj;
    return id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(Question.class, id);
  }
}
