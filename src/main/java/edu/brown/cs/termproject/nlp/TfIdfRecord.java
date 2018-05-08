package edu.brown.cs.termproject.nlp;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class TfIdfRecord<U extends TfIdfDocument, V extends TfIdfDocument> {

  private Map<String, Double> idf;
  private Map<V, Map<String, Double>> tf;

  TfIdfRecord() {
    this.idf = new HashMap<>();
    this.tf = new HashMap<>();
  }

  private double getIdf(String word) {
    return idf.getOrDefault(word, 0D);
  }

  private double getTf(String word, V document) {
    assert (tf.containsKey(document));
    return tf.get(document).getOrDefault(word, 0D);
  }

  double getTfIdf(List<String> query, V document) {
    if (!tf.containsKey(document)) {
      return 0;
    }
    Map<String , Double> freq = tf.get(document);

    double ret = 0D;
    for (String s : query) {
      ret += freq.getOrDefault(s, 0D) * idf.getOrDefault(s, 0D);
    }

    return ret;
  }

  void build(Collection<U> dataCorpus, Collection<V> queryCorpus) {
    double numDocuments = dataCorpus.size() + queryCorpus.size();
    Map<String, Integer> count = new HashMap<>();

    /* uses data corpus only for inverse document frequency */
    for (U document : dataCorpus) {
      Set<String> seen = new HashSet<>();
      Iterator<String> it = document.words();

      while (it.hasNext()) {
        seen.add(it.next());
      }

      for (String word : seen) {
        count.put(word, count.getOrDefault(word, 0) + 1);
      }
    }

    /* uses query corpus for both tf and idf */
    for (V document : queryCorpus) {
      Map<String, Integer> seen = new HashMap<>();
      Iterator<String> it = document.words();
      double size = 0;

      while (it.hasNext()) {
        ++size;
        String word = it.next();
        seen.put(word, seen.getOrDefault(word, 0) + 1);
      }

      if (size == 0) {
        continue;
      }

      Map<String, Double> freq = new HashMap<>(seen.size());
      for (Map.Entry<String, Integer> entry : seen.entrySet()) {
        String word = entry.getKey();
        freq.put(word, entry.getValue() / size);

        count.put(word, count.getOrDefault(word, 0) + 1);
      }
      tf.put(document, freq);
    }

    for (Map.Entry<String, Integer> entry : count.entrySet()) {
      idf.put(entry.getKey(), Math.log(numDocuments / (double) entry.getValue()));
    }
  }
}
