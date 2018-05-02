package edu.brown.cs.termproject.pageRank;

import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.service.CourseService;
import edu.brown.cs.termproject.service.UserService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.brown.cs.termproject.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;



@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RankTest {

  @Autowired
  private UserService userService;

  @Autowired
  private CourseService courseService;

  @Test
  public void testUserCourse() throws ClassNotFoundException{

    PageRank pr = new PageRank(userService.ofId(5));
    Class c =
      Class.forName("edu.brown.cs.termproject.model.Course");
    List<PageRankNode> result = pr.getTopResult(c, 2);
    System.out.println(result);


  }

  @Test
  public void simpleTest(){
    TestPageRankNode t1 = new TestPageRankNode(1);
    TestPageRankNode t2 = new TestPageRankNode(2);
    TestPageRankNode t3 = new TestPageRankNode(3);
    t1.setNodes(t2,t3);
    t2.setNodes(t1,t3);
    t3.setNodes(t1,t2);


    PageRank pr = new PageRank(t1);

  }


}
