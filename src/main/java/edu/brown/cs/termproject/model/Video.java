package edu.brown.cs.termproject.model;

import edu.brown.cs.termproject.collect.PickySet;
import edu.brown.cs.termproject.nlp.TfIdfCorpusSource;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "video")
public class Video implements TfIdfCorpusSource<Sentence, Question> {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(
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
  private Set<Question> questions = new PickySet<>();

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "video",
      fetch = FetchType.EAGER
  )
  private Set<Remark> remarks = new PickySet<>();

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "video",
      fetch = FetchType.EAGER
  )
  private Set<Sentence> sentences = new PickySet<>();

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

  public Set<Remark> getRemarks() {
    return Collections.unmodifiableSet(remarks);
  }

  public void setRemarks(Set<Remark> remarks) {
    this.remarks = remarks;
  }

  public Set<Question> getQuestions() {
    return Collections.unmodifiableSet(questions);
  }

  public void setQuestions(Set<Question> questions) {
    this.questions = questions;
  }

  public Set<Sentence> getSentences() {
    return sentences;
  }

  public void setSentences(Set<Sentence> sentences) {
    this.sentences = sentences;
  }

  public void addQuestion(Question question) {
    questions.add(question);
  }

  public void removeQuestion(Question question) {
    questions.remove(question);
  }

  public void addRemark(Remark remark) {
    remarks.add(remark);
  }

  public void removeRemark(Remark remark) {
    remarks.remove(remark);
  }

  @Override
  public Collection<Sentence> dataCorpus() {
    return this.sentences;
  }

  @Override
  public Collection<Question> queryCorpus() {
    return this.questions;
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
