@SuppressWarnings("unchecked")
public class HashMap<K extends Comparable<K>, V> implements IMap<K, V> {

    protected KeyValuePairLinkedList[] table;

    public HashMap() {
        this(11);
    }

    public HashMap(int size) {
        table = new KeyValuePairLinkedList[size];
        initTable();
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
    protected void initTable() {
        for (int i = 0; i < table.length; i++) {
            table[i] = new KeyValuePairLinkedList<>();
        }
    }

    
    /**
     * @param key to be hashed
     * @return int the hash value of the given key.
     */
    protected int hash(K key) {
        int code = Math.abs(key.hashCode());
        return code;
    }

    
    /** Adds the given key value to the hashmap
     * @param key to be added
     * @param value to be added
     */
    public void add(K key, V value) {
        if (key != null) {
            int hash_code = hash(key);
            int location = hash_code % table.length;
            table[location].add(key, value);
        }

    }

    
    /** Returns the value associated with the key
     * @param key 
     * @return V that is given by the key.
     */
    public V get(K key) {
        if (key != null) {
            int hash_code = hash(key);
            int location = hash_code % table.length;
            if ((V) table[location].get(key) != null) {
                return (V) table[location].get(key).getValue();

            } else {
                return null;
            }
        } else {
            return null;
        }

    }
}
