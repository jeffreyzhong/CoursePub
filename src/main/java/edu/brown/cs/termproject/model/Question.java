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
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "question")
public class Question {

  @Id
  @Column(name = "id")
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(
      name = "uuid",
      strategy = "uuid2"
  )
  private String id;

  private Post post = new Post();

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(
      name = "videoId",
      referencedColumnName = "id"
  )
  private Video video;

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "question",
      fetch = FetchType.EAGER
  )
  private Set<QuestionUpvote> questionUpvotes = new PickySet<>();

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

  public void addQuestionUpvote(QuestionUpvote questionUpvote) {
    questionUpvotes.add(questionUpvote);
  }

  public void removeResponseUpvote(QuestionUpvote questionUpvote) {
    questionUpvotes.remove(questionUpvote);
  }

  public Set<QuestionUpvote> getQuestionUpvotes() {
    return Collections.unmodifiableSet(questionUpvotes);
  }

  public void setQuestionUpvotes(Set<QuestionUpvote> questionUpvotes) {
    this.questionUpvotes = questionUpvotes;
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
