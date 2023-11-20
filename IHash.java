public interface IHash<E> {
    int hashCode(E a);

    boolean equal(E a, E b);
}
