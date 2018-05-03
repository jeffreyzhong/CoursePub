package edu.brown.cs.termproject.pageRank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PageRank {

  Set<PageRankNode> list;
  PageRankNode srcNode;
  Map<PageRankNode, Double> rankResult;
  HashSet<PageRankNode> exclusionList;
  Map<PageRankNode, Map<PageRankNode, Double>> cache = new HashMap<>();

  public PageRank(PageRankNode src) {
    srcNode = src;
    list = new HashSet<>();
    int curSize = 0;
    Map<PageRankNode, Double> temp = srcNode.getDsts();
    cache.put(srcNode, temp);
    exclusionList = new HashSet<>(temp.keySet());
    Set<PageRankNode> addition = temp.keySet();
    Set<PageRankNode> nextAdditions = new HashSet<>();
    list.addAll(addition);

    while (list.size() > curSize) {
      curSize = list.size();
      for (PageRankNode p : addition) {
        temp = p.getDsts();
        cache.put(p, temp);
        nextAdditions.addAll(temp.keySet());
      }
      list.addAll(nextAdditions);
      addition = nextAdditions;
      nextAdditions = new HashSet<>();
    }

  }

  public void calc() {

    double dampingFactor = 0.4;
    double initialPageRank = 1.0 / (double) list.size();
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

      curRank.put(srcNode, (1 - dampingFactor) * 1);


      for (PageRankNode p : list) {
        Double curWeight = tempRank.get(p);
        Map<PageRankNode, Double> destinations = cache.get(p);

        for (PageRankNode destP : destinations.keySet()) {
          Double valueAdded = dampingFactor
              * curWeight * destinations.get(destP);
          Double value = curRank.get(destP);
          value += valueAdded;
          curRank.put(destP, value);
        }
      }
    }

    rankResult = curRank;
  }


  public List<PageRankNode> getTopResult(Class className,
                                         Integer numberOfResults) {
    if (rankResult == null) {
      this.calc();
    }

    List<PageRankNode> result = new ArrayList<>();
    LinkedHashMap<PageRankNode, Double> rankedResult = sortByValue(rankResult);
//    List<PageRankNode> myList = new ArrayList<>(rankedResult.keySet());


    for (PageRankNode p : rankedResult.keySet()) {
      if (result.size() < numberOfResults) {
        if (className.isInstance(p)) {
          if(!exclusionList.contains(p)) {
            result.add(p);
          }
        }
      } else {
        return result;
      }
    }

    return result;


  }

  private boolean isDifferent(Map<PageRankNode, Double> tempRank,
                              Map<PageRankNode, Double> curRank) {
    if (tempRank.size() != curRank.size()) {
      return true;
    }

    double cumulator = 0;

    for (PageRankNode p : list) {
      cumulator += Math.pow(tempRank.get(p) - curRank.get(p), 2);
    }

    return cumulator >= 0.0001;
  }

  public static <K, V extends Comparable<? super V>> LinkedHashMap<K, V>
  sortByValue(Map<K, V> map) {
    List<Map.Entry<K, V>> list;
    list = new ArrayList<>(map.entrySet());
    list.sort(Map.Entry.comparingByValue());
    Collections.reverse(list);

    LinkedHashMap<K, V> result = new LinkedHashMap<>();
    for (Map.Entry<K, V> entry : list) {
      result.put(entry.getKey(), entry.getValue());
    }

    return result;
  }

}

