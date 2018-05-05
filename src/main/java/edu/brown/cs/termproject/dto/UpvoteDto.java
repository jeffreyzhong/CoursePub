package edu.brown.cs.termproject.dto;

import edu.brown.cs.termproject.model.AbstractUpvote;
import edu.brown.cs.termproject.model.QuestionUpvote;
import edu.brown.cs.termproject.model.ResponseUpvote;

import java.util.Map;

public class UpvoteDto implements Dto<AbstractUpvote> {

  /* request */
  private Integer upvoteType;
  private Integer id;

  /* fill */
  private Integer num;

  public UpvoteDto(Map<String, ?> values) throws IllegalArgumentException {
    try {
      this.upvoteType = ((Double) values.get("upvoteType")).intValue();
      this.id = ((Double) values.get("id")).intValue();
    } catch (Throwable t) {
      throw new IllegalArgumentException(t);
    }
  }

  public UpvoteDto(QuestionUpvote questionUpvote) {
    fill(questionUpvote);
  }

  public UpvoteDto(ResponseUpvote responseUpvote) {
    fill(responseUpvote);
  }

  public Integer getUpvoteType() {
    return upvoteType;
  }

  public Integer getId() {
    return id;
  }

  @Override
  public void fill(AbstractUpvote upvote) {
    if (upvote != null) {
      this.num = 1;
    } else {
      this.num = 0;
    }
  }
}
