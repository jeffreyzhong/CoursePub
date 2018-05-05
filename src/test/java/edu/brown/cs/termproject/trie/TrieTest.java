package edu.brown.cs.termproject.trie;


import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static junit.framework.TestCase.assertTrue;

public class TrieTest {

  @Test
  public void SimpleTest(){
    Map<String, Double> testMao = new HashMap<>();
    testMao.put("This is my first sentence", 0.1);
    testMao.put("The second thing I say is this", 3.4);
    testMao.put("Please try testing me", 5.3);
    testMao.put("Thanks for testing me", 8.4);

    TrieManager.insertVideoTranscript(1,testMao);
    List<String> result = TrieManager.getWordTimeList("testing",
        1, 0.5, 10.0);
    assertTrue(result.size()==4);
  }
}
