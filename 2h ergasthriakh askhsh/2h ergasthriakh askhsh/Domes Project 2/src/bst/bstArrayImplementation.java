package bst;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.util.Random;
import java.util.Vector;

/** 
 * 
 * 
 * 
 * @author giannis lam
 *
 */


public class bstArrayImplementation implements BinarySearchTree {

	//How many integers are in the file. 
	final int N = 100;
	//This array contains the value of each element of the tree.
	private int info[];
	//This array contains the left subtree of the current element.
	private int left[];
	//This array contains the right subtree of the current element.
	private int right[];
	//The root of the BST.
	private int root;
	//The line where the next element will be inserted.
	private int avail;
	// counts the number of times a conditional statement has happened. 
	private int counting; 
	//is used for the initialization of the binary search array.
	private int[] array;
	//is used for the initialization of the binary search array.
	public int arraycounter;
	
	/**
	 * The empty constructor of the array Implementation of the Binary Search Tree.
	 */
	
	public bstArrayImplementation() {
		info = new int[N];
		left = new int[N];
		right = new int[N];
		root = -1;
		avail = 0;
		counting = 0;
		arraycounter=0;
		array=new int[N];
		//initializes the available array 
		for (int i = 0; i < N; i++)
			right[i] = i + 1;
		//initializes the left array
		for (int i = 0; i < N; i++)
			left[i] = -1;
	}

	/**
	 * This method returns the info array.
	 * @return the info array.
	 */
	public int[] getInfo() {
		return info;
	}
	/**
	 * This method sets the info array
	 * @param info The array to be set.
	 */
	public void setInfo(int[] info) {
		this.info = info;
	}

	/**This method returns the left array.
	 * @return the left array
	 */
	
	public int[] getLeft() {
		return left;
	}
	/**
	 * Sets the left array.
	 * @param left the array to be inserted.
	 */
	public void setLeft(int[] left) {
		this.left = left;
	}
	/**
	 * 	Returns the right array.
	 * @return the right array.
	 */
	public int[] getRight() {
		return right;
	}
	
	/**
	 * This method sets the right array.
	 * @param right the array to be set.
	 */
	
	public void setRight(int[] right) {
		this.right = right;
	}	

	/**
	 * 
	 * @return
	 */
	
	public int getN() {
		return N;
	}
	/**
	 * 
	 * @return
	 */
	public int getRoot() {
		return root;
	}
	/**
	 * 
	 * @param root
	 */
	public void setRoot(int root) {
		this.root = root;
	}
	/**
	 * 
	 * @return
	 */
	public int getAvail() {
		return avail;
	}
	/**
	 * 
	 * @param avail
	 */
	public void setAvail(int avail) {
		this.avail = avail;
	}
	/**
	 *  This method counts the number of times a conditional statement has happened. 
	 * @return the number of times a conditional statement has happened. 
	 */
	public int getCounting() {
		return counting;
	}
	
	/**
	 * Sets the counting variable.
	 * @param counting
	 */
	public void setCounting(int counting) {
		this.counting = counting;
	}

	@Override
	public void insert(int key) {
		arraycounter=0;
		this.root = insertArray(key, this.root);
	}

	/**
	 * This method inserts a number in our binary Search tree implemented with arrays.
	 * @param key the integer to be inserted.
	 * @param line the line to be searched.
	 * @return returns the line that the number is found.
	 */
	
	private int insertArray(int key, int line) {
		
		if ((line == -1) & ++counting > 0) {
			//An empty leaf of the array as been reached and the integer will be inserted.
			//updates the line to an empty position.
			line = avail;
			//gets the next available position.
			avail = this.right[avail];
			info[line] = key;
			right[line] = -1;

			return line;
		}

		if ((key < info[line]) & ++counting > 0)
			//searches the left subtree
			left[line] = insertArray(key, left[line]);
		else {
			//searches the right subtree.
			right[line] = insertArray(key, right[line]);
		}
		return line;
	}

	@Override
	public void search(int key) {
		searchArray(key, root);
	}

	/**
	 * This method searches an integer in the Binary Search Tree implemented with arrays.
	 * @param key The number to be searched.
	 * @param line the line to be searched.
	 * @return returns the line of the number or -1 if the number is not found.
	 * */
	private int searchArray(int key, int line) {
		//The number was not found.
		if (line == -1 & ++counting > 0)
			//System.out.println("The number was not found.");
			return -1;
		//The number is found.
		if (info[line] == key & ++counting > 0)
			return line;
		
		else if (info[line] < key & ++counting > 0)
			//The right subtree is searched.
			return searchArray(key, right[line]);
		else
			//The left subtree is searched.
			return searchArray(key, left[line]);
	}

	@Override
	public void inorder() {
		arraycounter=0;
		inorderArray(root);
		
	}
	
	/**
	 * This method prints all the values stored in the Binary Search tree implemented with arrays in a sorted manner.
	 * @param line The line of the value to be printed.
	 */
	public void inorderArray(int line) {
		if (line == -1)
			return;
		//prints the left subtree.
		inorderArray(left[line]);
		//prints the integer stored in the Binary Search Tree.
		//System.out.println(info[line] + " ");
		//saves the integer to the array so the binary Search class can be initialized.
		array[arraycounter]=info[line];
		arraycounter=arraycounter+1;
		//prints the right subtree.
		inorderArray(right[line]);

	}
	
	/**
	 *	Returns the array for the binary search class initialization 
	 * @return Returns the array.
	 */
	
	public int[] getArray() {
		return array;
	}
	
	@Override
	public void range(int k1, int k2) {
		rangeArray(k1, k2, root);

	}

	/**
	 * Prints every integer in the BST that is in between the range of k1 and k2.
	 * @param k1 the least possible value to be printed.
	 * @param k2 the maximum possible value to be printed.
	 * @param line the current line to be checked.
	 */
	
	private void rangeArray(int k1, int k2, int line) {
		//The search has ended.
		if (line == -1 & ++counting > 0) {
			return;
		}
		//Searches the left subtree.  
		if (k1 < info[line] & ++counting > 0) {
			rangeArray(k1, k2, left[line]);
		}
		if ((k1 <= info[line] & ++counting > 0) && (k2 >= info[line] & ++counting > 0)) {
		//	System.out.print(info[line] + " ");
	}
		if (k2 > info[line] & ++counting > 0) {
			rangeArray(k1, k2, right[line]);
		}
	}
	/**
	 * This method creates 100 random numbers,searches them in the binary search tree and prints the time it took.
	 */
	
	public void randomSearchCounter() {
		setCounting(0);
		Random randNumber = new Random();
		//gets the current time in nanoseconds.
		long start = System.nanoTime();
		for (int i = 0; i < 100; i++) {
			search(randNumber.nextInt());
		}
		long end = System.nanoTime();
		System.out.println("Arwray Implem: The time it took to search " + 100 + " random numbers was: " + (end - start) + " ns");
		System.out.println("Total if's: " + getCounting() / 100);

	}
	/** This method first searches 100 random numbers in the range of 100 and then searches again with a range of 1000. 
	 * 
	 */
	
	public void randomRangeCounter() {
		setCounting(0);
		Random randNumber = new Random();
		long start = System.nanoTime();
		//searches 100 random numbers.
		for (int i = 0; i < 100; i++) {
			int temp = randNumber.nextInt();
			range(temp, temp + 100);
		}
		long end = System.nanoTime();
		//prints the total time it took to search the numbers.
		System.out.println("Array Implem: The time it took for the range method for " + 100 + " random numbers was: " + (end - start) + " ns");
		System.out.println("Total if's: " +(getCounting()) / 100);

		setCounting(0);
		start = System.nanoTime();
		//searches 100 random numbers.
		for (int i = 0; i < 100; i++) {
			int temp = randNumber.nextInt();
			range(temp, temp + 1000);
		}
		end = System.nanoTime();
		//prints the total time it took to search the numbers.
		System.out.println("Array Implem: The time it took for the range method for " + 1000 + " random numbers was: " + (end - start) + " ns");
		System.out.println("Total if's: " + getCounting() / 100);

	}
	
	/**
	 * Inserts an array of integers to the Binary search Tree.
	 * @param a the array of integers to be inserted.
	 */
	
	public void insertFromFile(int a[]) {
			int i = 0;
			counting = 0;
			while (i < a.length && ++counting > 0) {
				insert(a[i]);
				i++;
			}
			System.out.println("Total if's: " + counting / N);
	}
}