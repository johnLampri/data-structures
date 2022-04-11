package bst;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

/**
 * This class is used to binary search an array and also print every value of the array in a specific range, using the 
 * binary search algorithm.
 * @author giannis lam
 *
 */

class BinarySearch { 
    
	
	//How many integers are in the file. 
	private final int N=100;
	// counts the number of times a conditional statement has happened.
	private int counting;
	//Stores the array that will be searched.
	private int array[];
	
	
	/**
	 * The default constructor oF the Binary Search Class
	 */
	public BinarySearch(){
		counting=0;
		this.array=new int[N];
	}
	
	/**This method returns the number of times a conditional statement has happened. 
	 * 
	 * @return Returns the number stored.
	 */
	
    public int getCounting() {
		return counting;
	}
    /**
     * sets the value of counting.
     * @param counting the value to be set.
     */
	public void setCounting(int counting) {
		this.counting = counting;
	}
	/**
	 * A basic binarry search algorithm in an array.
	 * @param x the number to be searched.
	 * @return returns the element that was searched.
	 */
	
	public int binarySearch(int x){ 
        int l = 0, r = array.length - 1; 
        while (l <= r && ++counting>0) { 
            int m = l + (r - l) / 2; 
  
            // Check if x is present at mid 
            if (array[m] == x && ++counting>0) 
                return m; 
              // If x greater, ignore left half 
            if (array[m] < x && ++counting>0) 
                l = m + 1; 
  
            // If x is smaller, ignore right half 
            else
                r = m - 1; 
        } 
  
        // if we reach here, then element was 
        // not present 
        return -1; 
    } 
	
	/* Find index of first occurrence of least element  
	   greater than key in array 
	 * Returns: an index in range [0, n-1] if key is not 
	             the greatest element in array, 
	 *          -1 if key is the greatest element in array */
	int leastgreater(int[] a,int low, int high, int key) { 
	    int ans = -1; 
	  
	    while (low <= high && ++counting>0) { 
	        int mid = low + (high - low + 1) / 2; 
	        int midVal = a[mid]; 
	  
	        if (midVal < key && ++counting>0) { 
	  
	            // if mid is less than key, all elements 
	            // in range [low, mid - 1] are <= key 
	            // then we search in right side of mid 
	            // so we now search in [mid + 1, high] 
	            low = mid + 1; 
	        } 
	        else if (midVal > key && ++counting>0) { 
	  
	            // if mid is greater than key, all elements 
	            // in range [mid + 1, high] are >= key 
	            // we note down the last found index, then  
	            // we search in left side of mid 
	            // so we now search in [low, mid - 1] 
	            ans = mid; 
	            high = mid - 1; 
	        } 
	        else if (midVal == key) { 
	  
	            // if mid is equal to key, all elements in 
	            // range [low, mid] are <= key 
	            // so we now search in [mid + 1, high] 
	            low = mid + 1; 
	        } 
	    } 
	  
	    return ans; 
	} 
	
	/**
	 *  Find index of last occurrence of greatest element 
	   less than key in array using the Binary Search algorithm.
	 * Returns: an index in range [0, n-1] if key is not  
	             the least element in array, 
	 *          -1 if key is the least element in array
	 * @param a The array to be searched
	 * @param low 
	 * @param high
	 * @param key The number to be searched.
	 * @return the least 
	 */
	int greatestlesser(int a[],int low, int high, int key) 
	{ 
	    int ans = -1; 
	  
	    while (low <= high && ++counting>0) { 
	        int mid = low + (high - low + 1) / 2; 
	        int midVal = a[mid]; 
	  
	        if (midVal < key && ++counting>0) { 
	  
	            // if mid is less than key, all elements  
	            // in range [low, mid - 1] are < key 
	            // we note down the last found index, then  
	            // we search in right side of mid 
	            // so we now search in [mid + 1, high] 
	            ans = mid; 
	            low = mid + 1; 
	        } 
	        else if (midVal > key && ++counting>0) { 
	  
	            // if mid is greater than key, all elements 
	            // in range [mid + 1, high] are > key 
	            // then we search in left side of mid 
	            // so we now search in [low, mid - 1] 
	            high = mid - 1; 
	        } 
	        else if (midVal == key) { 
	  
	            // if mid is equal to key, all elements  
	            // in range [mid + 1, high] are >= key 
	            // then we search in left side of mid 
	            // so we now search in [low, mid - 1] 
	            high = mid - 1; 
	        } 
	    } 
	  
	    return ans; 
	} 
	/**
	 * This method prints every number that is between key1 and key2.
	 * @param key1 The least possible value to be printed.
	 * @param key2 The maximum value to be printed.
	 */
	public void binarySearchRange(int key1,int key2) {
		
		int temp1= greatestlesser(array, 0, array.length-1, key1);
			if(temp1==-1 && ++counting>0) {
				//System.out.println("The number was out of range.");
				return;
			}
		int temp2= leastgreater(array, 0, array.length-1, key2);
			if(temp2==-1 && ++counting>0) {
				
				return;
				}
		//System.out.println("The numbers in the range of "+key1+" and "+key2+"were: ");
		for(int i=temp1;i<temp2;i++) {
			++counting;
			//System.out.print(array[i]+" ");
		}
			//System.out.println();
	}
	/**
	 * Gets the sorted array.
	 * @return Returns the sorted array.
	 */
public int[] getArray() {
		return array;
	}
	/**
	 * Sets a sorted array.
	 * @param array The array to be set. 
	 */
	public void setArray(int[] array) {
		this.array = array;
	}

	/**
	 * Returns how many integers are stored in the file.
	 * @return Returns the number stored.
	 */
	
	public int getN() {
		return N;
	}
	
	/**
	 * This method is used to get how much time it takes for 100 random searches,
	 * in a sorted array using the Binary Search algorithm.
	 */
	public void randomSearchCounter() {
		setCounting(0);
		Random randNumber = new Random();
		long start = System.nanoTime();
		for (int i = 0; i < 100; i++) {
			binarySearch(randNumber.nextInt());
		}
		long end = System.nanoTime();
		System.out.println("Binary search: The time it took to search " + 100 + " random numbers was: " + (end - start) + " ns");
		System.out.println("Total if's: " + getCounting() / 100);

	}
	
	/** This method first searches 100 random numbers in the range of 100 and then searches again with a range of 1000. 
	 * 
	 */
	

public void randomRangeCounter() {
	setCounting(0);
	Random randNumber=new Random();
	//searches 100 random numbers.
	long start=System.nanoTime();
	for(int i=0; i<100;i++) {
		int temp=randNumber.nextInt();
		binarySearchRange(temp,temp+100);
	}
	long end=System.nanoTime();
	//prints the total time it took to search the numbers.
	System.out.println("Bin Search:The time it took for the range method for the range of "+100+ " for 100 random numbers was: "+(end-start)+" ns");
	System.out.println("Total if's: "+getCounting()/100);

	
	setCounting(0);
	start=System.nanoTime();
	//searches 100 random numbers.
	for(int i=0; i<100;i++) {
		int temp=randNumber.nextInt();
		binarySearchRange(temp,temp+1000);
	}
	end=System.nanoTime();
	//prints the total time it took to search the numbers.
	System.out.println("Bin Search: The time it took for the range method for the range of "+1000+ " for 100 random numbers was: "+(end-start)+" ns");
	System.out.println("Total if's: "+getCounting()/100);
	
}	
} 