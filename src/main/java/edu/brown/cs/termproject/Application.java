package edu.brown.cs.termproject;

import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Question;
import edu.brown.cs.termproject.model.QuestionUpvote;
import edu.brown.cs.termproject.model.Response;
import edu.brown.cs.termproject.model.ResponseUpvote;
import edu.brown.cs.termproject.model.User;
import edu.brown.cs.termproject.model.Video;
import edu.brown.cs.termproject.service.CourseService;
import edu.brown.cs.termproject.service.QuestionService;
import edu.brown.cs.termproject.service.RegistrationService;
import edu.brown.cs.termproject.service.ResponseService;
import edu.brown.cs.termproject.service.UserService;
import edu.brown.cs.termproject.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.ApplicationContext;

import java.util.Calendar;

/**
 * Main class of the project.
 *
 * @author yqin
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

  private UserService uS;
  private CourseService cS;
  private RegistrationService rS;
  private VideoService vS;
  private QuestionService qS;
  private ResponseService reS;


  @Autowired
  public Application(UserService uS, CourseService cS, RegistrationService rS,
                     VideoService vS, QuestionService qS, ResponseService reS) {
    this.uS = uS;
    this.cS = cS;
    this.rS = rS;
    this.vS = vS;
    this.qS = qS;
    this.reS = reS;
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
  public void run(String... args) {
    /*
    String[] emails = new String[] {
        "yujun_qin@brown.edu",
        "xinyang_zhou@brown.edu",
        "song_yang@brown.edu",
        "jeffrey_zhong@brown.edu"
    };

    for (String email : emails) {
      uS.add(email);
    }

    Course course = cS.add("cs032");

    for (User u : uS.getAllUsers()) {
      rS.add(u, course, 0);
    }

    Video video = vS.add("https://google.com", course);

    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(5000);

    Question question1 = qS.add(uS.ofId(1), c, "what is happening", "hi jj", video);

    QuestionUpvote questionUpvote = qS.upvote(uS.ofId(2), question1);

    Response response = reS.add(uS.ofId(3), question1, "this does not make sense.");

    ResponseUpvote responseUpvote = reS.upvote(uS.ofId(1), response);

    c.setTimeInMillis(15000);
    Question question2 = qS.add(uS.ofId(3), c, "another question", "blah", video);
    c.setTimeInMillis(62000);
    Question question3 = qS.add(uS.ofId(3), c, "yet another", "blahblah", video);
    */
  }
}
