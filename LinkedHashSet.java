import java.lang.reflect.Array;

@SuppressWarnings("unchecked")
public class LinkedHashSet<E> implements ISet<E> {

    protected Buckets<E>[] table;
    private int size;
    private int capacity;
    private Class<E> eClass;
    private Buckets<E> head;

    private IHash<E> hasher;
    private IComparator<E> preferedElement;

    public LinkedHashSet(Class<E> gClass) {
        this(gClass, new Hasher.defaultHasher<E>());

    }

    public LinkedHashSet(Class<E> gClass, IHash<E> hasher, IComparator<E> preferedElement) {
        /* for very simple hashing, primes reduce collisions */
        this(gClass, 11);
        this.hasher = hasher;
        this.preferedElement = preferedElement;
    }

    public LinkedHashSet(Class<E> gClass, IHash<E> hasher) {
        /* for very simple hashing, primes reduce collisions */
        this(gClass, 11);
        this.hasher = hasher;
    }

    public LinkedHashSet(Class<E> gClass, int capacity) {
        table = (Buckets<E>[]) Array.newInstance(Buckets.class, capacity);
        this.eClass = gClass;
        this.capacity = capacity;
        this.size = 0;
        initTable();
    }

    protected void initTable() {
        for (int i = 0; i < table.length; i++) {
            table[i] = new Buckets<E>();
        }
    }

    /**
     * @return boolean
     */
    // protected int hash(E element) {
    // int code = Math.abs(element.hashCode());
    // return code;
    // }

    public boolean add(E element) {
        if (element != null) {
            if (this.contains(element) == false) {
                int hash_code = hasher.hashCode(element);
                int location = hash_code % table.length;
                Buckets<E> bucket = table[location];
                if (bucket.add(element)) {
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

                    return true;
                }

            }
        }
        return false;
    }

    /**
     * @param newData
     */
    // Or use clone
    public void copy(LinkedHashSet<E> newData) {
        System.arraycopy(newData.table, 0, this.table, 0, newData.table.length);
        this.size = newData.size;
        this.capacity = newData.capacity;
        this.hasher = newData.hasher;
        int i = 0;
        Buckets<E> pos = null;
        while (i < table.length && pos == null) {
            pos = this.table[i];
            i++;
        }
        while (pos.getNext() != null) {
            pos = pos.getNext();
        }
        this.head = pos;
    }

    /**
     * @return HashSetIterator<E>
     */
    public HashSetIterator<E> iterator() {
        return new HashSetIterator<E>(head);
    }

    /**
     * @param comparator
     * @return E
     */
    public E findMin(IComparator<E> comparator) {
        HashSetIterator<E> iter = this.iterator();
        E currentMin = null;
        while (iter.hasNext() == true) {
            if (currentMin == null) {
                currentMin = iter.current();
            } else if (comparator.compare(currentMin, iter.current()) > 0) {
                currentMin = iter.current();
            }
        }
        return currentMin;
    }

    /**
     * @param b
     * @return LinkedHashSet<E>
     */
    public LinkedHashSet<E> union(LinkedHashSet<E> b) {
        LinkedHashSet<E> joint = new LinkedHashSet<E>(eClass);
        E[] additional;
        if (b.size > this.size) {
            additional = this.getArray();
            joint.copy(b);
        } else {
            joint.copy(this);
            additional = b.getArray();

        }

        for (int i = 0; i < additional.length; i++) {
            E current = joint.get(additional[i]);
            if (current != null) {
                if (preferedElement.compare(current, additional[i]) >0) {
                    System.out.println(joint.remove(additional[i]));
                    joint.add(additional[i]);                
                }
            }

        }
        return joint;
    }

    /**
     * @param b
     * @return LinkedHashSet<E>
     */
    // all of a without b (unsymmetric)
    public LinkedHashSet<E> difference(LinkedHashSet<E> b) {
        LinkedHashSet<E> joint = new LinkedHashSet<E>(eClass);
        joint.copy(this);
        E[] additional = b.getArray();
        for (int i = 0; i < additional.length; i++) {
            E current = joint.get(additional[i]);
            if (current != null) {
                joint.remove(additional[i]);
            }
        }
        return joint;
    }

    /**
     * @param b
     * @return LinkedHashSet<E>
     */
    // Speed???
    public LinkedHashSet<E> symmetricDifference(LinkedHashSet<E> b) {
        LinkedHashSet<E> aDiff = this.difference(b);
        LinkedHashSet<E> bDiff = b.difference(this);

        return aDiff.union(bDiff);
    }

    /**
     * @param b
     * @return LinkedHashSet<E>
     */
    public LinkedHashSet<E> intersect(LinkedHashSet<E> b) {
        LinkedHashSet<E> joint = new LinkedHashSet<E>(eClass);
        E[] additional;
        if (b.size > this.size) {
            additional = this.getArray();
            joint.copy(b);
        } else {
            joint.copy(this);
            additional = b.getArray();

        }

        for (int i = 0; i < additional.length; i++) {
            E current = joint.get(additional[i]);
            if (current != null) {
                if (preferedElement.compare(current, additional[i]) < 0) {
                    //joint.add(additional[i]);
                } else {
                    joint.remove(additional[i]);
                    joint.add(additional[i]);
                }
            } else {
                joint.remove(additional[i]);
            }

        }
        return joint;
    }

    /**
     * @param element The element
     * @return boolean
     */
    public boolean contains(E element) {
        if (element != null) {
            int hash_code = hasher.hashCode(element);
            int location = hash_code % table.length;

            Buckets<E> searchList = table[location];
            if (searchList != null) {
                if (searchList.containsHash(element, this.hasher)) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * Find and returns the element that matches (as according to the provided
     * hasher) the given element that is container within the hashset
     * 
     * @param element: the element that will be compared to
     * @return E the element found in the hashset that is equivalent according to
     *         the hasher provided
     */
    public E get(E element) {
        if (element != null) {
            int hash_code = hasher.hashCode(element);
            int location = hash_code % table.length;

            Buckets<E> searchList = table[location];
            if (searchList != null) {
                return searchList.getHash(element, this.hasher);
            }
        }
        return null;
    }

    /**
     * Clears the entire hashset maintaining the hasher and capacity
     */
    public void clear() {
        head = null;
        table = (Buckets<E>[]) Array.newInstance(eClass, capacity);
        this.size = 0;
        initTable();
    }

    /**
     * @return E[] A generic array of type eClass that corresponds to the entire
     *         HashSet.
     */
    public E[] getArray() {
        //The Bucket that the entire hashset will be added to
        Buckets<E> array = new Buckets<E>();
        if (head == null) {
            return array.returnArray(eClass);
        } else {
            Buckets<E> next = head;
            array.merge(next);
            while (next.getPrev() != null) {
                next = next.getPrev();
                array.merge(next);
            }
        }
        return array.returnArray(eClass);
    }

    /**
     * @return boolean: Whether or not the HashSet is empty
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return int: The size of the HashSet
     */
    public int size() {
        return this.size;
    }

    /**
     * @param element The element that will be attempted to be removed
     * @return boolean: Whether the given element was found and removed from the
     *         HashSet
     */
    public boolean remove(E element) {
        if (element != null && table != null) {
            int hash_code = hasher.hashCode(element);
            int location = hash_code % table.length;

            Buckets<E> searchList = table[location];
            if (searchList != null) {
                if (searchList.remove(element)) {
                    this.size--;

                    // If the bucket that was deleted from is empty
                    if (searchList.size() == 0) {
                        if (searchList.getNext() != null && searchList.getPrev() != null) {
                            Buckets<E> prev = searchList.getPrev();
                            Buckets<E> next = searchList.getNext();

                            next.setPrev(prev);
                            prev.setNext(next);

                        }
                        // If this container is the last container
                        else if (searchList.getNext() == null && searchList.getPrev() == null) {
                            head = null;
                        } else if (searchList.getNext() == null) {
                            head = searchList.getPrev();
                            searchList.getPrev().setNext(null);
                        } else if (searchList.getPrev() == null) {
                            searchList.getNext().setPrev(null);
                        }

                        searchList.setNext(null);
                        searchList.setPrev(null);

                    }
                }
                return true;
            }

        }
        return false;

    }
}