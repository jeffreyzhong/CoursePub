package edu.brown.cs.termproject.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "instructorAnswerUpvote")
public class InstructorAnswerUpvote extends AbstractUpvote<InstructorAnswer> {

  @Override
  protected String getTableName() {
    return "instructorAnswerUpvote";
  }
}
