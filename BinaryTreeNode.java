public class BinaryTreeNode<E extends Comparable<E>> {

    private E value;
    private BinaryTreeNode<E> left;
    private BinaryTreeNode<E> right;
    private BinaryTreeNode<E> parent;
    private int height;

    public BinaryTreeNode(E val) {
        value = val;
        left = null;
        right = null;
        parent = null;
        this.height = 0;
    }

    public E getValue() { 
        return value; 
    }
    public int getHeight(){
        return this.height;
    }

    public BinaryTreeNode<E> getLeft() { 
        return left; 
    }

    public BinaryTreeNode<E> getRight() {
        return right; 
    }
    public BinaryTreeNode<E> getParent() {
        return parent; 
    }

    public void setValue(E v) { 
        value = v;
    }

    public void setParent(BinaryTreeNode<E> p){
        parent = p;
    }

    public void setLeft(BinaryTreeNode<E> p) {
        left = p; 
    }
    
    public void updateHeight(){
        if (left != null && right != null){
            this.height = Math.max(left.getHeight(), right.getHeight())+1;
        }
        else if (left != null){
            this.height = left.getHeight()+1;
        }
        else if (right != null){
            this.height = right.getHeight()+1;
        }
        else{
            this.height = 0;
        }
    }

    public void setRight(BinaryTreeNode<E> p) {
        right = p; 
    }

}
