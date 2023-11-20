public class BinaryTree<E extends Comparable<E>> {
    protected BinaryTreeNode<E> root;
    protected int size;
    protected IComparator<E> comparator;

    public BinaryTree(IComparator<E> comparator) {
        root = null;
        this.size = 0;
        this.comparator = comparator;

    }
    
    /** Eithers adds to n directly or adds to the left or right subtree of n depending on the comparision of v and the value of n
     * @param n The root of the subtree
     * @param v The value of new node that will be added
     * @return BinaryTreeNode<E>
     */
    // Unique keys
    private BinaryTreeNode<E> addToSubTree(BinaryTreeNode<E> n, E v) {
        if (n != null) // sanity check!
        {
            E nValue = n.getValue();
            BinaryTreeNode<E> added = null;
            if (comparator.compare(v, nValue) <= 0) {
                if (n.getLeft() == null) {
                    added = new BinaryTreeNode<E>(v);
                    n.setLeft(added);
                    added.setParent(n);
                    if (n.getRight() == null) {
                        n.updateHeight();
                        this.updateParentHeight(n.getParent());
                    }
                } else {
                    added = addToSubTree(n.getLeft(), v);
                }
            } else {
                if (n.getRight() == null) {
                    added = new BinaryTreeNode<E>(v);
                    n.setRight(added);
                    added.setParent(n);
                    if (n.getLeft() == null) {
                        n.updateHeight();
                        this.updateParentHeight(n.getParent());
                    }
                } else {
                    added = addToSubTree(n.getRight(), v);
                }
            }

            return added;
        }
        return null;
    }

    
    /** Updates the height of the given node and its ancestors up to the root
     * @param node the node to be updated
     */
    protected void updateParentHeight(BinaryTreeNode<E> node) {
        if (node != null) {
            node.updateHeight();
            this.updateParentHeight(node.getParent());
        }
    }

    
    /** Adds the binary tree by recursive calls to addToSubTree.
     * Maintains the order property of the tree
     * @param v The element to be added to the tree.
     * @return BinaryTreeNode<E> The created BinaryTreeNode
     */
    public BinaryTreeNode<E> add(E v) {
        if (v != null) {
            this.size++;
            if (root == null) {
                root = new BinaryTreeNode<>(v);
                root.setParent(null);
                return root;
            } else {
                return addToSubTree(root, v);
            }
        }
        return null;
    }

    
    /** Given n find the node that is the next in order in the tree
     * @param n the current node
     * @return BinaryTreeNode<E> the next node
     */
    public BinaryTreeNode<E> nextInOrder(BinaryTreeNode<E> n) {
        if (n == null) {
            return null;
        }
        BinaryTreeNode<E> next = n.getRight();

        while (next.getLeft() != null) {
            next = next.getLeft();
        }
        return next;
    }

    
    /**  Returns the size of the tree
     * @return int the size of the tree
     */
    public int size() {
        return this.size;
    }
}
