package edu.brown.cs.termproject.model;

import edu.brown.cs.termproject.collect.PickySet;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "response")
public class Response {

  @Id
  @Column(name = "id")
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(
      name = "uuid",
      strategy = "uuid2"
  )
  private String id;

  @Column(name = "time")
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date time;

  @Column(name = "title")
  private String title;

  @Column(name = "body")
  private String body;

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(
      name = "userId",
      referencedColumnName = "id"
  )
  private User user;

  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL
  )
  @PrimaryKeyJoinColumn(
      name = "questionId",
      referencedColumnName = "id"
  )
  private Question question;

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "response",
      fetch = FetchType.EAGER
  )
  private Set<ResponseUpvote> responseUpvotes = new PickySet<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void addResponseUpvote(ResponseUpvote responseUpvote) {
    responseUpvotes.add(responseUpvote);
  }

  public void removeResponseUpvote(ResponseUpvote responseUpvote) {
    responseUpvotes.remove(responseUpvote);
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Set<ResponseUpvote> getResponseUpvotes() {
    return Collections.unmodifiableSet(responseUpvotes);
  }

  public void setResponseUpvotes(Set<ResponseUpvote> responseUpvotes) {
    this.responseUpvotes = responseUpvotes;
  }

  @Override
  public String toString() {
    return String.format("{response: {id: %s, title: %s,},}", id, title);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || id == null) {
      return false;
    }
    if (!(obj instanceof Response)) {
      throw new UnsupportedOperationException(
          "Comparison with object of a different class is undefined.");
    }

    Response other = (Response) obj;
    return id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(Response.class, id);
  }
}
