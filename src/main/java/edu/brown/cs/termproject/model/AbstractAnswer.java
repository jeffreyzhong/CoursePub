package edu.brown.cs.termproject.model;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public abstract class AbstractAnswer<T extends AbstractUpvote>
    extends AbstractPost<T> {

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(
      name = "questionId",
      referencedColumnName = "id"
  )
  private Question question;

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }
}
