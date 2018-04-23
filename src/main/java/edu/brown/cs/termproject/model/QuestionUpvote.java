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
import java.util.Objects;

@Entity
@Table(name = "questionUpvote")
public class QuestionUpvote {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(name = "questionId", referencedColumnName = "id")
  private Question question;

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "id")
  private User user;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Question getQuestion() {
    return question;
  }

  public User getUser() {
    return user;
  }

  public void setQuestion(Question question) {
    question.addQuestionUpvote(this);
    this.question = question;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return String.format("{questionUpvote: {id: %d, questionId: %s, userId: %d,},}", id, question.getId(), user.getId());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || id == null) {
      return false;
    }
    if (!(obj instanceof QuestionUpvote)) {
      throw new UnsupportedOperationException(
          "Comparison with object of a different class is undefined.");
    }

    QuestionUpvote other =
        (QuestionUpvote) obj;
    return id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(QuestionUpvote.class, id);
  }
}


