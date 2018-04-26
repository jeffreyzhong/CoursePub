package edu.brown.cs.termproject.pageRank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageRank<P extends PageRankNode> {

  List<P> list;
  int srcNode;

  public PageRank(List<P> nodeList, int src){
    list = nodeList;
    srcNode = src;
  }

  public Map<P, Double> calc() {


    double dampingFactor = 0.6;
    double initialPageRank = 1.0 / (double)list.size();
    Map<P, Double> tempRank = new HashMap<>();
    Map<P, Double> curRank = new HashMap<>();

    //initial setup
    for (P p : list) {
      curRank.put(p, initialPageRank);
    }

    while (isDifferent(tempRank, curRank)) {
      tempRank = curRank;
      curRank = new HashMap<>();
      for (P p : list) {
        curRank.put(p, 0.0);
      }
      P srcP = list.get(srcNode);
      curRank.put(srcP, (1 - dampingFactor) * 1);


      for (P p : list) {
        Double curWeight = tempRank.get(p);
        Map<P, Double> destinations = p.getDsts();

        for (P destP : destinations.keySet()) {
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

  private boolean isDifferent(Map<P, Double> tempRank,
                              Map<P, Double> curRank) {
    if(tempRank.size()!=curRank.size()){
      return true;
    }

    double cumulator = 0;

    for(P p:list) {
      cumulator += Math.pow(tempRank.get(p) - curRank.get(p), 2);
    }

    return cumulator<=0.01;
  }

}

