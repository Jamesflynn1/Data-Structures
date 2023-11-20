public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {
    BinaryTreeNode<KeyValuePair<K, V>> tempRoot;

    public AVLTree(IComparator<KeyValuePair<K, V>> comparator) {
        super(comparator);
    }

    public AVLTree() {
        super();
    }

    /**
     * Adds the key and its associated value to the tree, ensuring the ordering and
     * balance properties are maintained
     * 
     * @param key   The key of the KeyValuePair that will be added to the AVL tree.
     * @param value The value of the KeyValuePair that will be added to the AVL tree
     * @return
     */
    public BinaryTreeNode<KeyValuePair<K, V>> add(K key, V value) {
        if (key != null) {
            // Find a better way
            BinaryTreeNode<KeyValuePair<K, V>> node = super.add(key, value);
            balance(node.getParent());
            return node;
        }
        return null;
    }

    /**
     * Balances the subtree as defined by the node and subsequently it's supertrees
     * if they exist. This is achieved by performing a series of rotations on the
     * subtree around the root to acheive the desired balance factors of +1,0,-1 for
     * the node.
     * 
     * @param node The root of the subtree that is undergoing the balance procedure
     */
    public void balance(BinaryTreeNode<KeyValuePair<K, V>> node) {
        if (node == null) {
            return;
        }
        BinaryTreeNode<KeyValuePair<K, V>> next = node.getParent();
        if (node != null) {
            int bfP = this.calculateBF(node);
            //Right heavy
            if (bfP >= 2) {
                int bfR = this.calculateBF(node.getRight());
                // Right right heavy
                if (bfR >= 0) {
                    this.rr(node);
                }
                // Right left heavy
                else {
                    this.drl(node);
                }
            }
            //Left heavy
            else if (bfP <= -2) {
                int bfL = this.calculateBF(node.getLeft());
                // left right heavy
                if (bfL > 0) {
                    this.dlr(node);
                }
                // left left heavy
                else {
                    this.lr(node);
                }
            }
            if ((next) == null) {
                return;
            }
            balance(next);
        }
    }

    /**
     * @param node The current root node of the subtree that will undergo the
     *             rotation
     */
    private void lr(BinaryTreeNode<KeyValuePair<K, V>> node) {
        // 1 Set b as the root of the given subtree and set b as the child of the parent
        // of A if it exists
        BinaryTreeNode<KeyValuePair<K, V>> b = node.getLeft();

        if (node == root) {
            this.root = b;
            this.root.setParent(null);

        } else {
            BinaryTreeNode<KeyValuePair<K, V>> localParent = node.getParent();
            if (localParent.getLeft() == node) {
                localParent.setLeft(b);
            } else {
                localParent.setRight(b);
            }
            b.setParent(localParent);
        }
        // 2 a takes b right child as its left child
        if (b.getRight() != null) {
            b.getRight().setParent(node);
        }
        node.setLeft(b.getRight());

        // 3 b takes a as its right child
        node.setParent(b);
        b.setRight(node);

        this.updateParentHeight(node);
        this.updateParentHeight(b);

    }

    /**
     * @param node The current root node of the subtree that will undergo the
     *             rotation.
     */
    private void rr(BinaryTreeNode<KeyValuePair<K, V>> node) {

        // 1 Set b as the root of the given subtree and set b as the child of the parent
        // of A if it exists
        BinaryTreeNode<KeyValuePair<K, V>> b = node.getRight();
        if (node == root) {
            this.root = b;
            root.setParent(null);
        } else {
            BinaryTreeNode<KeyValuePair<K, V>> localParent = node.getParent();
            if (localParent.getLeft() == node) {
                localParent.setLeft(b);
            } else {
                localParent.setRight(b);
            }
            b.setParent(localParent);
        }
        // 2 a takes b right child as its left child
        if (b.getLeft() != null) {
            b.getLeft().setParent(node);
        }
        node.setRight(b.getLeft());

        // 3 b takes a as its left child
        node.setParent(b);
        b.setLeft(node);

        this.updateParentHeight(node);
        this.updateParentHeight(b);

    }

    /**
     * Right rotates the left child of the root then left rotates the root.
     * 
     * @param node The current root node of the subtree that will undergo the
     *             rotation.
     */
    private void drl(BinaryTreeNode<KeyValuePair<K, V>> node) {

        BinaryTreeNode<KeyValuePair<K, V>> b = node.getRight();
        BinaryTreeNode<KeyValuePair<K, V>> c = b.getLeft();
        BinaryTreeNode<KeyValuePair<K, V>> localParent = node.getParent();

        if (node == root) {
            root = c;
            c.setParent(null);
        } else if (localParent.getLeft() == node) {
            localParent.setLeft(c);
            c.setParent(localParent);
        } else {
            localParent.setRight(c);
            c.setParent(localParent);
        }

        node.setRight(c.getLeft());
        if (node.getRight() != null) {
            node.getRight().setParent(node);
        }
        b.setLeft(c.getRight());
        if (b.getLeft() != null) {
            b.getLeft().setParent(b);
        }
        c.setRight(b);
        c.setLeft(node);
        b.setParent(c);
        node.setParent(c);

        this.updateParentHeight(node);
        this.updateParentHeight(b);
        this.updateParentHeight(c);
    }

    /**
     * Left rotates the right child of the root then right rotates the root.
     * 
     * @param node The current root node of the subtree that will undergo the
     *             rotation.
     */
    private void dlr(BinaryTreeNode<KeyValuePair<K, V>> node) {
        BinaryTreeNode<KeyValuePair<K, V>> b = node.getLeft();
        BinaryTreeNode<KeyValuePair<K, V>> c = b.getRight();
        BinaryTreeNode<KeyValuePair<K, V>> localParent = node.getParent();

        if (node == root) {
            root = c;
            c.setParent(null);
        } else if (localParent.getLeft() == node) {
            localParent.setLeft(c);
            c.setParent(localParent);
        } else {
            localParent.setRight(c);
            c.setParent(localParent);

        }
        node.setLeft(c.getRight());
        if (node.getLeft() != null) {
            node.getLeft().setParent(node);
        }
        b.setRight(c.getLeft());
        if (b.getRight() != null) {
            b.getRight().setParent(b);
        }
        c.setLeft(b);
        c.setRight(node);
        b.setParent(c);
        node.setParent(c);

        this.updateParentHeight(node);
        this.updateParentHeight(b);
        this.updateParentHeight(c);
    }

    /**
     * Calculates the balance factor of subtree rooted at node. Positive if the tree
     * is right weighted, negative if left weighted or 0 if neither.
     * 
     * @param node The root node fo the subtree that we are calculating the balance
     *             factor of.
     * @return The balance factor of the given subtree as an int
     */
    public int calculateBF(BinaryTreeNode<KeyValuePair<K, V>> node) {
        if (node.getLeft() != null && node.getRight() != null) {
            return node.getRight().getHeight() - node.getLeft().getHeight();
        } else if (node.getLeft() != null) {
            return -node.getLeft().getHeight();
        } else if (node.getRight() != null) {
            return node.getRight().getHeight();
        } else {
            return 0;
        }
    }

    /**
     * Removes the BinaryTreeNode that has the corresponding key and value from the
     * binary tree, ensuring that the tree remains balanced by performing the
     * appropriate balancing operations.
     * 
     * @param key to find and delete from the tree
     * @return The value of the KeyValuePair that has been deleted from the tree
     */
    public V delete(K key) {
        if (this.search(key) == null) {
            return null;
        }

        V retValue = super.delete(key);
        balance(lastDeletedParent);

        return retValue;
    }

    /**
     * Removes the BinaryTreeNode that has the corresponding key and value from the
     * binary tree, ensuring that the tree remains balanced by performing the
     * appropriate balancing operations.
     * 
     * @param key   that should match with the key to find the KeyValuePair to
     *              delete
     * @param Value that should match with the key to find the KeyValuePair to
     *              delete
     * @return V the value that was deleted from the tree
     */
    public V delete(K key, V value) {

        if (this.search(key, value) == null) {
            return null;
        }

        V retValue = super.delete(key, value);
        balance(lastDeletedParent);
        return retValue;
    }
}