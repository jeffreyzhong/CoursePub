package edu.brown.cs.termproject;

import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Main class of the project.
 *
 * @author yqin
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

  private ApplicationContext appContext;

  @Autowired
  public Application(ApplicationContext appContext) {
    this.appContext = appContext;
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

  @Override
  public void run(String... args) throws Exception {
    UserService service = appContext.getBean("userServiceImpl", UserService.class);

    for (User u : service.getAllUsers()) {
      System.out.println(u);
    }
  }
}
