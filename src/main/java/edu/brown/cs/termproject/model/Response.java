package edu.brown.cs.termproject.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "response")
public class Response extends AbstractPost<ResponseUpvote> {

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(
      name = "questionId",
      referencedColumnName = "id"
  )
  private Question question;

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
    question.addResponse(this);
  }

  @Override
  protected String getTableName() {
    return "response";
  }
}
