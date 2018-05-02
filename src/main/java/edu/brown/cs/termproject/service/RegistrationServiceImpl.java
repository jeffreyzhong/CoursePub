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
  public Registration add(User user, Course course, Integer type)
      throws IllegalArgumentException {
    if (registrationDao.exists(user, course)) {
      throw new IllegalArgumentException(
          String.format("%s has already registered in %s.", user, course));
    }

    Registration registration = new Registration();

    registration.setCourse(course);
    registration.setUser(user);
    registration.setType(type);

    registrationDao.add(registration);

    user.register(registration);
    course.register(registration);

    return registration;
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isInstructor(User user, Course course) {
    if (!registrationDao.exists(user, course)) {
      return false;
    }

    return registrationDao.get(user, course).getType() == 1;
  }
}
