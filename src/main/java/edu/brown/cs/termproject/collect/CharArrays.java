package edu.brown.cs.termproject.collect;

import com.google.common.primitives.Chars;

/**
 * This class consists exclusively of static methods that operate on char
 * arrays.
 *
 * @author yqin
 */
final class CharArrays {

  private static final String CHAR_FORMAT = "%s\0";

  private CharArrays() throws IllegalAccessError {
    throw new IllegalAccessError("Something is seriously wrong.");
  }

  static int align(char[] string, int from, char[] template)
      throws IllegalArgumentException {
    if (string == null || template == null) {
      throw new IllegalArgumentException("Strings cannot be null.");
    }

    int len = string.length - from;

    if (len < 0) {
      throw new IllegalArgumentException("Starting index is invalid.");
    }

    for (int i = 0; i < len; ++i) {
      if (i == template.length || string[from + i] != template[i]) {
        return i;
      }
    }

    return len;
  }

  static int align(char[] string, char[] template) {
    return align(string, 0, template);
  }

  static int levenshtein(char[] s1, char[] s2) throws IllegalArgumentException {
    if (s1 == null || s2 == null) {
      throw new IllegalArgumentException("Strings cannot be null.");
    }

    /* p points to the start of current array cache */
    int len1 = s1.length, len2 = s2.length, p = len1;

    /* saves memory with this long cache */
    int[] arr = new int[len1 + len2 + 1];

    /* initialize edit distance between s2 and empty string as the first row */
    for (int i = 0; i <= len2; ++i) {
      arr[p + i] = i;
    }
    --p;

    for (char c1 : s1) {
      arr[p] = arr[p + 1] + 1;
      for (int j = 0; j < len2; ++j) {
        char c2 = s2[j];
        /* updates entry only if c1 != c2 */
        if (c1 != c2) {
          arr[p + j + 1] = Math.min(arr[p + j],
              Math.min(arr[p + j + 1], arr[p + j + 2])) + 1;
        }
      }
      /* moves head ptr by one index to the left */
      --p;
    }

    return arr[len2];
  }

  static char[] toNullCharArray(String s) throws IllegalArgumentException {
    if (s == null) {
      throw new IllegalArgumentException("Input cannot be null.");
    }
    return String.format(CHAR_FORMAT, s).toCharArray();
  }

  static char[] concat(String s, char[] postfix)
      throws IllegalArgumentException {
    if (s == null || postfix == null) {
      throw new IllegalArgumentException("Input cannot be null.");
    }
    return Chars.concat(s.toCharArray(), postfix);
  }
}
