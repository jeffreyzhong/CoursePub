package edu.brown.cs.termproject.nlp;

import java.util.Iterator;

public interface TfIdfDocument {

  Iterator<String> words();

  TfIdfCorpusSource source();
}
