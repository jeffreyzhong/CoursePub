package edu.brown.cs.termproject.dto;

import edu.brown.cs.termproject.model.AbstractAnswer;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.time.CalendarSerializer;

import java.util.Calendar;
import java.util.Map;

public class QuestionDto implements Dto<Question> {

  private enum RESOVLED_STATE {
      NOT_ANSWERED, STUDENT_ANSWERED, INSTRUCTOR_ANSWERED;
  }

  /* request */
  private String questionTimestamp;
  private String summary;
  private String detail;
  private Integer videoId;

  /* fill */
  private Long time;
  private Integer id;
  private Integer user;
  private Integer resolved;
  private Integer upvotes;
  private String postTime;
  private String postDate;
  private AnswerDto instructorAnswer;
  private AnswerDto studentAnswer;

  public QuestionDto(Map<String, ?> values)
      throws IllegalArgumentException {
    try {
      this.questionTimestamp = (String) values.get("questionTimestamp");
      this.summary = (String) values.get("summary");
      this.detail = (String) values.get("detail");
      this.videoId = ((Double) values.get("videoId")).intValue();
    } catch (Throwable t) {
      throw new IllegalArgumentException(t);
    }
  }

  public QuestionDto(Question question) {
    this.questionTimestamp = "";
    this.videoId = question.getVideo().getId();
    this.summary = question.getTitle();
    this.detail = question.getBody();
    fill(question);
  }

  @Override
  public void fill(Question question) {
    Calendar cal = question.getPostTime();
    AbstractAnswer instructorAnswer = question.getInstructorAnswer();
    AbstractAnswer studentAnswer = question.getStudentAnswer();

    this.time = question.getVideoTime().getTimeInMillis() / 1000;
    this.id = question.getId();
    this.user = question.getUser().getId();
    this.upvotes = question.getUpvotes().size();
    this.postDate = CalendarSerializer.toDate(cal);
    this.postTime = CalendarSerializer.toTime(cal);

    if (instructorAnswer != null) {
      this.instructorAnswer = new AnswerDto(instructorAnswer);
    }

    if (studentAnswer != null) {
      this.studentAnswer = new AnswerDto(studentAnswer);
    }

    if (instructorAnswer != null) {
      this.resolved = RESOVLED_STATE.INSTRUCTOR_ANSWERED.ordinal();
    } else if (studentAnswer != null) {
      this.resolved = RESOVLED_STATE.STUDENT_ANSWERED.ordinal();
    } else {
      this.resolved = RESOVLED_STATE.NOT_ANSWERED.ordinal();
    }
  }

  public String getQuestionTimestamp() {
    return questionTimestamp;
  }

  public String getSummary() {
    return summary;
  }

  public String getDetail() {
    return detail;
  }

  public Long getTime() {
    return time;
  }

  public Integer getVideoId() {
    return videoId;
  }
}
