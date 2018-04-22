package edu.brown.cs.termproject.model;

import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Integer id;

  @NaturalId
  @Column(name = "email")
  private String email;

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "user",
      fetch = FetchType.EAGER
  )
  private Set<Registration> registrations = new HashSet<>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<Registration> getRegistrations() {
    return registrations;
  }

  public void setRegistrations(Set<Registration> registrations) {
    this.registrations = registrations;
  }

  public void register(Registration registration) {
    if (!Hibernate.isInitialized(registrations)) {
      Hibernate.initialize(registrations);
    }

    if (!registrations.contains(registration)) {
      registrations.add(registration);
    }
  }

  public void unregister(Registration registration) {
    if (!Hibernate.isInitialized(registrations)) {
      Hibernate.initialize(registrations);
    }

    if (registrations.contains(registration)) {
      registrations.remove(registration);
    }
  }

  @Override
  public String toString() {
    return String.format("{user: {id: %d, email: %s,},}", id, email);
  }

  @Override
  public boolean equals(Object obj) throws UnsupportedOperationException {
    if (obj == null || id == null) {
      return false;
    }
    if (!(obj instanceof User)) {
      throw new UnsupportedOperationException(
          "Comparison with object of a different class is undefined.");
    }

    User other = (User) obj;
    return id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(User.class, id);
  }
}
