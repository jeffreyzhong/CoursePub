package edu.brown.cs.termproject.autocorrect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * Trie Class: This class models the trie data structure. It contains
 * functionality for adding nodes, seeing if a node exists, and also various
 * maps and sets for keeping track of unigrams, bigrams, and trigrams.
 *
 * @author Jeffreyzhong
 */
public class Trie {

  private TrieNode root;
  private HashSet<String> corpusSet;
  private HashMap<String, Integer> unigramMap;
  private ListMultimap<String, String> bigramMap;
  private ListMultimap<String, String> trigramMap;
  private ACControl acControl;
  private SuggestionGenerator suggestionGenerator;
  private static HashMap<String, String> caseInsensitive;
  private Ranker ranker;

  /**
   * Trie Constructor: This constructor initializes the root of the tree as well
   * as a set containing all the words, and three maps for unigrams, bigrams,
   * and trigrams.
   */
  public Trie() {

    root = new TrieNode("");
    corpusSet = new HashSet<String>();
    unigramMap = new HashMap<String, Integer>();
    caseInsensitive = new HashMap<String, String>();
    bigramMap = ArrayListMultimap.create();
    trigramMap = ArrayListMultimap.create();
    suggestionGenerator = new SuggestionGenerator(root, corpusSet, this);
    ranker = new Ranker(unigramMap, bigramMap, trigramMap);
    acControl = new ACControl();

  }

  /**
   * This method adds a word to the trie by finding where each letter should be
   * inserted and keeping track of leaf nodes that signify the end of a
   * particular word.
   *
   * @param word
   *          The word to insert into the trie
   * @param prevWord
   *          The previous word in order to keep track of bigrams
   */
  public void addNodes(String word, String prevWord) {
    HashMap<String, TrieNode> children = root.getChildren();
    for (int i = 0; i < word.length(); i++) {
      Character c = word.charAt(i);
      TrieNode t;
      if (children.containsKey(c.toString())) {
        t = children.get(c.toString());
      } else {
        t = new TrieNode(c.toString());
        children.put(c.toString(), t);
      }
      children = t.getChildren();
      if (i == word.length() - 1) {

        t.setIsLeaf(true);
        corpusSet.add(word);
        bigramMap.put(prevWord, word);

        if (unigramMap.containsKey(word)) {
          unigramMap.put(word, unigramMap.get(word) + 1);

        } else {
          unigramMap.put(word, 1);
        }
      }
    }
  }

  /**
   * This method checks whether or not a given word is contained within the
   * trie.
   *
   * @param word
   *          The word to check
   * @return boolean
   */
  public boolean contains(String word) {

    TrieNode t = null;
    HashMap<String, TrieNode> children = root.getChildren();
    for (int i = 0; i < word.length(); i++) {
      Character c = word.charAt(i);
      if (children.containsKey(c.toString())) {
        t = children.get(c.toString());
        children = t.getChildren();
      } else {
        return false;
      }
    }
    if (t != null) {
      return t.isLeaf();
    }
    return false;
  }

  /**
   * This is an additional add method that can be used to add additional words
   * to the trie.
   *
   * @param word
   *          The word to add
   */
  public void add(String word) {
    HashMap<String, TrieNode> children = root.getChildren();
    String lowerWord = word.toLowerCase();
    for (int i = 0; i < word.length(); i++) {
      Character c = lowerWord.charAt(i);
      TrieNode t;
      if (children.containsKey(c.toString())) {
        t = children.get(c.toString());

      } else {
        t = new TrieNode(c.toString());
        children.put(c.toString(), t);
      }

      children = t.getChildren();
      if (i == word.length() - 1) {
        t.setIsLeaf(true);
        corpusSet.add(word);
        if (unigramMap.containsKey(word)) {
          unigramMap.put(word, unigramMap.get(word) + 1);

        } else {
          caseInsensitive.put(lowerWord, word);
          unigramMap.put(word, 1);
        }
      }
    }
  }

  /**
   * This method calls the appropriate suggestion generators based on the
   * options the user has selected.
   *
   * @param toCorrect
   *          The word to correct
   * @param wordBefore
   *          The word that came before in the user's input if it exists
   * @param twoBefore
   *          The word that came two words before in the user's input if it
   *          exists
   * @return List of the top five suggestions ranked accordingly
   */
  public List<String> autoCorrect(String toCorrect, String wordBefore,
      String twoBefore) {

    List<String> prefix = new ArrayList<String>();
    List<String> whitespace = new ArrayList<String>();
    List<String> levenshtein = new ArrayList<String>();

    if (acControl.prefixOn()) {
      prefix = suggestionGenerator.prefixMatch(toCorrect);
    }
    if (acControl.whiteSpaceOn()) {
      whitespace = suggestionGenerator.calcWhiteSpace(toCorrect);
    }
    if (acControl.levenshteinDist() > 0) {
      levenshtein = suggestionGenerator.calcLevenshtein(toCorrect,
          acControl.levenshteinDist());
    }
    List<String> finalResult = new ArrayList<String>();

    if (!acControl.prefixOn() && !acControl.whiteSpaceOn()
        && acControl.levenshteinDist() == 0 && !acControl.smartOn()) {
      finalResult.add(toCorrect);
      return finalResult;
    }

    if (wordBefore != null && !acControl.smartOn()) {
      finalResult = ranker.rankWithWordBefore(toCorrect, wordBefore, prefix,
          whitespace, levenshtein);
      return finalResult;

    } else if (wordBefore == null && !acControl.smartOn()) {
      finalResult = ranker.rankOnItsOwn(toCorrect, prefix, whitespace,
          levenshtein);
      return finalResult;

    } else if (wordBefore != null && acControl.smartOn()) {
      finalResult = ranker.smartRank(toCorrect, wordBefore, twoBefore, prefix,
          whitespace, levenshtein);
      return finalResult;
    }

    return finalResult;
  }

  /**
   * Special autocorrect just for Bacon.
   *
   * @param prefix
   *          The word to be autocorrected
   * @return the list of suggestions
   */
  public List<String> baconCorrect(String prefix) {
    return suggestionGenerator.prefixMatch(prefix);
  }
  
  public List<String> courseCorrect(String word) {
    List<String> prefix = new ArrayList<String>();
    List<String> whitespace = new ArrayList<String>();
    List<String> levenshtein = new ArrayList<String>();
    prefix = suggestionGenerator.prefixMatch(word);
    whitespace = suggestionGenerator.calcWhiteSpace(word);
    levenshtein = suggestionGenerator.calcLevenshtein(word, 4);
    prefix.addAll(whitespace);
    prefix.addAll(levenshtein);
    return prefix;
    
  }

  /**
   * This method adds words to the trigram map to be used in calculating smart
   * rankings.
   *
   * @param toAdd
   *          The list of words to add
   */
  public void addToTrigram(List<String> toAdd) {
    try {
      trigramMap.put(toAdd.get(0), null);
      trigramMap.put(toAdd.get(1), null);

      for (int i = 2; i < toAdd.size(); i++) {
        trigramMap.put(toAdd.get(i - 2), toAdd.get(i));
      }
    } catch (IndexOutOfBoundsException e) {
      return;
    }
  }

  /**
   * Get the root of the trie.
   *
   * @return TrieNode
   */
  public TrieNode getRoot() {
    return root;
  }

  /**
   * Get the set containing all the words in the corpus.
   *
   * @return HashSet
   */
  public HashSet<String> getCorpusSet() {
    return corpusSet;
  }

  /**
   * Get the map containing all the bigrams in the corpus.
   *
   * @return ListMultimap
   */
  public ListMultimap<String, String> getBigramMap() {
    return bigramMap;
  }

  /**
   * Get the map containing all the trigrams in the corpus.
   *
   * @return ListMultimap
   */
  public ListMultimap<String, String> getTrigramMap() {
    return trigramMap;
  }

  /**
   * Get the map containing all the unigrams in the corpus.
   *
   * @return ListMultimap
   */
  public HashMap<String, Integer> getUnigramMap() {
    return unigramMap;
  }

  /**
   * Get the autocorrector control object.
   *
   * @return ACControl
   */
  public ACControl getControl() {
    return acControl;
  }

  /**
   * S.
   *
   * @return s
   */
  public static HashMap<String, String> getCaseInsensitive() {
    return caseInsensitive;
  }

}
