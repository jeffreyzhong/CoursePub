package edu.brown.cs.termproject.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "remarkUpvote")
public class RemarkUpvote extends AbstractUpvote<Remark> {

  @Override
  protected String getTableName() {
    return "remarkUpvote";
  }
}

