public class HashSetIterator<E> {

   private int bucketPos;
   private Buckets<E> currentBucket;
   private E curr;

   public HashSetIterator(Buckets<E> head) {

      currentBucket = head;
      bucketPos = 0;
   }

   public E getNext() {
      if (currentBucket == null) {
         return null;
      }
      if (curr == null) {
         curr = currentBucket.get(0);
         bucketPos++;
         return curr;
      } else if (bucketPos < currentBucket.size()) {
         curr = currentBucket.get(bucketPos);
         bucketPos++;
         return curr;
      } else {
         if (getNextBucket() == false) {
            return null;
         } else {
            return getNext();
         }
      }
   }

   public E current() {
      return curr;
   }

   private boolean getNextBucket() {
      if (currentBucket.getPrev() == null) {
         return false;
      } else {
         currentBucket = currentBucket.getPrev();
         bucketPos = 0;
         return true;
      }
   }

   public boolean hasNext() {
      return getNext() != null;
   }
}
