package edu.brown.cs.termproject.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "studentAnswerUpvote")
public class StudentAnswerUpvote extends AbstractUpvote<StudentAnswer> {

  @Override
  protected String getTableName() {
    return "studentAnswerUpvote";
  }
}
