package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class LoginSuccessHandler
    extends SavedRequestAwareAuthenticationSuccessHandler {

  private UserService userService;

  @Autowired
  public LoginSuccessHandler(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/whoami")
  @ResponseBody
  public Boolean whoami(Principal principal) {
    return principal != null;
  }

  @GetMapping("/success")
  public ModelAndView success(User user) {

    Map<String, Object> variables = ImmutableMap.of(
        "title", "Login success",
        "email", user.getEmail()

    );
    return new ModelAndView("successPage", variables);
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse,
                                      Authentication authentication)
      throws IOException, ServletException {
    try {
      userService.get(authentication);
      super.onAuthenticationSuccess(httpServletRequest, httpServletResponse,
          authentication);
    } catch (IllegalArgumentException e) {
      httpServletResponse.sendRedirect("/success");
    }
  }
}
