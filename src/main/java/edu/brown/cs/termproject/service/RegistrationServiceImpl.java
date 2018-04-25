package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.RegistrationDao;
import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Registration;
import edu.brown.cs.termproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

  private RegistrationDao registrationDao;

  @Autowired
  public RegistrationServiceImpl(RegistrationDao registrationDao) {
    this.registrationDao = registrationDao;
  }

  @Override
  @Transactional(readOnly = false)
  public void add(User user, Course course, Integer type) {
    Registration registration = new Registration();

    registration.setCourse(course);
    registration.setUser(user);
    registration.setType(type);

    registrationDao.add(registration);
  }
}
