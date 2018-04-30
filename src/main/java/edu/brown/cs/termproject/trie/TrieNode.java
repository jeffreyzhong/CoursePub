package edu.brown.cs.termproject.trie;

/**
 * This is a Trienode class.
 *
 * @author sy69
 */

public class TrieNode {
  private int maxChildren = Trie.getReferenceString().length();
  private TrieNode[] children = new TrieNode[maxChildren];
  private char content;
  private int level;
  private String value;

  /**
   * Constructor of trienode.
   * @param c char of trienode.
   * @param l level of trinode.
   */

  public TrieNode(char c, int l) {
    content = c;
    level = l;
  }

  /**
   * Return the children of trienode.
   * @return an array of trienode.
   */

  public TrieNode[] getChildren() {
    return children;
  }

  /**
   * Set the children for trieNode.
   * @param node node to be set.
   * @param index index for node to be set.
   */

  public void setChildren(TrieNode node, int index) {
    this.children[index] = node;
  }

  /**
   * Returns the value of the trie.
   * @return value of the trie.
   */

  public String getValue() {
    return value;
  }

  /**
   * Sets the value of the trie.
   * @param v value to be set.
   */

  public void setValue(String v) {
    value = v;
  }

  /**
   * Get the level of the trie.
   * @return level of the trie.
   */

  public int getLevel() {
    return level;
  }
}
