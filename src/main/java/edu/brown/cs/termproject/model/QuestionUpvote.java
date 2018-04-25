package edu.brown.cs.termproject.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "questionUpvote")
public class QuestionUpvote extends AbstractUpvote<Question> {

  @Override
  protected String getTableName() {
    return "questionUpvote";
  }
}


