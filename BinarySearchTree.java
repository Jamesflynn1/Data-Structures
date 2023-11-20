import java.lang.reflect.Array;

public class BinarySearchTree<K extends Comparable<K>, V> extends BinaryTree<KeyValuePair<K, V>> {
    private V[] sorted;
    private K[] sortedKey;
    private int index;
    protected BinaryTreeNode<KeyValuePair<K, V>> lastDeletedParent;

    public BinarySearchTree() {
        this(new Comparator.KeyValuePairComparator());
    }

    public BinarySearchTree(IComparator<KeyValuePair<K, V>> comparator) {
        super(comparator);
    }

    
    /** 
     * @param key
     * @param value
     * @return BinaryTreeNode<KeyValuePair<K, V>>
     */
    public BinaryTreeNode<KeyValuePair<K, V>> add(K key, V value) {
        if (key != null) {
            return super.add(new KeyValuePair(key, value));
        }
        return null;
    }

    
    /** 
     * @param key
     * @param value
     * @return BinaryTreeNode<KeyValuePair<K, V>>
     */
    public BinaryTreeNode<KeyValuePair<K, V>> search(K key, V value) {
        return this.search(this.root, key, value);
    }

    
    /** 
     * @param BinaryTreeNode<KeyValuePair<K
     * @param node
     * @param key
     * @param value
     * @return BinaryTreeNode<KeyValuePair<K, V>>
     */
    public BinaryTreeNode<KeyValuePair<K, V>> search(BinaryTreeNode<KeyValuePair<K, V>> node, K key, V value) {
        if (node == null) {
            // System.out.println("null");
            return null;
        }
        if (super.comparator.equal(node.getValue(), new KeyValuePair<K, V>(key, value))) {
            return node;
        } else if (super.comparator.compare(node.getValue(), new KeyValuePair<K, V>(key, value)) <= 0) {
            return search(node.getRight(), key, value);
        } else {
            return search(node.getLeft(), key, value);
        }
    }

    
    /** 
     * @param key
     * @return BinaryTreeNode<KeyValuePair<K, V>>
     */
    public BinaryTreeNode<KeyValuePair<K, V>> search(K key) {
        return this.search(this.root, key);
    }

    
    /** 
     * @param BinaryTreeNode<KeyValuePair<K
     * @param node
     * @param key
     * @return BinaryTreeNode<KeyValuePair<K, V>>
     */
    public BinaryTreeNode<KeyValuePair<K, V>> search(BinaryTreeNode<KeyValuePair<K, V>> node, K key) {
        if (node == null) {
            // System.out.println("null");
            return null;
        }
        if (super.comparator.equal(node.getValue(), new KeyValuePair<K, V>(key, null))) {
            return node;
        } else if (super.comparator.compare(node.getValue(), new KeyValuePair<K, V>(key, null)) <= 0) {
            return search(node.getRight(), key);
        } else {
            return search(node.getLeft(), key);
        }
    }

    
    /** 
     * @param value
     * @return BinaryTreeNode<KeyValuePair<K, V>>
     */
    public BinaryTreeNode<KeyValuePair<K, V>> search(V value) {
        return this.search(this.root, value);
    }

    
    /** 
     * @param BinaryTreeNode<KeyValuePair<K>
     * @param node
     * @param value
     * @return BinaryTreeNode<KeyValuePair<K, V>>
     */
    public BinaryTreeNode<KeyValuePair<K, V>> search(BinaryTreeNode<KeyValuePair<K, V>> node, V value) {
        if (node == null) {
            // System.out.println("null");
            return null;
        }
        if (super.comparator.equal(node.getValue(), new KeyValuePair<K, V>(null, value))) {
            return node;
        } else if (super.comparator.compare(node.getValue(), new KeyValuePair<K, V>(null, value)) <= 0) {
            return search(node.getRight(), value);
        } else {
            return search(node.getLeft(), value);
        }
    }

    
    /** Searches for the given key in the tree. Returns true if found, false otherwise
     * @param key the key that we are checking exists within the tree.
     * @return boolean whether or not a node exists in the tree with the given key k.
     */
    public boolean contains(K key) {
        if (this.search(key) == null) {
            return false;
        } else {
            return true;
        }
    }

    
    /** 
     * @param gClass
     * @return V[]
     */
    public V[] getValueOrdered(Class<V> gClass) {
        if (this.root != null) {
            sorted = (V[]) Array.newInstance(gClass, super.size());
            index = 0;
            getValueOrdered(this.root);
            return sorted;
        } else {
            return (V[]) Array.newInstance(gClass, 0);
        }

    }

    
    /** Adds the subtree of n to the array representation of the tree
     * @param n the root of the subtree that is being converted to an array.
     */
    public void getValueOrdered(BinaryTreeNode<KeyValuePair<K, V>> n) {
        if (n != null) {
            getValueOrdered(n.getLeft());
            sorted[index] = n.getValue().getValue();
            index++;
            getValueOrdered(n.getRight());

        }
    }

    public void unique() {
        if (unique(root)) {
            System.out.println("Uniqueness");
            System.out.println("Uniqueness");
            System.out.println("Uniqueness");

        }

    }

    
    /** 
     * @param n
     * @return boolean
     */
    public boolean unique(BinaryTreeNode<KeyValuePair<K, V>> n) {
        if (n != null && n.getLeft() != null && n.getRight() == null) {
            if (!unique(n.getLeft()) || !unique(n.getRight())) {
                return false;
            }
            if (n.getLeft().getValue() == n.getValue() && n.getLeft().getValue() == n.getValue()) {
                return false;
            }
            return true;

        }
        return true;
    }

    
    /** Returns an array of the given class of all elements of the tree in order.
     * @param gClass the type of the return array.
     * @return K[] an array that contains all the elements of the tree in order.
     */
    public K[] getKeyOrdered(Class<K> gClass) {
        if (this.root != null) {
            sortedKey = (K[]) Array.newInstance(gClass, super.size());
            index = 0;
            getValueOrdered(this.root);
            return sortedKey;
        } else {
            return (K[]) Array.newInstance(gClass, 0);
        }

    }

    
    /** 
     * @param n
     * @param depth
     */
    public void printTree(BinaryTreeNode<KeyValuePair<K, V>> n, int depth) {
        if (n != null) {
            printTree(n.getLeft(), depth + 1);

            System.out.println("Value : " + n.getValue().toString() + " Depth " + ((Integer) depth).toString()
                    + " Height " + ((Integer) n.getHeight()));
            System.out.println(" Expected : " + ((Integer) (depth + n.getHeight())).toString());

            printTree(n.getRight(), depth + 1);

        }
    }

    public void updateTreeHeight() {
        if (root.getRight() == null || root.getLeft() == null) {
            return;
        }
        updateTreeHeight(root.getLeft());
        updateTreeHeight(root.getRight());
    }

    
    /** A utility function that is used to ensure all the heights are updated
     * DEPRECATED
     * @param n
     */
    public void updateTreeHeight(BinaryTreeNode<KeyValuePair<K, V>> n) {
        if (n.getLeft() == null && n.getRight() == null) {
            n.updateHeight();
            this.updateParentHeight(n);

        } else if (n.getLeft() == null) {
            updateTreeHeight(n.getRight());
        } else if (n.getRight() == null) {
            updateTreeHeight(n.getLeft());
        } else {
            updateTreeHeight(n.getLeft());
            updateTreeHeight(n.getRight());
        }
    }

    public void printTree() {
        updateTreeHeight();
        printTree(root, 0);
    }

    
    /** 
     * @param key
     * @return V
     */
    public V delete(K key) {
        BinaryTreeNode<KeyValuePair<K, V>> deleted = this.search((K) key);
        V deletedValue = deleted.getValue().getValue();
        if (deleted != null) {
            removal(deleted);
            return deletedValue;
        } else {
            return null;
        }
    }

    
    /** 
     * @param key
     * @param value
     * @return V
     */
    public V delete(K key, V value) {
        BinaryTreeNode<KeyValuePair<K, V>> deleted = this.search((K) key, value);
        V deletedValue = deleted.getValue().getValue();

        if (deleted != null) {
            removal(deleted);
            return deletedValue;
        } else {
            return null;
        }
    }
    
    /** Removes the given node from the tree making sure to maintain the ordering property
     * @param node: the node to be removed from the tree
     * @return V the value of the node that will be removed from the tree
     */
    // FIX UP, PRINT TREE, PROBABLY EDGE CASE, DOESN'T PROPERLY REMOVE!!
    public V removal(BinaryTreeNode<KeyValuePair<K, V>> node) {
        if (node == null) {
            return null;
        }
        V deletedValue = node.getValue().getValue();
        lastDeletedParent = node.getParent();
        if (node.getRight() == null) {
            if (node.getLeft() != null) {
                node.getLeft().setParent(node.getParent());
            }

            if (node.getParent().getLeft() == node) {
                node.getParent().setLeft(node.getLeft());
            } else {
                node.getParent().setRight(node.getLeft());
            }
        } else {
            BinaryTreeNode<KeyValuePair<K, V>> promoted = nextInOrder(node);
            node.setValue(promoted.getValue());
            BinaryTreeNode<KeyValuePair<K, V>> parent = promoted.getParent();
            // FIX PLZ
            lastDeletedParent = promoted;
            if (parent == node) {
                parent.setRight(promoted.getRight());
                if (promoted.getRight() != null) {
                    promoted.getRight().setParent(parent);
                }
            } else {
                parent.setLeft(promoted.getRight());
                if (promoted.getRight() != null) {
                    promoted.getRight().setParent(parent);
                }
            }
            promoted.setParent(null);
            promoted.setLeft(null);
            promoted.setRight(null);
        }
        this.size--;
        lastDeletedParent.updateHeight();
        return deletedValue;
    }

}