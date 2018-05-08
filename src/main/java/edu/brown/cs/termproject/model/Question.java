package edu.brown.cs.termproject.model;

import edu.brown.cs.termproject.nlp.TfIdf;
import edu.brown.cs.termproject.nlp.TfIdfDocument;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "question")
public class Question extends AbstractMasterPost<QuestionUpvote>
    implements TfIdfDocument {

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "question"
  )
  @OrderBy(value = "postTime")
  private List<Response> responses = new ArrayList<>();

  @OneToOne(
      cascade = CascadeType.ALL,
      mappedBy = "question"
  )
  private InstructorAnswer instructorAnswer;

  @OneToOne(
      cascade = CascadeType.ALL,
      mappedBy = "question"
  )
  private StudentAnswer studentAnswer;

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

  @Override
  public Iterator<String> words() {
    return TfIdf.SANITIZER.apply(getTitle()).iterator();
  }

  public InstructorAnswer getInstructorAnswer() {
    return instructorAnswer;
  }

  public void setInstructorAnswer(InstructorAnswer instructorAnswer) {
    this.instructorAnswer = instructorAnswer;
  }

  public StudentAnswer getStudentAnswer() {
    return studentAnswer;
  }

  public void setStudentAnswer(StudentAnswer studentAnswer) {
    this.studentAnswer = studentAnswer;
  }

  public void addResponse(Response response) {
    responses.add(response);
  }

  public void removeResponse(Response response) {
    responses.remove(response);
  }
}
