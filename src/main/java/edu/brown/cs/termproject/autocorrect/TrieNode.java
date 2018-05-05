package edu.brown.cs.termproject.autocorrect;

import java.util.HashMap;

/**
 * TrieNode Class: This class contains all of the information for any node in
 * the trie. It keeps track of all of its children with a HashMap. It stores a
 * single letter as its value.
 */
public class TrieNode {

  private HashMap<String, TrieNode> children = new HashMap<String, TrieNode>();
  private boolean isLeaf = false;
  private String c;

  /**
   * TrieNode Constructor: This constructor initializes the trienode's value to
   * the passed in letter.
   *
   * @param character
   *          The letter value of the current node
   */
  public TrieNode(String character) {
    c = character;
  }

  /**
   * Get the letter of the node.
   *
   * @return String
   */
  public String getValue() {
    return c;
  }

  /**
   * Returns boolean stating whether the current node is a leaf (and thus a
   * word).
   *
   * @return boolean
   */
  public boolean isLeaf() {
    return isLeaf;
  }

  /**
   * Set the boolean to keep track of whether or not the current node is a leaf.
   *
   * @param bool
   *          denoting if node is a leaf or not
   */
  public void setIsLeaf(boolean bool) {
    isLeaf = bool;
  }

  /**
   * Get the children of the current node.
   *
   * @return HashMap
   */
  public HashMap<String, TrieNode> getChildren() {
    return children;
  }

}
