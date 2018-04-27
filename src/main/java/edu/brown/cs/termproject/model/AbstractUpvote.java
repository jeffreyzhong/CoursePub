package edu.brown.cs.termproject.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractUpvote<T extends AbstractPost> {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(name = "postId", referencedColumnName = "id")
  private T post;

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "id")
  private User user;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public T getPost() {
    return post;
  }

  public void setPost(T post) {
    this.post = post;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  protected abstract String getTableName();

  @Override
  public String toString() {
    return String.format("{%s: {id: %s, userId: %s, postId: %s,},}",
        getTableName(), id, user.getId(), post.getId());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || post == null || user == null) {
      return false;
    }
    if (!(obj instanceof AbstractUpvote)) {
      throw new UnsupportedOperationException(
          "Comparison with object of a different class is undefined.");
    }

    AbstractUpvote other = (AbstractUpvote) obj;
    return post.equals(other.post) && user.equals(other.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getClass(), post, user);
  }
}



