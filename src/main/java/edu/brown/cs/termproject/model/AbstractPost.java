package edu.brown.cs.termproject.model;

import edu.brown.cs.termproject.collect.PickySet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@MappedSuperclass
public abstract class AbstractPost<T extends AbstractUpvote> {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "postTime")
  @Temporal(value = TemporalType.TIMESTAMP)
  private Calendar postTime;

  @Column(name = "body")
  private String body;

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(
      name = "userId",
      referencedColumnName = "id"
  )
  private User user;

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "post",
      fetch = FetchType.EAGER
  )
  private Set<T> upvotes = new PickySet<>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Calendar getPostTime() {
    return postTime;
  }

  public void setPostTime(Calendar postTime) {
    this.postTime = postTime;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Set<T> getUpvotes() {
    return Collections.unmodifiableSet(upvotes);
  }

  public void setUpvotes(Set<T> upvotes) {
    this.upvotes = upvotes;
  }

  public void addUpvote(T upvote) {
    upvotes.add(upvote);
  }

  public void removeUpvote(T upvote) {
    upvotes.remove(upvote);
  }

  protected abstract String getTableName();

  @Override
  public String toString() {
    return String.format("{%s: {id: %d, postTime: %s,},}",
        getTableName(), id, postTime);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || id == null) {
      return false;
    }
    if (!(obj instanceof AbstractPost)) {
      throw new UnsupportedOperationException(
          "Comparison with object of a different class is undefined.");
    }

    AbstractPost other = (AbstractPost) obj;
    return id.equals(other.id) && getTableName().equals(other.getTableName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getClass(), getId());
  }
}
