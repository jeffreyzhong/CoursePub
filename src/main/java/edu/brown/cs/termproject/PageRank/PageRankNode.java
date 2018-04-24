package edu.brown.cs.termproject.PageRank;

import java.util.List;
import java.util.Map;

public interface PageRankNode<P extends PageRankNode> {

  public Map<P, Double> getDsts();


  public double getCurWeight();


}
