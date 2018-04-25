package edu.brown.cs.termproject.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "responseUpvote")
public class ResponseUpvote extends AbstractUpvote<Response> {

  @Override
  protected String getTableName() {
    return "responseUpvote";
  }
}