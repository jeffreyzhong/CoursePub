package edu.brown.cs.termproject.collect;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class PickySetTest {

  @Test
  public void testAdd() {
    Set<String> set = new PickySet<>();
    set.add("jj");
    assertEquals(1, set.size());
    set.add("jjjj");
    assertEquals(2, set.size());
  }

  @Test
  public void testRemove() {
    Set<String> set = new PickySet<>();
    set.add("jj");
    set.add("jjjj");
    assertEquals(2, set.size());
    set.remove("jj");
    set.remove("jjjj");
    assertTrue(set.isEmpty());
  }

  @Test(expected = IllegalStateException.class)
  public void testFalseAdd() {
    Set<Integer> set = new PickySet<>();
    set.addAll(ImmutableList.of(1, 2));
    set.add(1);
  }

  @Test(expected = IllegalStateException.class)
  public void testFalseRemove() {
    Set<String> set = new PickySet<>();
    set.add("exist");
    set.remove("not exist");
  }
}
