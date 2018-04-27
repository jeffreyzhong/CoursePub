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
        .antMatchers("/", "/login/google").permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login/google"))
        .and()
        .logout().logoutSuccessUrl("/").permitAll()
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
  //  @Autowired
//  private void build(UserService uS, CourseService cS, RegistrationService rS,
//                VideoService vS, QuestionService qS, ResponseService reS) {
//    String[] emails = new String[] {
//        "yujun_qin@brown.edu",
//        "xinyang_zhou@brown.edu",
//        "song_yang@brown.edu",
//        "jeffrey_zhong@brown.edu"
//    };
//
//    for (String email : emails) {
//      uS.add(email);
//    }
//
//    Course course = cS.add("cs032");
//
//    for (User u : uS.getAllUsers()) {
//      rS.add(u, course, 0);
//    }
//
//    Video video = vS.add("https://google.com", course);
//
//    Calendar c = Calendar.getInstance();
//    c.setTimeInMillis(5000);
//
//    Question question1 = qS.add(uS.ofId(1), c, "what is happening", "hi jj", video);
//
//    QuestionUpvote questionUpvote = qS.upvote(uS.ofId(2), question1);
//
//    Response response = reS.add(uS.ofId(3), question1, "this does not make sense.");
//
//    ResponseUpvote responseUpvote = reS.upvote(uS.ofId(1), response);
//
//    c.setTimeInMillis(15000);
//    Question question2 = qS.add(uS.ofId(3), c, "another question", "blah", video);
//    c.setTimeInMillis(62000);
//    Question question3 = qS.add(uS.ofId(3), c, "yet another", "blahblah", video);
//  }

}
