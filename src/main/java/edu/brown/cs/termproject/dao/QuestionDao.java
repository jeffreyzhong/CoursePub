package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Question;

import java.util.List;

public interface QuestionDao {

  void add(Question question);

  Question ofId(String id);
}
