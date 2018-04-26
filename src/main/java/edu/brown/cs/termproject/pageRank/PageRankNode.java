package edu.brown.cs.termproject.pageRank;

import java.util.Map;

public interface PageRankNode<P extends PageRankNode> {

  public Map<P, Double> getDsts();


}
