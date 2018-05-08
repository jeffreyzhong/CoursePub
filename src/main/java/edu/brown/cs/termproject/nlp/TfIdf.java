package edu.brown.cs.termproject.nlp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class TfIdf<T extends TfIdfCorpusSource<U, V>,
    U extends TfIdfDocument,
    V extends TfIdfDocument> {

  public static final Function<String, List<String>> SANITIZER =
      s -> Arrays.asList(s.toLowerCase()
          .replaceAll("[^a-z]", " ")
          .trim()
          .split("[ ]+"));

  private Map<T, TfIdfRecord<U, V>> data;

  public TfIdf() {
    this.data = new ConcurrentHashMap<>();
  }

  public Map<V, Double> getScore(T source, String sentence)
      throws IllegalArgumentException {
    if (source == null || sentence == null) {
      throw new IllegalArgumentException("Source or sentence cannot be null.");
    }

    if (!data.containsKey(source)) {
      build(source);
    }

    Map<V, Double> ret = new HashMap<>();
    for (V document : source.queryCorpus()) {
      ret.put(document, data.get(source).getTfIdf(SANITIZER.apply(sentence), document));
    }

    return ret;
  }

  private void build(T source) {
    assert (source != null);
    assert (!data.containsKey(source));

    TfIdfRecord<U, V> record = new TfIdfRecord<>(source);
    record.build(source.dataCorpus(), source.queryCorpus());

    data.put(source, record);
  }
}
