package edu.brown.cs.termproject.service;

import edu.brown.cs.termproject.dao.CourseDao;
import edu.brown.cs.termproject.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

  private CourseDao courseDao;

  @Autowired
  public CourseServiceImpl(CourseDao courseDao) {
    this.courseDao = courseDao;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Course> getAllCourses() {
    return courseDao.getAllCourses();
  }

  @Override
  @Transactional(readOnly = false)
  public Course add(String name) {
    Course course = new Course();
    course.setName(name);

    courseDao.add(course);

    return course;
  }

  @Override
  @Transactional(readOnly = true)
  public Course ofId(Integer id) {
    return courseDao.ofId(id);
  }
}
