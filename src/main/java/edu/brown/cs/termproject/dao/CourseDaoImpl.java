package edu.brown.cs.termproject.dao;

import edu.brown.cs.termproject.model.Course;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CourseDaoImpl implements CourseDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Course> getAllCourses() {
    String hql = "FROM course";
    return entityManager.createQuery(hql).getResultList();
  }

  @Override
  @Transactional(readOnly = false)
  public void add(Course course) {
    entityManager.persist(course);
  }
}
