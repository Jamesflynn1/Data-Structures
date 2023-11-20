@SuppressWarnings("unchecked")
public class LinkedHashMap<K extends Comparable<K>, V> implements IMap<K, V> {

    protected Buckets<KeyValuePair<K, V>>[] table;
    public int size;
    private int capacity;
    public Buckets<KeyValuePair<K, V>> head;
    private IHash<KeyValuePair<K, V>> hasher;

    public LinkedHashMap() {
        /* for very simple hashing, primes reduce collisions */
        this(11);
    }

    public LinkedHashMap(int capacity) {
        size = 0;
        this.capacity = capacity;
        table = new Buckets[capacity];
        initTable();
        hasher = new Hasher.KVHasher<K, V>();
    }

    /*
     * public V find(K key) { //returns the number of comparisons required to find
     * element using Linear Search. int hashCode = hash(key); int location =
     * hashCode % table.length; int comparisons = 1; ListElement<KeyValuePair>
     * element = table[location].head;
     * 
     * while (element != null) { if (element.getValue().getKey().equals(key)) {
     * return element.getValue().getValue(); } element = element.getNext();
     * comparisons++; } }
     */
    public HashMapIterator<K, V> iterator() {
        return new HashMapIterator<K, V>(head);
    }

    protected void initTable() {
        for (int i = 0; i < table.length; i++) {
            table[i] = new Buckets<KeyValuePair<K, V>>();
        }
    }

    protected int hash(K key) {
        int code = Math.abs(key.hashCode());
        return code;
    }

    public void add(K key, V value) {
        if (key != null) {
            int hash_code = hash(key);
            int location = hash_code % table.length;
            Buckets<KeyValuePair<K, V>> bucket = table[location];
            if (bucket.add(new KeyValuePair<K, V>(key, value))) {
                this.size++;

                if ((bucket.getPrev() == null && bucket.getNext() == null) && bucket != head) {
                    if (head == null) {
                        head = bucket;
                    } else {
                        head.setNext(bucket);
                        bucket.setPrev(head);
                        head = bucket;
                    }
                }
            }
        }

    }

    public Object[] getArray() {
        Buckets<KeyValuePair<K, V>> array = new Buckets<KeyValuePair<K, V>>();
        if (head == null || head.isEmpty()) {
            return array.returnArray();
        } else {
            Buckets<KeyValuePair<K, V>> next = head;
            array.merge(next);
            while (next.getPrev() != null) {
                next = next.getPrev();
                array.merge(next);
            }
        }
        return array.returnArray();
    }

    public V get(K key) {
        KeyValuePair<K, V> result = getKV(key);
        if (result == null) {
            return null;
        } else {
            return result.getValue();
        }
    }

    public KeyValuePair<K, V> getKV(K key) {
        if (key != null) {
            int hash_code = hash(key);
            int location = hash_code % table.length;
            if ((KeyValuePair<K, V>) table[location].getHash(new KeyValuePair<K, V>(key, null), hasher) != null) {
                return (KeyValuePair<K, V>) table[location].getHash(new KeyValuePair<K, V>(key, null), hasher);

            } else {
                return null;
            }
        } else {
            return null;
        }

    }

}
