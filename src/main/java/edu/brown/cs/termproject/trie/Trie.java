package edu.brown.cs.termproject.trie;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
  private TrieNode root = new TrieNode('\u0000', 0, null);
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
    TrieNode previous;

    input = input.toLowerCase();

    while ((currentNode.getChildren())[charToInt(input.charAt(i))] != null) {
      previous = currentNode;
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
        previous = currentNode;
        (currentNode.getChildren())[charToInt(input.charAt(i))]
            = new TrieNode(input.charAt(i), i + 1, previous);
        currentNode = (currentNode.getChildren())[charToInt(input.charAt(i))];
        i += 1;
      }

      currentNode.addValue(time);
    } else {
      currentNode.addValue(time);
    }

  }

  List<String> traverse(List<String> outputList, TrieNode start) {
    TrieNode[] currentChildren = start.getChildren();

    for (int i = 0; i < currentChildren.length; i++) {
      if (currentChildren[i] != null) {
        if (currentChildren[i].getTimeList().size()>0) {
          outputList.add(currentChildren[i].getValue());
        }
        outputList = traverse(outputList, currentChildren[i]);
      }
    }

    return outputList;

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

        return new TreeSet<>();
      }
    }

    return new TreeSet<>();

  }

  /**
   * Traverse a trie given max led.
   *
   * @param outputList outputlist that contains all the words
   * @param start      starting trienode.
   * @param max        max led
   * @param input      input string for recursive calls
   * @return output string
   */

  public List<String> traverseMaxLED(List<String> outputList,
                                     TrieNode start, int max, String input) {
    TrieNode[] currentChildren = start.getChildren();
    int lowest = input.length() - max;

    for (int i = 0; i < currentChildren.length; i++) {
      if (currentChildren[i] != null) {
        if (currentChildren[i].getTimeList().size()>0  && currentChildren[i]
            .getLevel() >= lowest) {
          String curString = currentChildren[i].getValue();
          int led = LDistanceCalculator.calculate(curString, input);
          if (led <= max) {
            outputList.add(currentChildren[i].getValue());
          }
          int levelDiff = Math.abs(curString.length() - input.length());
          if (led <= max + levelDiff) {
            outputList = traverseMaxLED(outputList,
                currentChildren[i], max, input);
          }

        }
        outputList = traverseMaxLED(outputList, currentChildren[i], max, input);
      }
    }

    return outputList;

  }

  /**
   * This method find all words with a given prefix.
   *
   * @param input given prefix
   * @return all words
   */

  public List<String> findByPrefix(String input) {
    List<String> result = new ArrayList<>();
    input = input.toLowerCase();
    int i = 0;
    TrieNode currentNode = root;
    while ((currentNode.getChildren())[charToInt(input.charAt(i))] != null) {
      currentNode
          = (currentNode.getChildren())[charToInt(input.charAt(i))];

      if (input.length() > i + 1) {
        i += 1;
      } else if (input.length() == i + 1) {
        i += 1;
        break;
      }
    }

    if (i < input.length()) {
      return result;
    }

    return traverse(result, currentNode);

  }

  /**
   * Returns the root of the trie.
   *
   * @return root
   */


  public TrieNode getRoot() {
    return root;
  }

  public Set<String> getTopRec(Integer number, String input){
    Set<String> results = new HashSet<>();
    List<String> prfixResults = findByPrefix(input);

    while(results.size()<number & prfixResults.size()!=0){
      results.add(prfixResults.remove(0));
    }

    if(results.size()==number){
      return results;
    }

    List<String> LEDresults = new ArrayList<>();
    traverseMaxLED(LEDresults,root,2,input);

    while(results.size()<number & LEDresults.size()!=0){
      results.add(LEDresults.remove(0));
    }

    return results;

  }



}
