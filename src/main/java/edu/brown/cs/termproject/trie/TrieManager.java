package edu.brown.cs.termproject.trie;

import edu.brown.cs.termproject.model.Video;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class TrieManager {

  private static HashMap<Integer, Trie> trieMap = new HashMap<>();

  public static void insertVideoTranscript(Integer videoId, Map<String, Double>
      sentenceTimeSet) {
    if (trieMap.containsKey(videoId)) {
      return;
    } else {
      Trie tempTrie = new Trie();
      trieMap.put(videoId, tempTrie);

      for (String st : sentenceTimeSet.keySet()) {
        Double time = sentenceTimeSet.get(st);
        tempTrie.insertSentenceTime(time, st);
        String[] words = st.split("\\s+");
        tempTrie.insertList(Arrays.asList(words), time);
      }
    }
  }

  public static List<String> getWordTimeList(String word, Integer videoId,
                                Double start, Double end){

    Trie tempTrie = trieMap.get(videoId);
    ArrayList<String> result = new ArrayList<>();
    Set<Double> timeList = tempTrie.searchTimeList(word);
    for(Double d:timeList){
      if(d>=start & d<=end) {
        result.add(Double.toString(d));
        result.add(tempTrie.getSentence(d));
      }
    }


    return result;
  }

}
