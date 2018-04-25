package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Remark;
import edu.brown.cs.termproject.model.RemarkUpvote;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.model.Video;

import java.util.Date;

public interface RemarkService
    extends AbstractUpvoteService<Remark, RemarkUpvote> {

  Remark add(User user, Date time, String title, String body, Video video);
}
