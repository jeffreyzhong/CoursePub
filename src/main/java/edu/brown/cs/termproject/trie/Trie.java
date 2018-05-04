package edu.brown.cs.termproject.trie;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This is a Trie class that represents a char and its location in the tree.
 *
 * @author sy69
 */

public class Trie {
  private static String referenceString = "abcdefghijklmnopqrstuvwxyz"
      + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private TrieNode root = new TrieNode('\u0000', 0);

  /**
   * Takes in a list of strings to the trie.
   *
   * @param input a list of strings.
   */

  public void insertList(List<String> input, Calendar time) {
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

  public void insert(String input, Calendar time) {

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

//  List<String> traverse(List<String> outputList, TrieNode start) {
//    TrieNode[] currentChildren = start.getChildren();
//
//    for (int i = 0; i < currentChildren.length; i++) {
//      if (currentChildren[i] != null) {
//        if (currentChildren[i].getValue() != null) {
//          outputList.add(currentChildren[i].getValue());
//        }
//        outputList = traverse(outputList, currentChildren[i]);
//      }
//    }
//
//    return outputList;
//
//  }
//
//  /**
//   * Traverse a trie given max led.
//   *
//   * @param outputList outputlist that contains all the words
//   * @param start      starting trienode.
//   * @param max        max led
//   * @param input      input string for recursive calls
//   * @return output string
//   */
//
//  public List<String> traverseMaxLED(List<String> outputList,
//                                     TrieNode start, int max, String input) {
//    TrieNode[] currentChildren = start.getChildren();
//    int lowest = input.length() - max;
//
//    for (int i = 0; i < currentChildren.length; i++) {
//      if (currentChildren[i] != null) {
//        if (currentChildren[i].getValue() != null && currentChildren[i]
//            .getLevel() >= lowest) {
//          String curString = currentChildren[i].getValue();
//          int led = LDistanceCalculator.calculate(curString, input);
//          if (led <= max) {
//            outputList.add(currentChildren[i].getValue());
//          }
//          int levelDiff = Math.abs(curString.length() - input.length());
//          if (led <= max + levelDiff) {
//            outputList = traverseMaxLED(outputList,
//                currentChildren[i], max, input);
//          }
//
//        }
//        outputList = traverseMaxLED(outputList, currentChildren[i], max, input);
//      }
//    }
//
//    return outputList;
//
//  }

//  /**
//   * This method find all words with a given prefix.
//   *
//   * @param input given prefix
//   * @return all words
//   */
//
//  public List<String> findByPrefix(String input) {
//    List<String> result = new ArrayList<>();
//
//    input = input.toLowerCase();
//    int i = 0;
//    TrieNode currentNode = root;
//    while ((currentNode.getChildren())[charToInt(input.charAt(i))] != null) {
//      currentNode
//          = (currentNode.getChildren())[charToInt(input.charAt(i))];
//
//      if (input.length() > i + 1) {
//        i += 1;
//      } else if (input.length() == i + 1) {
//        i += 1;
//        break;
//      }
//    }
//
//    if (i < input.length()) {
//      return null;
//    }
//
//    return traverse(result, currentNode);
//
//  }

  /**
   * Tells if a string is word in the trie.
   *
   * @param input input string.
   * @return a boolean indicating if the string is a word.
   */

  public boolean isWord(String input) {


    input = input.toLowerCase();

    int i = 0;
    TrieNode currentNode = root;

    while ((currentNode.getChildren())[charToInt(input.charAt(i))] != null) {
      currentNode
          = (currentNode.getChildren())[charToInt(input.charAt(i))];
      if (input.length() > i + 1) {
        i += 1;
      } else {
        return (currentNode.getValue() != null);
      }
    }

    return false;

  }

  /**
   * Tells is a string is two words.
   *
   * @param input    input string
   * @param twoWords output sring with splitted words
   * @return true is can be splitted into two words
   */

  public boolean isStringTwoWords(String input, List<String> twoWords) {
    boolean result = false;
    for (int i = 1; i < input.length(); i++) {
      if (isWord(input.substring(0, i))) {
        if (isWord(input.substring(i, input.length()))) {
          result = true;
          twoWords.add(input.substring(0, i) + " "
              + input.substring(i, input.length()));
        }
      }
    }

    return result;

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
