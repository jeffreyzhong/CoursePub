package edu.brown.cs.termproject.PageRank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageRank<P extends PageRankNode> {

  List<P> list;

  public PageRank(List<P> nodeList){
    list = nodeList;
  }


  public Map<P, Double> calc() {


    double dampingFactor = 0.85;
    double initialPageRank = 1 / list.size();
    Map<P, Double> tempRank = new HashMap<>();
    Map<P, Double> curRank = new HashMap<>();

    //initial setup
    for (P p : list) {
      tempRank.put(p, initialPageRank);
    }

    while (isDifferent(tempRank, curRank)) {
      tempRank = curRank;
      curRank = new HashMap<>();
      for (P p : list) {
        curRank.put(p, (1 - dampingFactor) * initialPageRank);
      }

      for (P p : list) {
        Double curWeight = tempRank.get(p);
        Map<P, Double> destinations = p.getDsts();

        for (P destP : destinations.keySet()) {
          Double valueAdded = dampingFactor
              *(curWeight / (double) destinations.size())
              *destinations.get(p);
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
    double cumulator = 0;

    for(P p:list) {
      cumulator += Math.pow(tempRank.get(p) - curRank.get(p), 2);
    }

    return cumulator<=0.1;
  }

}

