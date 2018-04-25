package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.ResponseUpvoteDao;
import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.ResponseUpvote;
import edu.brown.cs.termproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResponseUpvoteServiceImpl implements ResponseUpvoteService {

  private ResponseUpvoteDao responseUpvoteDao;

  @Autowired
  public ResponseUpvoteServiceImpl(ResponseUpvoteDao responseUpvoteDao) {
    this.responseUpvoteDao = responseUpvoteDao;
  }

  @Override
  @Transactional(readOnly = false)
  public ResponseUpvote add(User user, Response response) {
    ResponseUpvote responseUpvote = new ResponseUpvote();

    responseUpvote.setUser(user);
    responseUpvote.setResponse(response);

    responseUpvoteDao.add(responseUpvote);

    return responseUpvote;
  }
}
