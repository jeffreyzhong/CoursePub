package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Course;

import java.util.List;

public interface CourseDao {

  List<Course> getAllCourses();

  void add(Course course);
}
