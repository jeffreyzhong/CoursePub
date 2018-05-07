package edu.brown.cs.termproject.collect;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import java.util.function.Predicate;

/**
 * A radix tree (compact trie) class.
 *
 * @author yqin
 */
public final class Trie implements Set<String> {

  private TrieNode root;
  private int size = 0;

  /**
   * Public constructor.
   *
   * @author yqin
   */
  public Trie() {

  }

  /**
   * package-private constructor.
   *
   * @author yqin
   * @param words list of strings to initialize the trie with
   */
  Trie(List<String> words) {
    addAll(words);
  }

  /**
   * Adds a string to trie.
   *
   * @param s string to add
   * @return <code>true</code> if successful, <code>false</code> otherwise
   */
  @Override
  public boolean add(String s) {
    TrieNode prev = null, curr = root, ret;
    char[] w = CharArrays.toNullCharArray(s);
    int aligned = -1;

    while (curr != null) {
      prev = curr;

      aligned = CharArrays.align(w, curr.getKey());

      if (aligned == 0) {
        curr = curr.getSibling();
      } else if (aligned < w.length) {
        if (aligned < curr.getLen()) {
          split(curr, aligned);
        }

        curr = curr.getChild();
        w = Arrays.copyOfRange(w, aligned, w.length);
      } else {
        return false;
      }
    }

    ret = new TrieNode(w);

    if (prev == null) {
      /* adds node as root if trie has nothing */
      assert (size == 0);
      root = ret;
    } else {
      /* adds node as child or sibling of previous node */
      if (aligned == 0) {
        prev.setSibling(ret);
      } else {
        prev.setChild(ret);
      }
    }

    ++size;

    return true;
  }

  /**
   * Adds a collection of strings to trie.
   *
   * @author yqin
   * @param c collection of strings to add
   * @return <code>true</code> if tree changes, <code>false</code> otherwise
   */
  @Override
  public boolean addAll(Collection<? extends String> c) {
    boolean ret = false;

    for (String s : c) {
      ret |= add(s);
    }

    return ret;
  }

  /**
   * Deletes all strings.
   *
   * @author yqin
   */
  @Override
  public void clear() {
    root = null;
    size = 0;
  }

  /**
   * Checks if trie contains a specific string.
   *
   * @author yqin
   * @param o string
   * @return <code>true</code> if o is a string in the trie,
   * <code>false</code> otherwise
   */
  @Override
  public boolean contains(Object o) {
    return o instanceof String
        && find(CharArrays.toNullCharArray(o.toString()), null)
        != null;
  }

  /**
   * Checks if trie contains all strings in a collection.
   *
   * @author yqin
   * @param c collection of strings
   * @return <code>true</code> if all are in trie, <code>false</code> otherwise
   */
  @Override
  public boolean containsAll(Collection<?> c) {
    boolean ret = true;

    for (Object s : c) {
      ret &= contains(s);
    }

    return ret;
  }

  /**
   * Checks if trie is empty.
   *
   * @author yqin
   * @return <code>true</code> if empty, <code>false</code> otherwise
   */
  @Override
  public boolean isEmpty() {
    return root != null;
  }

  /**
   * Returns an iterator of all strings.
   *
   * @return iterator of strings
   */
  @Override
  public Iterator<String> iterator() {
    return getIterator(root, new StringBuilder(), node -> true);
  }

  /**
   * Removes a string, hypothetically. This method is not supported.
   *
   * @author yqin
   * @param o something
   * @return nothing
   * @throws UnsupportedOperationException always
   */
  @Override
  public boolean remove(Object o)
      throws UnsupportedOperationException {
    throw new UnsupportedOperationException(
        "remove(Object o) is not supported.");
  }

  /**
   * Removes some strings, hypothetically. This method is not supported.
   *
   * @author yqin
   * @param c something
   * @return nothing
   * @throws UnsupportedOperationException always
   */
  @Override
  public boolean removeAll(Collection<?> c)
      throws UnsupportedOperationException {
    throw new UnsupportedOperationException(
        "removeAll(Collection<?> c) is not supported.");
  }

  /**
   * Retains some strings, hypothetically. This method is not supported.
   *
   * @author yqin
   * @param c something
   * @return nothing
   * @throws UnsupportedOperationException always
   */
  @Override
  public boolean retainAll(Collection<?> c)
      throws UnsupportedOperationException {
    throw new UnsupportedOperationException(
        "retainAll(Collection<?> c) is not supported.");
  }

  /**
   * Returns number of strings.
   *
   * @author yqin
   * @return number of strings
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Returns an array containing all strings, hypothetically.
   * This method is not supported.
   *
   * @author yqin
   * @return nothing
   * @throws UnsupportedOperationException always
   */
  @Override
  public Object[] toArray() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("toArray() is not supported.");
  }

  /**
   * Returns an array containing all strings as T, hypothetically.
   * This method is not supported.
   *
   * @author yqin
   * @param a something
   * @return nothing
   * @throws UnsupportedOperationException always
   */
  @Override
  public <T> T[] toArray(T[] a) {
    throw new UnsupportedOperationException("toArray(T[] a) is not supported.");
  }

  /**
   * Given a string, returns an iterator of all strings with that prefix.
   *
   * @author yqin
   * @param prefix prefix string
   * @return iterator of strings with that prefix
   */
  public Iterator<String> iteratorOfPrefix(String prefix) {
    StringBuilder sb = new StringBuilder();
    TrieNode top = find(prefix.toCharArray(), sb);
    int prefixLen = sb.length();
    return getIterator(top, sb, node -> sb.length() > prefixLen || node == top);
  }

  /**
   * Given a string, returns an iterator of all strings within some edit
   * distance from it.
   *
   * @author yqin
   * @param template template string
   * @param dist maximum edit distance
   * @return an iterator of strings within specified edit distance
   */
  public Iterator<String> iteratorOfDistance(String template, int dist) {
    StringBuilder sb = new StringBuilder();
    char[] t = CharArrays.toNullCharArray(template);
    int minLen = t.length - dist, maxLen = t.length + dist;

    Predicate<TrieNode> p = node -> {
      int len = node.getLen() + sb.length();
      if (node.isEnd()) {
        /* adds an end node if and only if it forms a qualified string with the
         * prefix in string builder */
        return len >= minLen && len <= maxLen
            && CharArrays.levenshtein(
            CharArrays.concat(sb.toString(), node.getKey()),
            t) <= dist;
      } else {
        /* adds an inner node if:
         * 1. it's shorter than minimum qualified length, or,
         * 2. its length is between minimum and maximum, and its children could
         * form a qualified string */
        return len < minLen
            || (len <= maxLen
            && CharArrays.levenshtein(
            CharArrays.concat(sb.toString(), node.getKey()),
            t) <= Math.abs(t.length - len) + dist + 1);
      }
    };

    return getIterator(root, sb, p);
  }

  /**
   * Given a word, returns an iterator of its prefixes that are in the trie,
   * the removal of which transforms the word into another word in the trie.
   *
   * @author yqin
   * @param word entire word
   * @return an iterator of prefix strings
   */
  public Iterator<String> iteratorOfWhitespace(String word) {
    StringBuilder sb = new StringBuilder();
    char[] w = CharArrays.toNullCharArray(word);
    /* boxes the stop toggle (wish there were pointers in Java) */
    boolean[] stop = new boolean[] {false};

    Predicate<TrieNode> p = node -> {
      if (stop[0]) {
        return false;
      }

      int len = node.getLen(), pre = sb.length();
      if (len + pre >= w.length) {
        return false;
      }

      int k = CharArrays.align(w, pre, node.getKey());
      if (node.isEnd()) {
        return (k == len - 1
            && find(Arrays.copyOfRange(w, pre + len - 1, w.length),
            null) != null);

      } else {
        if (k == len) {
          return true;
        } else {
          if (k != 0) {
            stop[0] = true;
          }
          return false;
        }
      }
    };

    return getIterator(root, sb, p);
  }

  /**
   * Splits a node into a parent and a child.
   *
   * @author yqin
   * @param node node to split
   * @param k number of characters in parent key
   */
  private void split(TrieNode node, int k) {
    /* splits old key into substrings of length k and (len - k) */
    TrieNode newChild = new TrieNode(node.getKeyLast(node.getLen() - k));
    node.setKey(node.getKeyFirst(k));

    /* puts new child between old node and old child */
    newChild.setChild(node.getChild());
    node.setChild(newChild);
  }

  /**
   * Searches for a word in the trie, and records the path it takes to reach
   * the end node, provided sb is not null.
   *
   * @author yqin
   * @param w word to find
   * @param sb string builder to keep track of path
   * @return end node if found, null otherwise
   */
  private TrieNode find(char[] w, StringBuilder sb) {
    int from = 0;
    TrieNode node = root;

    while (node != null) {
      int k = CharArrays.align(w, from, node.getKey());

      if (k == w.length - from) {
        /* returns node if node key matches exactly (including null char) */
        break;
      } else if (k == 0) {
        /* searches sibling if node key matches nothing */
        node = node.getSibling();
      } else if (k == node.getLen()) {
        /* appends key to already-matched string */
        if (sb != null) {
          sb.append(node.getKey());
        }

        /* searches child if entire node key is a substring */
        node = node.getChild();
        from += k;
      } else {
        /* returns null if part of node key is a substring... */
        node = null;
      }
    }

    return node;
  }

  private Iterator<String> getIterator(TrieNode top, StringBuilder sb,
                                       Predicate<TrieNode> predicate) {
    /* saves time of instantiating a new iterator, if we know it'll be empty */
    if (top == null) {
      return Collections.emptyIterator();
    }

    /* returns an anonymous iterator starting from some node */
    return new Iterator<String>() {

      private Stack<TrieNode> stack;
      private int k = 0;

      /* A mock constructor. */
      {
        stack = new Stack<>();
        grow(top);
      }

      @Override
      public boolean hasNext() {
        return !stack.isEmpty();
      }

      @Override
      public String next() {
        TrieNode node;
        /* saves return string excluding the null char */
        String ret = sb.substring(0, sb.length() - 1);

        /* prunes current branch and moves to sibling */
        grow(prune().getSibling());

        return ret;
      }

      /**
       * Adds all children starting from root, until it encounters a node that
       * has '\0', or does not satisfy predicate, in which case a different
       * branch is explored.
       *
       * @author yqin
       * @param node root node
       */
      private void grow(TrieNode node) {
        while (true) {
          while (node != null && predicate.test(node)) {
            sb.append(node.getKey());
            stack.push(node);
            node = node.getChild();
          }

          if (node != null) {
            /* adds sibling, because this node must have failed predicate */
            node = node.getSibling();
          } else if (!stack.isEmpty() && !stack.peek().isEnd()) {
            /* completes string in sb by pruning and switching branch */
            assert (sb.charAt(sb.length() - 1) != '\0');
            node = prune().getSibling();
          } else {
            return;
          }
        }
      }

      /**
       * Removes a branch from the stack, either because it has been explored,
       * or because it's not viable, i.e., some node on the branch does not
       * satisfy predicate.
       *
       * @author yqin
       * @return last node popped from stack
       */
      private TrieNode prune() {
        TrieNode node;

        /* keeps popping nodes with no sibling, having no other branch */
        do {
          int len = sb.length();
          node = stack.pop();
          sb.delete(len - node.getLen(), len);
        } while (node.getSibling() == null && !stack.isEmpty());

        return node;
      }
    };
  }

  /**
   * Node class for radix tree.
   *
   * @author yqin
   */
  private static final class TrieNode {

    private char[] key;
    private TrieNode sibling, child;

    private TrieNode(char[] key) {
      this.key = key;
    }

    private TrieNode getSibling() {
      return sibling;
    }

    private TrieNode getChild() {
      return child;
    }

    private void setSibling(TrieNode sibling) {
      this.sibling = sibling;
    }

    private void setChild(TrieNode child) {
      this.child = child;
    }

    private int getLen() {
      return key.length;
    }

    private void setKey(char[] key) {
      this.key = key;
    }

    private char[] getKeyLast(int n) {
      return Arrays.copyOfRange(key, key.length - n, key.length);
    }

    private char[] getKeyFirst(int n) {
      return Arrays.copyOf(key, n);
    }

    private char[] getKey() {
      return key;
    }

    private boolean isEnd() {
      return child == null;
    }
  }
}
