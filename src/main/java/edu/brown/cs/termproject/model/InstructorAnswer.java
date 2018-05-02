package edu.brown.cs.termproject.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "instructorAnswer")
public class InstructorAnswer extends AbstractAnswer<InstructorAnswerUpvote> {

  @Override
  protected String getTableName() {
    return "instructorAnswer";
  }
}
