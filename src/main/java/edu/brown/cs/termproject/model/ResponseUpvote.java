package edu.brown.cs.termproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "responseUpvote")
public class ResponseUpvote {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(name = "responseId", referencedColumnName = "id")
  private Response response;

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "id")
  private User user;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Response getResponse() {
    return response;
  }

  public User getUser() {
    return user;
  }

  public void setResponse(Response response) {
    response.addResponseUpvote(this);
    this.response = response;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return String.format("{responseUpvote: {id: %d, responseId: %s, userId: %d,},}", id, response.getId(), user.getId());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || id == null) {
      return false;
    }
    if (!(obj instanceof edu.brown.cs.termproject.model.ResponseUpvote)) {
      throw new UnsupportedOperationException(
          "Comparison with object of a different class is undefined.");
    }

    edu.brown.cs.termproject.model.ResponseUpvote other =
        (edu.brown.cs.termproject.model.ResponseUpvote) obj;
    return id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(edu.brown.cs.termproject.model.ResponseUpvote.class, id);
  }
}