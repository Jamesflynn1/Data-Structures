//RemovedArrayListClass has been removed for the purposes of uploading this to GitHub
public class Buckets<E> extends RemovedArrayListClass<E> {
    private Buckets<E> previous;
    private Buckets<E> next;

    public Buckets() {
        super();
    }

    public Buckets<E> getNext() {
        return this.next;
    }

    public Buckets<E> getPrev() {
        return this.previous;
    }

    public void setNext(Buckets<E> e) {
        this.next = e;
    }

    public void setPrev(Buckets<E> e) {
        this.previous = e;
    }
}
