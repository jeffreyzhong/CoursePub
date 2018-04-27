package edu.brown.cs.termproject.controller;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.termproject.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

  @GetMapping("/success")
  public ModelAndView success(User user) {

    Map<String, Object> variables = ImmutableMap.of(
        "title", "Login success",
        "whoami", user.toString()

    );
    return new ModelAndView("successPage", variables);
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse,
                                      Authentication authentication)
      throws IOException {
    assert (authentication instanceof OAuth2Authentication);

    httpServletResponse.sendRedirect("/success");
  }
}
