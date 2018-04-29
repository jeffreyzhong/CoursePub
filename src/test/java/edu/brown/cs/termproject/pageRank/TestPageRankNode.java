package edu.brown.cs.termproject.pageRank;

import edu.brown.cs.termproject.model.QuestionUpvote;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TestPageRankNode implements PageRankNode<TestPageRankNode>{
  TestPageRankNode first;
  TestPageRankNode second;
  int id;

  public TestPageRankNode(int myId){
    id=myId;
  }

  public int getId() {
    return id;
  }

  public void setNodes(TestPageRankNode a, TestPageRankNode b){
    first = a;
    second = b;
  }

  @Override
  public Map<TestPageRankNode, Double> getDsts() {
    Map<TestPageRankNode, Double> result = new HashMap<>();
    result.put(first, 0.5);
    result.put(second, 0.5);

    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null ) {
      return false;
    }

    TestPageRankNode tpn = (TestPageRankNode)obj;

    return id-tpn.getId()==0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(TestPageRankNode.class, id);
  }
}
