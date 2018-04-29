package edu.brown.cs.termproject.controller;

import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;

/**
 * Resolver class that gets current user from security context.
 *
 * @author yqin
 */
public class UserResolver implements HandlerMethodArgumentResolver {

  private UserService userService;

  UserResolver(UserService userService) {
    this.userService = userService;
  }

  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {
    return methodParameter.getParameterType().equals(User.class);
  }

  @Override
  public Object resolveArgument(MethodParameter methodParameter,
                                ModelAndViewContainer modelAndViewContainer,
                                NativeWebRequest nativeWebRequest,
                                WebDataBinderFactory webDataBinderFactory) {
    if (!supportsParameter(methodParameter)) {
      return WebArgumentResolver.UNRESOLVED;
    }

    try {
      /* gets current principal from request */
      OAuth2Authentication oAuth2Authentication =
          (OAuth2Authentication) nativeWebRequest.getUserPrincipal();

      /* gets email from details, filled by Google OAuth2 */
      Map<String, String> details = (Map<String, String>) oAuth2Authentication
          .getUserAuthentication().getDetails();

      if (!details.containsKey("email")) {
        return WebArgumentResolver.UNRESOLVED;
      }
      String email = details.get("email").toLowerCase();

      /* creates user if no such email exists */
      return userService.getOrAdd(email);

    } catch (ClassCastException e) {
      return WebArgumentResolver.UNRESOLVED;
    }
  }
}

/**
 * Configurations that adds <code>UserResolver</code> to list of
 * <code>HandlerMethodArgumentResolver</code>.
 *
 * @author yqin
 */
@Configuration
class UserResolverConfiguration implements WebMvcConfigurer {

  private UserService userService;

  @Autowired
  public UserResolverConfiguration(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void addArgumentResolvers(
      List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new UserResolver(userService));
  }
}
