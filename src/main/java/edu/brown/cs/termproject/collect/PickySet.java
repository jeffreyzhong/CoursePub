package edu.brown.cs.termproject.collect;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PickySet<T> implements Set<T> {

  private static final String ERR_EXIST = "Element %s already exists.";
  private static final String ERR_NO_EXIST = "Element %s does not exist.";

  private Set<T> set;

  public PickySet() {
    this.set = new HashSet<>();
  }

  private void err(String msg, T element) throws IllegalStateException {
    throw new IllegalStateException(String.format(msg, element.toString()));
  }

  private void checkElementNotExist(T t) {
    if (set.contains(t)) {
      err(ERR_EXIST, t);
    }
  }

  private void checkCollectionNotExist(Collection<? extends T> c) {
    for (T t : c) {
      if (set.contains(t)) {
        err(ERR_EXIST, t);
      }
    }
  }

  private void checkElementExist(Object o) {
    T t = (T) o;
    if (!set.contains(t)) {
      err(ERR_NO_EXIST, t);
    }
  }

  private void checkCollectionExist(Collection<?> c) {
    for (Object o : c) {
      T t = (T) o;
      if (!set.contains(t)) {
        err(ERR_NO_EXIST, t);
      }
    }
  }

  @Override
  public boolean add(@NotNull T t) {
    checkElementNotExist(t);

    return set.add(t);
  }

  @Override
  public boolean addAll(@NotNull Collection<? extends T> c) {
    checkCollectionNotExist(c);

    return set.addAll(c);
  }

  @Override
  public boolean remove(@NotNull Object o) {
    checkElementExist(o);

    return set.remove(o);
  }

  @Override
  public boolean removeAll(@NotNull Collection<?> c) {
    checkCollectionExist(c);

    return set.removeAll(c);
  }

  @Override
  public boolean retainAll(@NotNull Collection<?> c)  {
    checkCollectionExist(c);

    return set.retainAll(c);
  }

  @Override
  public boolean removeIf(Predicate<? super T> filter) {
    return set.removeIf(filter);
  }

  @Override
  public void clear() {
    set.clear();
  }

  @Override
  public boolean contains(Object o) {
    return set.contains(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return set.containsAll(c);
  }

  @Override
  public int size() {
    return set.size();
  }

  @Override
  public boolean isEmpty() {
    return set.isEmpty();
  }

  @Override
  public Iterator<T> iterator() {
    return set.iterator();
  }

  @Override
  public Spliterator<T> spliterator() {
    return set.spliterator();
  }

  @Override
  public Object[] toArray() {
    return set.toArray();
  }

  @Override
  public <T1> T1[] toArray(T1[] a) {
    return set.toArray(a);
  }

  @Override
  public void forEach(Consumer<? super T> action) {
    set.forEach(action);
  }

  @Override
  public Stream<T> stream() {
    return set.stream();
  }

  @Override
  public Stream<T> parallelStream() {
    return set.parallelStream();
  }

  @Override
  public String toString() {
    return set.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (!(obj instanceof PickySet)) {
      throw new UnsupportedOperationException(
          "Comparison with object of a different class is undefined.");
    }

    PickySet other = (PickySet) obj;
    return set.equals(other.set);
  }

  @Override
  public int hashCode() {
    return Objects.hash(PickySet.class, set);
  }
}
