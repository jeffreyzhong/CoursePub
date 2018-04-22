package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.model.Course;

import java.util.List;

public interface CourseService {

  List<Course> getAllCourses();

  Course add(String name);

  Course ofId(Integer id);
}
