package edu.brown.cs.termproject;

import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Registration;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.service.CourseService;
import edu.brown.cs.termproject.service.RegistrationService;
import edu.brown.cs.termproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

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
    UserService uS = appContext.getBean("userServiceImpl", UserService.class);
    CourseService cS = appContext.getBean("courseServiceImpl", CourseService.class);
    RegistrationService rS = appContext.getBean("registrationServiceImpl", RegistrationService.class);

    String[] emails = new String[] {"yujun_qin@brown.edu", "xinyang_zhou@brown.edu", "song_yang@brown.edu", "jeffrey_zhong@brown.edu"};

    for (String email : emails) {
      uS.add(email);
    }

    Course course = cS.add("cs032");

    for (User u : uS.getAllUsers()) {
      System.out.println(u);
      rS.add(u, course, 0);
      System.out.println(u.getRegistrations());
    }
  }
}
