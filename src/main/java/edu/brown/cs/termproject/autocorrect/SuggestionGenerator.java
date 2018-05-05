package edu.brown.cs.termproject.autocorrect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * SuggestionGenerator Class: This class contains the three algorithms for
 * generating suggestions. Prefix, whitespace, and Levenshtein Edit Distance are
 * all calculated here.
 *
 * @author Jeffreyzhong
 */
public class SuggestionGenerator {

  private TrieNode root;
  private HashSet<String> corpusSet;
  private Trie trie;

  /**
   * SuggestionGenerator Constructor: This constructor takes in the root and a
   * set of all the words in the current corpus.
   *
   * @param r
   *          The root TrieNode
   * @param cSet
   *          A HashSet of all the current words
   * @param t
   *          Trie data structure so that its contains() method can be accessed
   */
  public SuggestionGenerator(TrieNode r, HashSet<String> cSet, Trie t) {
    root = r;
    corpusSet = cSet;
    trie = t;
  }

  /**
   * This method takes in a prefix and returns a list of all the words in the
   * current corpus that start with this prefix.
   *
   * @param prefix
   *          The prefix that words must start with
   * @return List of all the words that start with the given prefix
   */
  public List<String> prefixMatch(String prefix) {
    TrieNode t = null;
    List<String> matches = new ArrayList<String>();
    t = findPrefix(prefix);
    if (t != null) {
      if (corpusSet.contains(prefix)) {
        matches.add(prefix);
      }
      matches = addMatches(t, prefix, matches);
    }
    return matches;
  }

  /**
   * This method searches the trie to find the initial position of the prefix in
   * the trie before recursing and finding all words that start with it.
   *
   * @param prefix
   *          The prefix to search for
   * @return The TrieNode associated with the prefix
   */
  public TrieNode findPrefix(String prefix) {

    TrieNode t = null;
    HashMap<String, TrieNode> children = root.getChildren();
    for (int i = 0; i < prefix.length(); i++) {
      Character c = prefix.charAt(i);
      if (children.containsKey(c.toString())) {
        t = children.get(c.toString());

        children = t.getChildren();
      } else {
        return null;
      }
    }
    return t;
  }

  /**
   * This method recursively searches for all words in the trie that start with
   * a given prefix.
   *
   * @param t
   *          The TrieNode to start at
   * @param prefix
   *          THe prefix that words must start with
   * @param matches
   *          A list to add all valid words to
   * @return A list of all words that start with the given prefix
   */
  public List<String> addMatches(TrieNode t, String prefix,
      List<String> matches) {
    if (t.getChildren().isEmpty()) {
      return matches;
    } else {
      Iterator<TrieNode> itr = null;
      itr = t.getChildren().values().iterator();
      while (itr.hasNext()) {
        TrieNode next = itr.next();
        if (next.isLeaf()) {
          matches.add(prefix + next.getValue());
        }
        addMatches(next, prefix + next.getValue(), matches);
      }
    }
    return matches;
  }

  /**
   * This method takes in a word and finds all possible ways to split the word
   * into two valid words and returns all instances.
   *
   * @param word
   *          The word to split
   * @return List of all valid split words
   */
  public List<String> calcWhiteSpace(String word) {
    String part1 = null;
    String part2 = null;
    List<String> whiteSpaceList = new ArrayList<String>();

    for (int i = 1; i < word.length(); i++) {
      part1 = word.substring(0, i);
      part2 = word.substring(i, word.length());

      if (trie.contains(part1) && trie.contains(part2)) {
        whiteSpaceList.add(part1 + " " + part2);
      }
    }
    return whiteSpaceList;
  }

  /**
   * This is a helper method to find the minimum between three integers. This is
   * used in the LED algorithm below.
   *
   * @param a
   * @param b
   * @param c
   * @return smallest value
   */
  private int minimum(int a, int b, int c) {
    int mi = a;
    if (b < mi) {
      mi = b;
    }
    if (c < mi) {
      mi = c;
    }
    return mi;
  }

  /**
   * This method takes in a word and the maximum edit distance and returns all
   * possible words that can be created in the number of edits specified from
   * the original word. It calls the recursive levenshtein algorithm.
   *
   * @param word
   *          The word to find all valid edits from
   * @param maxDist
   *          The maximum edit distance allowed
   * @return List of all possible words within the edit distance
   */
  public List<String> calcLevenshtein(String word, int maxDist) {

    List<String> result = new ArrayList<String>();
    Iterator<TrieNode> itr = root.getChildren().values().iterator();

    while (itr.hasNext()) {
      TrieNode currN = itr.next();
      calcLevenshteinRecurs(currN, currN.getValue(), word, maxDist, result);
    }
    return result;
  }

  /**
   * This is the recursive LED algorithm. It recurses down the tree to search
   * for all possible words within a certain number of edits. It will skip
   * certain branches if it knows it can't do any better.
   *
   * @param currN
   *          The current node that the recursion is on
   * @param currPhrase
   *          The current phrase that the algorithm is searching on, increasing
   *          by one letter each time it recurses
   * @param word
   *          The original word
   * @param maxDist
   *          The maximum edit distance
   * @param result
   *          The list to add all valid words to
   */
  public void calcLevenshteinRecurs(TrieNode currN, String currPhrase,
      String word, int maxDist, List<String> result) {

    int n = currPhrase.length();
    int m = word.length();
    int x = n + 1;
    int y = m + 1;
    int[][] distMatrix = new int[x][y];
    int cost = 0;

    // build 2D distance matrix
    for (int i = 0; i <= n; i++) {
      distMatrix[i][0] = i;
    }

    for (int j = 0; j <= m; j++) {
      distMatrix[0][j] = j;
    }
    Character c1 = null;
    Character c2 = null;

    for (int i = 1; i <= n; i++) {
      c1 = currPhrase.charAt(i - 1);

      for (int j = 1; j <= m; j++) {
        c2 = word.charAt(j - 1);

        if (c1 == c2) {
          cost = 0;
        } else {
          cost = 1;
        }
        distMatrix[i][j] = minimum(distMatrix[i - 1][j] + 1,
            distMatrix[i][j - 1] + 1, distMatrix[i - 1][j - 1] + cost);
      }
    }
    // add current word if its edit distance < maxDist
    if (distMatrix[n][m] <= maxDist && currN.isLeaf()) {
      result.add(currPhrase);
    }
    // recurse on subtree if the subtree may contain words that have an edit
    // distance < maxDist
    if ((distMatrix[n][m] <= maxDist) || currPhrase.length() <= word.length()
        || Math.abs(currPhrase.length() - word.length()) <= maxDist) {

      Iterator<TrieNode> itr = currN.getChildren().values().iterator();

      while (itr.hasNext()) {
        TrieNode node = itr.next();
        calcLevenshteinRecurs(node, currPhrase + node.getValue(), word, maxDist,
            result);
      }
    }
  }

}
