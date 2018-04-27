package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.ResponseDao;
import edu.brown.cs.termproject.dao.ResponseUpvoteDao;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.ResponseUpvote;
import edu.brown.cs.termproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service
public class ResponseServiceImpl implements ResponseService {

  private ResponseDao responseDao;
  private ResponseUpvoteDao responseUpvoteDao;

  @Autowired
  public ResponseServiceImpl(ResponseDao responseDao,
                             ResponseUpvoteDao responseUpvoteDao) {
    this.responseDao = responseDao;
    this.responseUpvoteDao = responseUpvoteDao;
  }

  @Override
  @Transactional(readOnly = false)
  public Response add(User user, Question question, String body) {
    Response response = new Response();

    response.setUser(user);
    response.setQuestion(question);
    response.setBody(body);
    response.setPostTime(Calendar.getInstance());

    responseDao.add(response);

    question.addResponse(response);

    return response;
  }

  @Override
  @Transactional(readOnly = false)
  public ResponseUpvote upvote(User user, Response response)
      throws IllegalArgumentException {
    if (responseUpvoteDao.exists(user, response)) {
      throw new IllegalArgumentException(
          String.format("%s has already upvoted %s.", user, response));
    }

    ResponseUpvote responseUpvote = new ResponseUpvote();
    responseUpvote.setUser(user);
    responseUpvote.setPost(response);

    responseUpvoteDao.add(responseUpvote);

    response.addUpvote(responseUpvote);

    return responseUpvote;
  }
}
