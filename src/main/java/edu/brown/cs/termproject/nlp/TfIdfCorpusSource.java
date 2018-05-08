package edu.brown.cs.termproject.nlp;

import java.util.Collection;

public interface TfIdfCorpusSource<U extends TfIdfDocument,
    V extends TfIdfDocument> {

  Collection<U> dataCorpus();

  Collection<V> queryCorpus();
}
