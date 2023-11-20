public class Hasher {
    public static class KVHasher<K extends Comparable<K>, V> implements IHash<KeyValuePair<K, V>> {
        IHash<K> internalHasher;

        public KVHasher() {
            internalHasher = new Hasher.defaultHashing<K>();
        }

        public KVHasher(IHash<K> hasher) {
            this.internalHasher = hasher;
        }

        public int hashCode(KeyValuePair<K, V> a) {
            return Math.abs(internalHasher.hashCode(a.getKey()));
        }

        public boolean equal(KeyValuePair<K, V> a, KeyValuePair<K, V> b) {
            return a.getKey().compareTo(b.getKey()) == 0;
        }

    }

    public static class lHasher implements IHash<Long> {
        public int hashCode(Long a) {
            return Math.abs(a.hashCode());
        }

        public boolean equal(Long a, Long b) {
            return a.compareTo(b) == 0;
        }
    }

    public static class defaultHashing<E extends Comparable<E>> implements IHash<E> {
        public int hashCode(E a) {
            return Math.abs(a.hashCode());
        }

        public boolean equal(E a, E b) {
            return a.compareTo(b) == 0;
        }
    }
}
