package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.CourseDao;
import edu.brown.cs.termproject.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

  private CourseDao courseDao;

  @Autowired
  public CourseServiceImpl(CourseDao courseDao) {
    this.courseDao = courseDao;
  }

  @Override
  public List<Course> getAllCourses() {
    return courseDao.getAllCourses();
  }

  @Override
  public void add(Course course) {
    courseDao.add(course);
  }
}
