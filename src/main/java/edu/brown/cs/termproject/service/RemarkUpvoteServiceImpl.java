package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.RemarkUpvoteDao;
import edu.brown.cs.termproject.model.Remark;
import edu.brown.cs.termproject.model.RemarkUpvote;
import edu.brown.cs.termproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemarkUpvoteServiceImpl implements RemarkUpvoteService {

  private RemarkUpvoteDao remarkUpvoteDao;

  @Autowired
  public RemarkUpvoteServiceImpl(RemarkUpvoteDao remarkUpvoteDao) {
    this.remarkUpvoteDao = remarkUpvoteDao;
  }

  @Override
  public RemarkUpvote add(User user, Remark remark) {
    RemarkUpvote remarkUpvote = new RemarkUpvote();

    remarkUpvote.setUser(user);
    remarkUpvote.setRemark(remark);

    remarkUpvoteDao.add(remarkUpvote);

    return remarkUpvote;
  }
}
