package edu.brown.cs.termproject.model;

import edu.brown.cs.termproject.collect.PickySet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "question")
public class Question extends AbstractMasterPost<QuestionUpvote> {

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "question",
      fetch = FetchType.EAGER
  )
  private Set<Response> responses = new PickySet<>();

  @Override
  protected String getTableName() {
    return "question";
  }

  public void addResponse(Response response) {
    responses.add(response);
  }

  public void removeResponse(Response response) {
    responses.remove(response);
  }
}
