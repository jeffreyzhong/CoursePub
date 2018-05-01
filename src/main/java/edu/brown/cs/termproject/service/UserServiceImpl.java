package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.UserDao;
import edu.brown.cs.termproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.support.WebArgumentResolver;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

  private UserDao userDao;

  @Autowired
  public UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> getAllUsers() {
    return userDao.getAllUsers();
  }

  @Override
  @Transactional(readOnly = false)
  public User add(String email) {
    if (userDao.hasEmail(email)) {
      throw new IllegalArgumentException("The email has already been taken.");
    }

    User user = new User();
    user.setEmail(email);

    userDao.add(user);

    return user;
  }

  @Override
  @Transactional(readOnly = true)
  public User ofId(Integer id) {
    return userDao.ofId(id);
  }

  @Override
  @Transactional(readOnly = false)
  public User getOrAdd(Principal principal) throws IllegalArgumentException {
    String email = principalToEmail(principal);
    /* returns user if email is already registered */
    if (userDao.hasEmail(email)) {
      return userDao.ofEmail(email);
    }

    /* creates user if no such email exists */
    User user = new User();
    user.setEmail(email);
    userDao.add(user);

    return user;
  }

  @Override
  @Transactional(readOnly = true)
  public User get(Principal principal) throws IllegalArgumentException {
    String email = principalToEmail(principal);

    /* returns user if email is already registered */
    if (userDao.hasEmail(email)) {
      return userDao.ofEmail(email);
    }

    /* throws exception otherwise */
    throw new IllegalArgumentException(String.format(
        "User does not exist for principal %s.", principal));
  }

  private String principalToEmail(Principal principal)
      throws IllegalArgumentException {
    try {
      /* gets current principal from request */
      OAuth2Authentication oAuth2Authentication =
          (OAuth2Authentication) principal;

      /* gets email from details, filled by Google OAuth2 */
      Map<String, String> details = (Map<String, String>) oAuth2Authentication
          .getUserAuthentication().getDetails();

      if (!details.containsKey("email")) {
        throw new IllegalArgumentException(
            "OAuth2Authentication does not have email as key.");
      }

      return details.get("email").toLowerCase();
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("Principal object is invalid.");
    }
  }
}
