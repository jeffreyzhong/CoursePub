package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Course;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CourseDaoImpl implements CourseDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Course> getAllCourses() {
    String ql = "FROM Course";
    return entityManager.createQuery(ql).getResultList();
  }

  @Override
  public void add(Course course) {
    entityManager.persist(course);
  }

  @Override
  public Course ofId(Integer id) {
    return entityManager.find(Course.class, id);
  }
}
