package edu.brown.cs.termproject.model;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.termproject.collect.PickySet;
import edu.brown.cs.termproject.pageRank.PageRankNode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements PageRankNode<PageRankNode> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  private Set<Registration> registrations = new PickySet<>();

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "user",
      fetch = FetchType.EAGER
  )
  private Set<Response> responses = new PickySet<>();

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
    return Collections.unmodifiableSet(registrations);
  }

  public void setRegistrations(Set<Registration> registrations) {
    this.registrations = registrations;
  }

  public void register(Registration registration) {
    registrations.add(registration);
  }

  public void unregister(Registration registration) {
    registrations.remove(registration);
  }

  public void addResponse(Response response) {
    responses.add(response);
  }

  public void removeResponse(Response response) {
    responses.remove(response);
  }

  @Override
  public Map<PageRankNode, Double> getDsts() {
    if (registrations.isEmpty() && responses.isEmpty()) {
      return Collections.emptyMap();
    }

    Set<PageRankNode> set = new HashSet<>();
    for (Registration registration : registrations) {
      set.add(registration.getCourse());
    }
    for (Response response : responses) {
      set.add(response.getQuestion().getUser());
    }

    double weight = 1 / (double) set.size();
    Map<PageRankNode, Double> ret = new HashMap<>();
    for (PageRankNode node : set) {
      ret.put(node, weight);
    }

    return ret;
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
