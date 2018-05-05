package edu.brown.cs.termproject.trie;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * This is a Trie class that represents a char and its location in the tree.
 *
 * @author sy69
 */

public class Trie {
  private static String referenceString = "abcdefghijklmnopqrstuvwxyz"
      + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private TrieNode root = new TrieNode('\u0000', 0);
  private Map<Double, String> timeMap = new HashMap<>();



  public void insertSentenceTime(Double time, String sentence){
    timeMap.put(time, sentence);
  }

  public String getSentence(Double time){
    return timeMap.get(time);
  }




  /**
   * Takes in a list of strings to the trie.
   *
   * @param input a list of strings.
   */

  public void insertList(List<String> input, Double time) {
    for (String temp : input) {
      if (!temp.equals("")) {
        //insert time as well
        this.insert(temp, time);
      }
    }
  }

  /**
   * Getter of referenceString.
   *
   * @return reference
   */

  public static String getReferenceString() {
    return referenceString;
  }

  /**
   * Takes in a word to the trie.
   *
   * @param input a word
   */

  private void insert(String input, Double time) {

    input = input.replaceAll("[^a-zA-Z ]", "")
        .replaceAll("\\s+", "")
        .trim();

    if (input.equals("")) {
      return;
    }

    int i = 0;
    TrieNode currentNode = root;

    input = input.toLowerCase();


    while ((currentNode.getChildren())[charToInt(input.charAt(i))] != null) {
      currentNode
          = (currentNode.getChildren())[charToInt(input.charAt(i))];
      if (input.length() > i + 1) {
        i += 1;
      } else if (input.length() == i + 1) {
        currentNode.addValue(time);
        return;
      } else {
        return;
      }
    }

    if (i + 1 <= input.length()) {
      while (i + 1 <= input.length()) {
        (currentNode.getChildren())[charToInt(input.charAt(i))]
            = new TrieNode(input.charAt(i), i + 1);
        currentNode = (currentNode.getChildren())[charToInt(input.charAt(i))];
        i += 1;
      }

      currentNode.addValue(time);
    } else {
      currentNode.addValue(time);
    }

  }

  private int charToInt(char c) {

    return referenceString.indexOf(c);
  }

  /**
   * Tells if a string is word in the trie.
   *
   * @param input input string.
   * @return a boolean indicating if the string is a word.
   */

  public Set<Double> searchTimeList(String input) {


    input = input.toLowerCase();

    int i = 0;
    TrieNode currentNode = root;

    while ((currentNode.getChildren())[charToInt(input.charAt(i))] != null) {
      currentNode
          = (currentNode.getChildren())[charToInt(input.charAt(i))];
      if (input.length() > i + 1) {
        i += 1;
      } else {
        if(currentNode.getTimeList().size()>0){
          return currentNode.getTimeList();
        }
      }
    }

    return new TreeSet<>();

  }

  /**
   * Returns the root of the trie.
   *
   * @return root
   */


  public TrieNode getRoot() {
    return root;
  }



}
