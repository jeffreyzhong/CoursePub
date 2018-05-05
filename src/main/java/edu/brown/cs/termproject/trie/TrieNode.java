package edu.brown.cs.termproject.trie;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * This is a Trienode class.
 *
 * @author sy69
 */

public class TrieNode {
  private int maxChildren = Trie.getReferenceString().length();
  private TrieNode[] children = new TrieNode[maxChildren];
  private int level;
  private char letter;
  private Set<Double> timeList = new TreeSet<>();

  /**
   * Constructor of trienode.
   * @param l level of trinode.
   */

  public TrieNode(char content, int l) {
    level = l;
    letter = content;
  }

  /**
   * Return the children of trienode.
   * @return an array of trienode.
   */

  public TrieNode[] getChildren() {
    return children;
  }


  /**
   * Returns the value of the trie.
   * @return value of the trie.
   */

  public Set<Double> getTimeList(){
    return timeList;
  }

  /**
   * Sets the value of the trie.
   * @param time value to be set.
   */

  public void addValue(Double time) {
    timeList.add(time);

  }

  /**
   * Get the level of the trie.
   * @return level of the trie.
   */

  public int getLevel() {
    return level;
  }


}
