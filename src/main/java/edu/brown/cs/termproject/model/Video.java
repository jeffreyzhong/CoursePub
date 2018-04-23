package edu.brown.cs.termproject.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "video")
public class Video {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(
      name = "courseId",
      referencedColumnName = "id"
  )
  private Course course;

  @Column(name = "url")
  private String url;

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "video",
      fetch = FetchType.EAGER
  )
  private Set<Question> questions = new HashSet<>();

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

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public Set<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<Question> questions) {
    this.questions = questions;
  }

  public void addQuestion(Question question) {
    if (!questions.contains(question)) {
      questions.add(question);
    }
  }

  public void removeQuestion(Question question) {
    if (questions.contains(question)) {
      questions.remove(question);
    }
  }

  @Override
  public String toString() {
    return String.format("{video: {id: %d, url: %s,},}", id, url);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || id == null) {
      return false;
    }
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