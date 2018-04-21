package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.RegistrationDao;
import edu.brown.cs.termproject.model.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

  private RegistrationDao registrationDao;

  @Autowired
  public RegistrationServiceImpl(RegistrationDao registrationDao) {
    this.registrationDao = registrationDao;
  }

  @Override
  public void add(Registration registration) {
    registrationDao.add(registration);
  }
}
