package edu.brown.cs.termproject.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "studentAnswer")
public class StudentAnswer extends AbstractAnswer<StudentAnswerUpvote> {

  @Override
  protected String getTableName() {
    return "studentAnswer";
  }
}
