package edu.brown.cs.termproject.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class Question extends AbstractMasterPost<QuestionUpvote> {

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "question"
  )
  @OrderBy(value = "postTime")
  private List<Response> responses = new ArrayList<>();

  public List<Response> getResponses() {
    return responses;
  }

  public void setResponses(List<Response> responses) {
    this.responses = responses;
  }

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
