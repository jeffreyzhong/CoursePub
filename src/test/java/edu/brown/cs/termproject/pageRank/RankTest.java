package edu.brown.cs.termproject.pageRank;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RankTest {
  @Test
  public void simpleTest(){
    TestPageRankNode t1 = new TestPageRankNode(1);
    TestPageRankNode t2 = new TestPageRankNode(2);
    TestPageRankNode t3 = new TestPageRankNode(3);
    t1.setNodes(t2,t3);
    t2.setNodes(t1,t3);
    t3.setNodes(t1,t2);

    List<TestPageRankNode> list = new ArrayList<>();
    list.add(t1);
    list.add(t2);
    list.add(t3);

    PageRank<TestPageRankNode> pr = new PageRank<>(list,1);

    System.out.println(pr.calc());

  }

}
