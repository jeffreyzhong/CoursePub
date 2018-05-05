package edu.brown.cs.termproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "sentence")
public class Sentence {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "words")
  private String words;

  @Column(name = "videoTime")
  @Temporal(value = TemporalType.TIMESTAMP)
  private Calendar videoTime;

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(
      name = "videoId",
      referencedColumnName = "id"
  )
  private Video video;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getWords() {
    return words;
  }

  public void setWords(String words) {
    this.words = words;
  }

  public Calendar getVideoTime() {
    return videoTime;
  }

  public void setVideoTime(Calendar videoTime) {
    this.videoTime = videoTime;
  }

  @Override
  public String toString() {
    return String.format("{sentence: {id: %d, videoTime: %s,},}", id, videoTime);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || id == null) {
      return false;
    }
    if (!(obj instanceof Sentence)) {
      throw new UnsupportedOperationException(
          "Comparison with object of a different class is undefined.");
    }

    Sentence other = (Sentence) obj;
    return id.equals(other.id)
        && videoTime.equals(other.videoTime)
        && words.equals(other.words);
  }

  @Override
  public int hashCode() {
    return Objects.hash(Sentence.class, id, videoTime, words);
  }
}
