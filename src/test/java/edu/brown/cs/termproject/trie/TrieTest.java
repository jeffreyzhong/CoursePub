package edu.brown.cs.termproject.trie;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

public class TrieTest {

  @Test
  public void basicConstruction() {
    List<String> myString = new ArrayList();
    myString.add("Hello");
    Trie myTrie = new Trie();
    myTrie.insertList(myString, 99.0);
    List<String> testTrieNode = new ArrayList<>();
    List<String> result = myTrie.traverse(testTrieNode, myTrie.getRoot());
    assertTrue(result.get(0).equals("hello"));
  }

  @Test
  public void twoWordConstruction() {
    List myString = new ArrayList();
    myString.add("Hello");
    myString.add("Expensive");
    Trie myTrie = new Trie();
    myTrie.insertList(myString, 10.0);
    List<String> testTrieNode = new ArrayList<>();
    List<String> result = myTrie.traverse(testTrieNode, myTrie.getRoot());
    assertTrue(result.get(0).equals("expensive"));
    assertTrue(result.get(1).equals("hello"));
    assertTrue(result.size() == 2);
  }

  @Test
  public void twoSameWordsTest() {
    List myString = new ArrayList();
    myString.add("Hello");
    myString.add("hello");
    Trie myTrie = new Trie();
    myTrie.insertList(myString, 6.0);
    List<String> testTrieNode = new ArrayList<>();
    List<String> result = myTrie.traverse(testTrieNode, myTrie.getRoot());
    assertTrue(result.get(0).equals("hello"));
    assertTrue(result.size() == 1);

  }

  @Test
  public void maxLED() {
    List myString = new ArrayList();
    myString.add("Expensive");
    myString.add("Expense");
    myString.add("Hello");
    myString.add("xpens");
    Trie myTrie = new Trie();
    myTrie.insertList(myString, 3.0);
    List<String> testTrieNode = new ArrayList<>();
    List<String> result = myTrie.traverseMaxLED(testTrieNode, myTrie.getRoot(), 3, "expens");
    assertTrue(result.get(0).equals("expense"));
    assertTrue(result.get(1).equals("expensive"));
    assertTrue(result.get(2).equals("xpens"));
    assertTrue(result.size() == 3);
  }


  @Test
  public void findByPrefix() {
    List<String> myString = new ArrayList();
    myString.add("Hello");
    myString.add("Expensive");
    myString.add("Expense");
    myString.add("xpens");
    myString.add("halla");
    myString.add("huhaa");
    Trie myTrie = new Trie();
    myTrie.insertList(myString, 2.0);
    List<String> result = myTrie.findByPrefix("exp");
    assertTrue(result.get(0).equals("expense"));
    assertTrue(result.get(1).equals("expensive"));
    assertTrue(result.size() == 2);
  }

  @Test
  public void topRec() {
    List<String> myString = new ArrayList();
    myString.add("Hello");
    myString.add("Expensive");
    myString.add("Expense");
    myString.add("xpens");
    myString.add("halla");
    myString.add("huhaa");
    Trie myTrie = new Trie();
    myTrie.insertList(myString, 2.0);
    Set<String> result = myTrie.getTopRec(4,"expens");
    System.out.println(result);
  }
}