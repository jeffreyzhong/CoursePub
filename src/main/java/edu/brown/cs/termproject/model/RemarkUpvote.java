package edu.brown.cs.termproject.model;

import edu.brown.cs.termproject.model.Remark;
import edu.brown.cs.termproject.model.User;

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
@Table(name = "remarkUpvote")
public class RemarkUpvote {

  @Id
  @Column(name = "remarkUpvoteId")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(name = "remarkId", referencedColumnName = "id")
  private Remark remark;

  @ManyToOne(fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn(name = "userId", referencedColumnName = "id")
  private User user;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Remark getRemark() {
    return remark;
  }

  public User getUser() {
    return user;
  }

  public void setRemark(Remark remark) {
    this.remark = remark;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return String.format("{course: {id: %d, questionId: %d, userId: %d,},}", id, remark, user);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || id == null) {
      return false;
    }
    if (!(obj instanceof edu.brown.cs.termproject.model.RemarkUpvote)) {
      throw new UnsupportedOperationException(
          "Comparison with object of a different class is undefined.");
    }

    edu.brown.cs.termproject.model.RemarkUpvote other =
        (edu.brown.cs.termproject.model.RemarkUpvote) obj;
    return id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(edu.brown.cs.termproject.model.RemarkUpvote.class, id);
  }
}

