public class Comparator {

    public static class KeyValuePairComparator<K extends Comparable<K>, V> implements IComparator<KeyValuePair<K, V>> {

        public int compare(KeyValuePair<K, V> a, KeyValuePair<K, V> b) {
            return a.getKey().compareTo(b.getKey());
        }

        /*
         * public int compare(KeyValuePair<K,V> a, K b) { return
         * a.getKey().compareTo(b); }
         */
        public boolean equal(KeyValuePair<K, V> a, KeyValuePair<K, V> b) {
            return a.getKey().equals(b.getKey());
        }
    }
}