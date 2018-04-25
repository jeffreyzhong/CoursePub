package edu.brown.cs.termproject.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "remark")
public class Remark extends AbstractMasterPost<RemarkUpvote> {

  @Override
  protected String getTableName() {
    return "remark";
  }
}
