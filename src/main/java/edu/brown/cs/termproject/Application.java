package edu.brown.cs.termproject;

import edu.brown.cs.termproject.controller.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Filter;

/**
 * Main class of the project.
 *
 * @author yqin
 */
@SpringBootApplication
@RestController
@EnableOAuth2Client
@EnableAuthorizationServer
@Order(200)
public class Application extends WebSecurityConfigurerAdapter {

  private OAuth2ClientContext oAuth2ClientContext;
  private AuthenticationSuccessHandler authenticationSuccessHandler;

  @Autowired
  public Application(OAuth2ClientContext oAuth2ClientContext,
                     LoginSuccessHandler loginSuccessHandler) {
    this.oAuth2ClientContext = oAuth2ClientContext;
    this.authenticationSuccessHandler = loginSuccessHandler;
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().mvcMatchers("/static/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/**").authorizeRequests()
        .antMatchers("/home", "/login/google", "/homePageSearchSuggestions","/whoami").permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login/google"))
        .and()
        .logout().logoutSuccessUrl("/home").permitAll()
        .and()
        .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .addFilterBefore(getFilter(), BasicAuthenticationFilter.class);
  }

  private Filter getFilter() {
    OAuth2ClientAuthenticationProcessingFilter googleFilter =
        new OAuth2ClientAuthenticationProcessingFilter("/login/google");

    OAuth2RestTemplate googleTemplate =
        new OAuth2RestTemplate(google(), oAuth2ClientContext);
    googleFilter.setRestTemplate(googleTemplate);

    UserInfoTokenServices tokenServices =
        new UserInfoTokenServices(googleResource().getUserInfoUri(),
            google().getClientId());
    tokenServices.setRestTemplate(googleTemplate);
    googleFilter.setTokenServices(tokenServices);

    googleFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);

    return googleFilter;
  }

  @Bean
  @ConfigurationProperties(value = "security.oauth2.client")
  public AuthorizationCodeResourceDetails google() {
    return new AuthorizationCodeResourceDetails();
  }

  @Bean
  @ConfigurationProperties(value = "security.oauth2.resource")
  public ResourceServerProperties googleResource() {
    return new ResourceServerProperties();
  }

  /**
   * Starts execution of all codes.
   *
   * @param args arguments
   * @author yqin
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
