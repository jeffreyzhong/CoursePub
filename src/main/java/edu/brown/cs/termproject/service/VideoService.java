package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Course;
import edu.brown.cs.termproject.model.Video;

public interface VideoService {

  Video add(String utl, Course course);
}
