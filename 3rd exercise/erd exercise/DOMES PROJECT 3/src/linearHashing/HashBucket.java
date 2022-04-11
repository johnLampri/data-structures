package linearHashing;
import java.io.*;

/**
 * THis class is used to store a number of keys for the implementation of the LinearHashing class.
 * @author giannis lam
 *
 */

class HashBucket {

	private int keysNumber;
	private int[] keys;
	private HashBucket overflowBucket;
	
	
	/**
	 * This method is the constructor of the class.
	 * @param bucketSize
	 */
	
	public HashBucket(int bucketSize) {		// Constructor: initialize variables

		keysNumber = 0;
		keys = new int[bucketSize];
		overflowBucket = null;
	}

	
	/**
	 * This method returns the number of keys stored in the table.
	 * @return
	 */
	
	public int numKeys(){return keysNumber;}

	
	/**
	 *  inserts a key to the node
	 * @param key the key to be inserted.
	 * @param lh The linear hashing class that the hashbucket is.
	 */
	
	public void insertKey(int key, LinearHashing lh) { // inserts a key to the node


		int i;
		int bucketSize = lh.getBucketSize();
		lh.counting();
		int keysNum = lh.getKeysNum();
		lh.counting();
		int keySpace = lh.getKeySpace();
		lh.counting();
		
		for (i = 0; ((i < this.keysNumber & lh.counting()>0) && (i < bucketSize & lh.counting()>0)) ; i++){
		   if (this.keys[i] == key  & lh.counting()>0) {	//key already here. Ignore the new one
		     return;
		   }
		}
		if (i < bucketSize & lh.counting()>0){				// bucket not full write the new key
		  keys[i] = key;
		  lh.counting();
		  this.keysNumber++;
		  lh.counting();
		  keysNum++;
		  lh.counting();
		  lh.setKeysNum(keysNum); 			// update linear hashing class.
		  //System.out.println("HashBucket.insertKey: KeysNum = " + keysNum );
		}
		else {
		    //System.out.println("Overflow.............");
		    if (this.overflowBucket != null & lh.counting()>0){	// pass key to the overflow
		      this.overflowBucket.insertKey(key, lh);
		    }
		    else {						// create a new overflow and write the new key
			this.overflowBucket = new HashBucket(bucketSize);
			lh.counting();
			keySpace += bucketSize;
			lh.counting();
		        lh.setKeySpace(keySpace);		// update linear hashing class.
			this.overflowBucket.insertKey(key, lh);
		    }
		}
	}

	
	/**
	 * This method is used to delete a key from a hashbucket.
	 * @param key the integer to be deleted.
	 * @param lh the linear hashing instance that the key will be deleted from.
	 */
	
	public void deleteKey(int key, LinearHashing lh) { 
		int i;
		int bucketSize = lh.getBucketSize();
		lh.counting();
		int keysNum = lh.getKeysNum();
		lh.counting();
		int keySpace = lh.getKeySpace();
		lh.counting();
		
		for (i = 0; (i < this.keysNumber & lh.counting()>0) && (i < bucketSize & lh.counting()>0); i++) {
		   if (this.keys[i] == key & lh.counting()>0) {
		     if (this.overflowBucket == null & lh.counting()>0) {		// no overflow
			 this.keys[i] = this.keys[this.keysNumber-1];
			 this.keysNumber--;
			 lh.counting();
			 keysNum--;
			 lh.counting();
			 lh.setKeysNum(keysNum);						// update linear hashing class.
		     }	
		     else {											// bucket has an overflow so remove a key from there and bring it here
			 this.keys[i] = this.overflowBucket.removeLastKey(lh);
			 lh.counting();
			 keysNum--;
			 lh.counting();
			 lh.setKeysNum(keysNum);						// update linear hashing class.
			 if (this.overflowBucket.numKeys() == 0 & lh.counting()>0) { 		// overflow empty free it
			   this.overflowBucket = null;
			   keySpace -= bucketSize;
			   lh.counting();
		       lh.setKeySpace(keySpace);					// update linear hashing class.
			 }
		     }
		     return;
		   }
		}
		if (this.overflowBucket != null & lh.counting()>0) {			// look at the overflow for the key to be deleted if one exists
		  this.overflowBucket.deleteKey(key, lh);
		  if (this.overflowBucket.numKeys() == 0 & lh.counting()>0) {	// overflow empty free it
		    this.overflowBucket = null;
		    lh.counting();
		    keySpace -= bucketSize;
		    lh.counting();
		    lh.setKeySpace(keySpace);				// update linear hashing class.
		  }
	      }
	}

	/**
	 * This method removes the bucket's last key.
	 * @param lh instance of the linearHashing class that the bucket is.
	 * @return the last key.
	 */
	
	int removeLastKey(LinearHashing lh) {	// remove bucket last key

		int retval;
		int bucketSize = lh.getBucketSize();
		lh.counting();
		int keySpace = lh.getKeySpace();
		lh.counting();
		
		if (this.overflowBucket == null & lh.counting()>0) {
		  if (this.keysNumber != 0 & lh.counting()>0){
		    this.keysNumber--;
		    lh.counting();
		    return this.keys[this.keysNumber];
		  }
		  return 0;
		}
		else {
		  retval = this.overflowBucket.removeLastKey(lh);
		  lh.counting();
		  if (this.overflowBucket.numKeys() == 0 & lh.counting()>0) {	// overflow empty free it
		    this.overflowBucket = null;
		    lh.counting();
		    keySpace -= bucketSize;
		    lh.counting();
		    lh.setKeySpace(keySpace);			// update linear hashing class.
		  }
		  return retval;
		}
	}

	
	/**
	 * This method is used to search a key in the hashBucket.
	 * @param key
	 * @param lh
	 * @return true if the key is found, false otherwise.
	 */
	
	public boolean searchKey(int key, LinearHashing lh) {

		int i;
		int bucketSize = lh.getBucketSize();
		lh.counting();

		for (i = 0; (i < this.keysNumber & lh.counting()>0) && (i < bucketSize & lh.counting()>0); i++) {
		   if (this.keys[i] == key & lh.counting()>0) {	//key found
		     return true;
		   }
		}
		if (this.overflowBucket != null & lh.counting()>0) {				//look at the overflow for the key if one exists
		  return this.overflowBucket.searchKey(key,lh);
	      }
	      else {
		  return false;
	      }
	}

	/**
	 * This method splits the current bucket.
	 * @param lh 
	 * @param n
	 * @param bucketPos
	 * @param newBucket
	 */
	
	public void splitBucket(LinearHashing lh, int n, int bucketPos, HashBucket newBucket) {	

		int i;
		int bucketSize = lh.getBucketSize();
		lh.counting();
		int keySpace = lh.getKeySpace();
		lh.counting();
		int keysNum = lh.getKeysNum();
		lh.counting();

		for (i = 0; ((i < this.keysNumber & lh.counting()>0) && (i < bucketSize & lh.counting()>0)) ;) {
		   if (((this.keys[i]%n) != bucketPos )& lh.counting()>0){	//key goes to new bucket
		     newBucket.insertKey(this.keys[i], lh);
		     this.keysNumber--;
		     lh.counting();
		     keysNum = lh.getKeysNum();
		     lh.counting();
		     keysNum--;
		     lh.counting();
		     lh.setKeysNum(keysNum);		// update linear hashing class.
		     //System.out.println("HashBucket.splitBucket.insertKey: KeysNum = " + keysNum );
		     this.keys[i] = this.keys[this.keysNumber];
		     lh.counting();
		   }
		   else {				// key stays here
		     i++;
		     lh.counting();
		   }
		}

		if (this.overflowBucket != null & lh.counting()>0) {	// split the overflow too if one exists
		  this.overflowBucket.splitBucket(lh, n, bucketPos, newBucket);
		}
		while (this.keysNumber != bucketSize & lh.counting()>0 ) {
		     if (this.overflowBucket == null & lh.counting()>0) {
			 return;
		     }
		     if (this.overflowBucket.numKeys() != 0 & lh.counting()>0) {
		       this.keys[this.keysNumber] = this.overflowBucket.removeLastKey(lh);
		       lh.counting();
		       if (this.overflowBucket.numKeys() == 0 & lh.counting()>0) {	// overflow empty free it
			 this.overflowBucket = null;
			 lh.counting();
			 keySpace -= bucketSize;
			 lh.counting();
			 lh.setKeySpace(keySpace);      // update linear hashing class.
		       }
		       this.keysNumber++;
		       lh.counting();
		     }
		     else {				// overflow empty free it
			 this.overflowBucket = null;
			 lh.counting();
			 keySpace -= bucketSize;
			 lh.counting();
		         lh.setKeySpace(keySpace);	// update linear hashing class.
		     }
	 	}
	}
/**
 * This method merges the current bucket.
 * @param lh
 * @param oldBucket
 */
	
	public void mergeBucket(LinearHashing lh, HashBucket oldBucket) {	

		int keysNum = 0;
		lh.counting();

		while (oldBucket.numKeys() != 0 & lh.counting()>0) {
		     this.insertKey(oldBucket.removeLastKey(lh), lh);
		}
	}

      public void printBucket(int bucketSize) {

		int i;

		System.out.println("keysNum is: " + this.keysNumber);
		for (i = 0; (i < this.keysNumber ) && (i < bucketSize); i++) {
		   System.out.println("key at: " + i + " is: " + this.keys[i]);
		}
		if (this.overflowBucket != null) {
		  System.out.println("printing overflow---");
		  this.overflowBucket.printBucket(bucketSize);
		}
	}

	
} // HaskBucket class
