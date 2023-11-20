public interface IComparator<E> {
  int compare(E a, E b);

  boolean equal(E a,E b);
}