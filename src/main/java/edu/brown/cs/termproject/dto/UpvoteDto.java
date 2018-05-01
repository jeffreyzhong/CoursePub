package edu.brown.cs.termproject.dto;

import edu.brown.cs.termproject.model.AbstractUpvote;
import edu.brown.cs.termproject.model.QuestionUpvote;
import edu.brown.cs.termproject.model.ResponseUpvote;

import java.util.Map;

public class UpvoteDto implements Dto<AbstractUpvote> {

  public UpvoteDto(Map<String, ?> values) {

  }

  public UpvoteDto(QuestionUpvote questionUpvote) {

  }

  public UpvoteDto(ResponseUpvote responseUpvote) {

  }

  @Override
  public void fill(AbstractUpvote upvote) {

  }
}
