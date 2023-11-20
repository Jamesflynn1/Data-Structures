package uk.ac.warwick.cs126.structures;

@SuppressWarnings("unchecked")
public class HashSet<E> implements ISet<E> {

    protected MyArrayList<E>[] table;
    private int size;
    private int capacity;

    public HashSet() {
        /* for very simple hashing, primes reduce collisions */
        this(11);
    }

    public HashSet(int capacity) {
        table = new MyArrayList[capacity];
        this.capacity = capacity;
        this.size = 0;
        initTable();
    }

    protected void initTable() {
        for (int i = 0; i < table.length; i++) {
            table[i] = new MyArrayList<E>();
        }
    }

    protected int hash(E element) {
        int code = Math.abs(element.hashCode());
        return code;
    }

    public boolean add(E element) {
        if (element != null) {
            if (this.contains(element) == false) {
                int hash_code = hash(element);
                int location = hash_code % table.length;
                if (table[location].add(element)) {
                    this.size++;
                    return true;
                }
            }
        }
        return false;
    }

    public HashSet<E> union(HashSet<E> b) {
        if (b.size > this.size) {
            return null;
        } else {
            return null;

        }

    }

    public HashSet<E> intersect(HashSet<E> b) {
        return null;

    }

    public boolean contains(E element) {
        if (element != null) {
            int hash_code = hash(element);
            int location = hash_code % table.length;

            MyArrayList<E> searchList = table[location];
            if (searchList != null) {
                if (searchList.contains(element)) {
                    return searchList.contains(element);
                }
            }
        }
        return false;

    }

    public void clear() {
        table = new MyArrayList[capacity];
        this.size = 0;
        initTable();
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return this.size;
    }

    public boolean remove(E element) {
        if (element != null && table != null) {
            int hash_code = hash(element);
            int location = hash_code % table.length;

            MyArrayList<E> searchList = table[location];
            if (searchList != null) {
                if (searchList.remove(element)) {
                    this.size--;
                    return true;

                }
            }
        }
        return false;
    }
}