package linearHashing;
import java.io.*;
import java.nio.BufferUnderflowException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
/**
 * 
 * This class is used to implement a linear Hashing Table.
 * @author giannis lam
 *
 */

class LinearHashing {

	private HashBucket[] hashBuckets;	// pointer to the hash buckets
	final private int  N=10000; 
	private float maxThreshold;		// max load factor threshold
	private float minThreshold;		// min load factor threshold

	
	public int  counting;			//	This variable is used to count how many comparisons and 
									// 	changes to the values of the variables are made. 
	private int bucketSize;			// max number of keys in each bucket
	private int keysNum;			// number of keys currently stored in the table
	private int keySpace;			// total space the hash table has for keys
	private int p;					// pointer to the next bucket to be split
	private int n;					// current number of buckets
	private int j;					// the n used for the hash function
	private int minBuckets;			// minimum number of buckets this hash table can have

	/**
	 * 
	 * The constructor of the class. 
	 * 
	 * @param itsBucketSize 		//The value to be set on the variable bucketSize.
	 * @param initPages				//The starting pages of the Linear Hashing instance. 
	 * @param maxTheshold			//The maximum load factor of the Linear Hashing Table.
	 */
	
	
	/**
	 * The default constructor of the linear Hashing Class.
	 * @param itsBucketSize max number of keys in each bucket.
	 * @param initPages The initial pages.
	 * @param maxTheshold  Max load factor threshold.
	 */
	
	
	public LinearHashing(int itsBucketSize, int initPages,float maxTheshold) { 	// Constructor.

		int i;

		bucketSize = itsBucketSize; 
		keysNum = 0;
		p = 0;
		n = initPages;
		j = initPages;
		minBuckets = initPages;
		keySpace = n*bucketSize;
		this.maxThreshold = maxTheshold;
		minThreshold = (float)0.5;

		if ((bucketSize == 0) || (n == 0)) {
		  System.out.println("error: space for the table cannot be 0");
		  System.exit(1);
		}
		hashBuckets = new HashBucket[n];
		for (i = 0; i < n; i++) {
		   hashBuckets[i] = new HashBucket(bucketSize);
	}
}
	
	/**
	 * This method is used to increase the counter.
	 * @return returns the counting variable.
	 */
	
	public int counting() {
		this.counting=counting+1;
		return this.counting;
	}
	
	/**
	 * This method is used to initialize the counter to 0.
	 * 
	 */
	
	public void initialiseCounting() {
		this.counting=0;
	}
	
	/**
	 * This method is used to return the N value.
	 * @return the N variable.
	 */
	
	public int getN(){
		return N;
	}
	
	/**
	 * This method returns the position of the linear hashing table for the corresponding key .
	 * @param key the key that it's position is required.
	 * @return returns the position of the key.
	 */
	
	private int hashFunction(int key){	//

		int retval;

		retval = key%this.j;
		counting();
		if (retval < 0 & counting()>0) {
			retval *= -1;
			counting();
		}
		if (retval >= p & counting()>0){
		  //System.out.println( "Retval = " + retval);
		  return retval;
		}
		else {
			 retval = key%(2*this.j);
			 counting();
			 if (retval < 0 & counting()>0) {
				 retval *= -1;
				 counting();
			 }
			 //System.out.println( "Retval = " + retval);
		         return retval;
		}
	}

	/**
	 * Returns the current load factor of the hash table.
	 * @return the load factor
	 */
	
	private float loadFactor() {

		return ((float)this.keysNum)/((float)this.keySpace);
	}
	
	/**
	 * Splits the bucket pointed by p.
	 */

	private void bucketSplit() {

		int i;
		HashBucket[] newHashBuckets;

		newHashBuckets= new HashBucket[n+1];
		for (i = 0; i < this.n & counting()>0; i++){
		   newHashBuckets[i] = this.hashBuckets[i];
		   counting();
		}

		hashBuckets = newHashBuckets;
		counting();
		hashBuckets[this.n] = new HashBucket(this.bucketSize);
		counting();
		this.keySpace += this.bucketSize;
		counting();
		this.hashBuckets[this.p].splitBucket(this, 2*this.j, this.p, hashBuckets[this.n]);
		this.n++;
		counting();
		if (this.n == 2*this.j & counting()>0) {
		  this.j = 2*this.j;
		  counting();
		  this.p = 0;
		  counting();
		}
		else {
		    this.p++;
		    counting();
		}
	}

	
	/**
	 * Merges the last bucket that was split.
	 */
	
	private void bucketMerge() {

		int i;

		HashBucket[] newHashBuckets;
		newHashBuckets= new HashBucket[n-1];
		counting();
		for (i = 0; i < this.n-1  & counting()>0; i++) {
		   newHashBuckets[i] = this.hashBuckets[i];
		   counting();
		}
		if (this.p == 0  & counting()>0) {
		  this.j = (this.n)/2;
		  counting();
		  this.p = this.j-1;
		  counting();
		}
		else {
		  this.p--;
		  counting();
		}
		this.n--;
		counting();
		this.keySpace -= this.bucketSize;
		counting();
		this.hashBuckets[this.p].mergeBucket(this, hashBuckets[this.n]);
		hashBuckets = newHashBuckets;
		counting();
	}

	/**
	 * This method returns the current bucket size. 
	 * @return The bucket size.
	 */
	public int getBucketSize() {return bucketSize;}
	
	/**
	 * This method returns the number of keys stored in the Linear Hashing table. 
	 * @return keysNum
	 */
	public int getKeysNum() {return keysNum;}
	
	/**
	 * This method returns the total space the hash table has for keys. 
	 * @return keysNum
	 */
	public int getKeySpace() {return keySpace;}
	
	/**
	 * This method sets the space of the HashBucket.
	 * @param size the size to be set.
	 */
	
	public void setBucketSize(int size) {bucketSize = size;counting();}
	
	/**
	 * This method sets the number of keys currently stored in the Linear Hashing table.
	 * @param keysNum the number of keys to be set.
	 */
	
	public void setKeysNum(int num) {keysNum = num;counting();}
	
	/**
	 * This method sets the maximum number of keys stored in the table.
	 * @param space the number of keys to be set.
	 */
	
	public void setKeySpace(int space) {keySpace = space;counting();}

	
	/**
	 *  This method inserts a new key.
	 * @param key the number to be inserted.
	 */
	public void insertKey(int key) {

		//System.out.println( "hashBuckets[" + this.hashFunction(key) + "] =  " + key);
		this.hashBuckets[this.hashFunction(key)].insertKey(key, this);
		if (this.loadFactor() > maxThreshold & counting()>0){
		  //System.out.println("loadFactor = " + this.loadFactor() );
		  this.bucketSplit();
		  //System.out.println("BucketSplit++++++");
		}
	}

	/**
	 * This method deletes a key.
	 * @param key the key to be deleted.
	 */
	public void deleteKey(int key) {	

		this.hashBuckets[this.hashFunction(key)].deleteKey(key, this);
		if (this.loadFactor() > maxThreshold & counting()>0){
		  this.bucketSplit();
		}
		else if ((this.loadFactor() < minThreshold & counting()>0) && (this.n > this.minBuckets & counting()>0)){
			 this.bucketMerge();
		}
	}

	/**
	 * This method returns true, if the number is found in the hash table. 
	 * @param key the number to be searched.
	 * @return true if the key is found, false otherwise.
	 */
	
	public boolean searchKey(int key) {		// Search for a key.

		return this.hashBuckets[this.hashFunction(key)].searchKey(key, this);
	}

	//public void printHash() {

	//	int i;

		//for (i = 0; i < this.n; i++) {
		//   System.out.println("Bucket[" + i + "]");
		//   this.hashBuckets[i].printBucket(this.bucketSize);
	//	}
	//}

	
	/**
	 * This class uses MappedByteBuffer to quickly get the numbers needed for our tests.
	 * The max size of the file to be read is 2gb
	 */
	
	
	public int[] insertFromFile() {
		try {
			//Creates a file channel so the file can be read.
			FileChannel channel = new FileInputStream("testnumbers_10000_BE.bin").getChannel();
			//a byte buffer to read easily the bytes in a binary file.
			MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
			channel.position(0);
			int i = 0;
			int[] a= new int[N];
			//read every number of the file and stores it in an array.
			while (i < buffer.capacity() - 1) {
				int temp = buffer.getInt();
				a[i/4]=temp;
				i=buffer.position();
			}
			
			channel.close();
			return a;
		} catch (BufferUnderflowException e) {
			System.out.println("All the numbers have been read.");

			// when finished
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int[] a= new int[1];
		return a;
	}
	
	/**
	 * 
	 * This method is used to insert the numbers from the file to the table, search numbers from the table 
	 * and delete some numbers.
	 * @param a this array has the numbers to be inserted in the table.
	 * @param b this array stores the numbers to be searched from the table.
	 * @param c this array stores the numbers to be deleted from the table.
	 * @return returns an array with the average number of operations
	 */
	
	public float[][] insertToTable(int[] a,int[] b,int[] c){
		float[][] testingValues= new float [3][N/100];
		
		for(int i=0; i<N;i=i+100) {
			this.initialiseCounting();
			for(int j=i;j<i+100;j++) {
				this.insertKey(a[j]);
			}
			testingValues[0][i/100]=this.counting/100;
			this.initialiseCounting();
			
			for(int k=i/2; k<i/2+50 ;k++) {
			this.searchKey(b[k]);
			}
			testingValues[1][i/100]=this.counting/50;
			this.initialiseCounting();
			for(int k=i/2; k<i/2+50 ;k++) {
				this.deleteKey(c[k]);
				}
			testingValues[2][i/100]=this.counting/50;
			
		}
		return testingValues;
	}
	
	
	
	

} // LinearHashing class
