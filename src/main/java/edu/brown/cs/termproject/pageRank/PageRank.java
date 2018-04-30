package edu.brown.cs.termproject.pageRank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageRank {

  List<PageRankNode> list;
  int srcNode;

  public PageRank(List<PageRankNode> nodeList, int src){
    list = nodeList;
    srcNode = src;
  }

  public Map<PageRankNode, Double> calc() {


    double dampingFactor = 0.4;
    double initialPageRank = 1.0 / (double)list.size();
    Map<PageRankNode, Double> tempRank = new HashMap<>();
    Map<PageRankNode, Double> curRank = new HashMap<>();

    //initial setup
    for (PageRankNode p : list) {
      curRank.put(p, initialPageRank);
    }

    while (isDifferent(tempRank, curRank)) {
      tempRank = curRank;
      curRank = new HashMap<>();
      for (PageRankNode p : list) {
        curRank.put(p, 0.0);
      }
      PageRankNode srcP = list.get(srcNode);
      curRank.put(srcP, (1 - dampingFactor) * 1);


      for (PageRankNode p : list) {
        Double curWeight = tempRank.get(p);
        Map<PageRankNode, Double> destinations = p.getDsts();

        for (PageRankNode destP : destinations.keySet()) {
          Double valueAdded = dampingFactor
              * curWeight * destinations.get(destP);
          Double value = curRank.get(destP);
          value += valueAdded;
          curRank.put(destP, value);
        }
      }
    }

    return curRank;


  }

  private boolean isDifferent(Map<PageRankNode, Double> tempRank,
                              Map<PageRankNode, Double> curRank) {
    if(tempRank.size()!=curRank.size()){
      return true;
    }

    double cumulator = 0;

    for(PageRankNode p:list) {
      cumulator += Math.pow(tempRank.get(p) - curRank.get(p), 2);
    }

    return cumulator>=0.0001;
  }

}

