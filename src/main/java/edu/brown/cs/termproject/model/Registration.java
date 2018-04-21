package edu.brown.cs.termproject.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "registration")
public class Registration {

  @EmbeddedId
  private RegistrationId id;

  @MapsId("courseId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "courseId", referencedColumnName = "id")
  private Course course;

  @MapsId("userId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", referencedColumnName = "id")
  private User user;

  @Column(name = "type")
  private Integer type;

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return String.format("{registration: {id: %s, type: %s}}", id, type);
  }

  @Override
  public boolean equals(Object obj) throws UnsupportedOperationException {
    if (!(obj instanceof Registration)) {
      throw new UnsupportedOperationException(
          "Comparison with object of a different class is undefined.");
    }

    Registration other = (Registration) obj;
    return id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(Registration.class, id);
  }

  @Embeddable
  private static class RegistrationId implements Serializable {

    private Integer regId;
    private Integer userId;
    private Integer courseId;

    @Override
    public String toString() {
      return String.format("{registration_id: {regId: %d, userId: %d, courseId: %d,},}", regId, userId, courseId);
    }

    @Override
    public boolean equals(Object obj) throws UnsupportedOperationException {
      if (!(obj instanceof RegistrationId)) {
        throw new UnsupportedOperationException(
            "Comparison with object of a different class is undefined.");
      }

      RegistrationId other = (RegistrationId) obj;
      return userId.equals(other.userId) && courseId.equals(other.courseId)
          && regId.equals(other.regId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(RegistrationId.class,
          regId, userId, courseId);
    }
  }
}
