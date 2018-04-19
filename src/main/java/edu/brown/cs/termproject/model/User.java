package edu.brown.cs.termproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "User")
public class User {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "EMAIL")
  private String email;

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

  @Override
  public String toString() {
    return String.format("user %d with email %s", id, email);
  }

  @Override
  public boolean equals(Object obj) throws UnsupportedOperationException {
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
