public class HashMapIterator<K extends Comparable<K>, V> {

   private int bucketPos;
   private Buckets<KeyValuePair<K, V>> currentBucket;
   private KeyValuePair<K, V> curr;

   public HashMapIterator(Buckets<KeyValuePair<K, V>> head) {

      currentBucket = head;
      bucketPos = 0;
   }

   
   /** 
    * @return KeyValuePair<K, V>
    */
   private KeyValuePair<K, V> getNext() {
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

   
   /** 
    * @return KeyValuePair<K, V> Returns the KeyValuePair of the associated hashmap at the current 
    */
   public KeyValuePair<K, V> current() {
      return curr;
   }

   
   /** 
    * @return boolean
    */
   private boolean getNextBucket() {
      if (currentBucket.getPrev() == null) {
         return false;
      } else {
         currentBucket = currentBucket.getPrev();
         bucketPos = 0;
         return true;
      }
   }

   
   /** 
    * @return boolean
    */
   public boolean hasNext() {
      return getNext() != null;
   }
}
