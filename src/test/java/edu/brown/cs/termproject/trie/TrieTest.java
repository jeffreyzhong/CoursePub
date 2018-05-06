package edu.brown.cs.termproject.trie;


import com.google.common.collect.ImmutableList;
import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Sentence;
import edu.brown.cs.termproject.model.Video;
import edu.brown.cs.termproject.service.VideoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TrieTest {

  @Autowired
  private VideoService videoService;

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

  @Test
  public void DbTest(){
    Integer id = 2;
    if (!TrieManager.hasTrie(id)) {
      Video tempVideo = videoService.ofId(id);
      Set<Sentence> sentences = tempVideo.getSentences();
      Map<String, Double> tempMap = new HashMap<>();
      for(Sentence s:sentences){
        Long c = s.getVideoTime().getTimeInMillis();
        tempMap.put(s.getWords(), (double)c/1000);
      }
      TrieManager.insertVideoTranscript(id,tempMap);
    }
    List<String> result = TrieManager.getWordTimeList("the",
        id, 1.1, 2000.0);
    System.out.println(result);
  }
}
