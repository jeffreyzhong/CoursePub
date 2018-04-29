package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.RemarkDao;
import edu.brown.cs.termproject.dao.RemarkUpvoteDao;
import edu.brown.cs.termproject.model.Remark;
import edu.brown.cs.termproject.model.RemarkUpvote;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service
public class RemarkServiceImpl implements RemarkService {

  private RemarkDao remarkDao;
  private RemarkUpvoteDao remarkUpvoteDao;

  @Autowired
  public RemarkServiceImpl(RemarkDao remarkDao,
                           RemarkUpvoteDao remarkUpvoteDao) {
    this.remarkDao = remarkDao;
    this.remarkUpvoteDao = remarkUpvoteDao;
  }

  @Override
  @Transactional(readOnly = false)
  public Remark add(User user, Calendar time, String title, String body,
                    Video video) {
    Remark remark = new Remark();

    remark.setUser(user);
    remark.setTime(time);
    remark.setTitle(title);
    remark.setBody(body);
    remark.setVideo(video);
    remark.setPostTime(Calendar.getInstance());

    remarkDao.add(remark);

    return remark;
  }

  @Override
  @Transactional(readOnly = false)
  public RemarkUpvote upvote(User user, Remark remark) {
    if (remarkUpvoteDao.exists(user, remark)) {
      throw new IllegalArgumentException(
          String.format("%s has already upvoted %s.", user, remark));
    }

    RemarkUpvote remarkUpvote = new RemarkUpvote();
    remarkUpvote.setUser(user);
    remarkUpvote.setPost(remark);

    remarkUpvoteDao.add(remarkUpvote);

    return remarkUpvote;
  }
}
