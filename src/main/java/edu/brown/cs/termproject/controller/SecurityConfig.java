package edu.brown.cs.termproject.controller;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @GetMapping("/whoami")
  @ResponseBody
  public Principal whoami(Principal principal) { return principal; }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .antMatcher("/**").authorizeRequests()
        .antMatchers("/", "/login**", "/hello").permitAll()
        .anyRequest().authenticated()
        .and()
        .logout().logoutSuccessUrl("/").permitAll()
        .and()
        .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().mvcMatchers("/static/**");
  }
}
