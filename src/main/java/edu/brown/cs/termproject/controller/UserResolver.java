package edu.brown.cs.termproject.controller;

import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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
      return userService.getOrAdd(nativeWebRequest.getUserPrincipal());
    } catch (IllegalArgumentException e) {
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
