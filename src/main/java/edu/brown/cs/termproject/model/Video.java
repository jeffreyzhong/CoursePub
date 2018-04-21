package edu.brown.cs.termproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "video")
public class Video {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "courseId", referencedColumnName = "id")
  private Course course;

  @Column(name = "url")
  private String url;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return String.format("{video: {id: %d, url: %s,},}", id, url);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Video)) {
      throw new UnsupportedOperationException(
          "Comparison with object of a different class is undefined.");
    }

    Video other = (Video) obj;
    return id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(Video.class, id);
  }
}
