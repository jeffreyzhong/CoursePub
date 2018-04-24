package edu.brown.cs.termproject.model;

import edu.brown.cs.termproject.collect.PickySet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "name")
  private String name;

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "course",
      fetch = FetchType.EAGER
  )
  private Set<Registration> registrations = new PickySet<>();

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "course",
      fetch = FetchType.EAGER
  )
  private List<Video> videos = new ArrayList<>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void register(Registration registration) {
    registrations.add(registration);
  }

  public void unregister(Registration registration) {
    registrations.remove(registration);
  }

  @Override
  public String toString() {
    return String.format("{course: {id: %d, name: %s,},}", id, name);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || id == null) {
      return false;
    }
    if (!(obj instanceof Course)) {
      throw new UnsupportedOperationException(
          "Comparison with object of a different class is undefined.");
    }

    Course other = (Course) obj;
    return id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(Course.class, id);
  }
}
