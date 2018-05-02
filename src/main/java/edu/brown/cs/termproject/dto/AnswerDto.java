package edu.brown.cs.termproject.dto;

import edu.brown.cs.termproject.model.AbstractAnswer;
import edu.brown.cs.termproject.time.CalendarSerializer;

import java.util.Calendar;
import java.util.Map;

public class AnswerDto implements Dto<AbstractAnswer> {

  /* request */
  private Integer questionId;
  private String detail;

  /* fill */
  private Integer id;
  private Integer userId;
  private String postTime;
  private String postDate;

  public AnswerDto(Map<String, ?> values)
      throws IllegalArgumentException {
    try {
      this.questionId = ((Double) values.get("questionId")).intValue();
      this.detail = (String) values.get("detail");
    } catch (Throwable t) {
      throw new IllegalArgumentException(t);
    }
  }

  public AnswerDto(AbstractAnswer answer) {
    this.questionId = answer.getQuestion().getId();
    this.detail = answer.getBody();
    fill(answer);
  }

  @Override
  public void fill(AbstractAnswer answer) {
    Calendar cal = answer.getPostTime();

    this.id = answer.getId();
    this.userId = answer.getId();
    this.postTime = CalendarSerializer.toTime(cal);
    this.postDate = CalendarSerializer.toDate(cal);
  }

  public Integer getQuestionId() {
    return questionId;
  }

  public String getDetail() {
    return detail;
  }
}
