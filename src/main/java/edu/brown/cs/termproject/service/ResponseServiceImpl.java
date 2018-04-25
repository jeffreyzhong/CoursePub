package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.ResponseDao;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResponseServiceImpl implements ResponseService {

  private ResponseDao responseDao;

  @Autowired
  public ResponseServiceImpl(ResponseDao responseDao) {
    this.responseDao = responseDao;
  }

  @Override
  @Transactional(readOnly = false)
  public Response add(User user, Question question, String body) {
    Response response = new Response();

    response.setUser(user);
    response.setQuestion(question);
    response.setBody(body);

    responseDao.add(response);

    return response;
  }
}
