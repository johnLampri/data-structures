package linearHashing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


/**
 * This class realizes the dynamically a Binary Search Tree.
 * @author Ioannis Lamprinidis
 *
 */

public class BstDynamicImplementation implements BinarySearchTree {
	//the root of the BST.
	Node root;
	// counts the number of times a conditional statement has happened.
	private int counting;
	//Stores how many integers will be read from the file.
	final int N = 10000;

	
	/**The default constructor of the Dynamic Implementation of The Binary Search Tree.
	 * 
	 */
	
	public BstDynamicImplementation() {
		root = null;
	}
	
	@Override
	public void insert(int search) {
		root = insert(root, search);
		counting=counting+1;
	}
	/**The implementation of the insert method for the Dynamic Implementation of the BST
	 * 
	 * @param root The node to be checked.
	 * @param key The number to be inserted.
	 * @return
	 */
	
	public Node insert(Node root, int key) {
		// if the root is null, create a new node and return it
		if (root == null & ++counting > 0) {
			return new Node(key);
		}

		// if given key is less than the root node,
		// recur for left subtree
		if (key < root.getData() & ++counting > 0) {
			root.setLeft(insert(root.getLeft(), key));
			counting=counting+1;
		}

		// else recur for right subtree
		else {
			// key >= root.data
			root.setRight(insert(root.getRight(), key));
			counting=counting+1;
		}

		return root;
	}

	@Override
	public void search(int k) {
		// TODO Auto-generated method stub
		Node temp = search(root, k);
		counting=counting+1;
		/*I commented this part of the code, because i didn't want  
		 * if(temp==null){
		 * System.out.println("The number was not found.");
		 * }else{
		 * System.out.println("the number was found.");
		 * }
		 */

	}
	/**
	 * The implementation of the search method for the Dynamic Implementation.8
	 * @param root the node to be searched.
	 * @param key The integer that is being searched in the Binary Search tree.
	 * @return returns the node that is being searched.
	 */
	
	
	public Node search(Node root, int key) {
		// Base Cases: root is null or key is present at root
		if ((root == null & ++counting > 0) || (root.getData() == key & ++counting > 0))
			return root;

		// val is greater than root's key
		if (root.getData() > key & ++counting > 0)
			return search(root.getLeft(), key);

		// val is less than root's key
		return search(root.getRight(), key);
	}

	@Override
	public void inorder() {
		inorder(root);
	}
	/**This method prints all the values of the BST in an ordered fashion. 
	 * @param root The node to start the inorder method(usually the root). 
	 */
	public static void inorder(Node root) {
		if (root == null) {
			return;
		}
		//Enters the left Subtree;
		inorder(root.getLeft());
		// Prints the data of the Node.
		//System.out.println(root.getData() + " ");
		//Enters the right Subtree.
		inorder(root.getRight());
	}

	@Override
	public void range(int k1, int k2) {
		range(root, k1, k2);
	}

	/**
	 * The range implementation method for the Dynamic Implementation of the Binary Search Tree.
	 * @param node The node to be checked.
	 * @param k1 The least possible value that can be printed.
	 * @param k2 the maximum possible value that can be printed.
	 */
	
	void range(Node node, int k1, int k2) {

		/* base case */
		if (node == null & ++counting>0) {
			return;
		}
		/*
		 * Since the desired o/p is sorted, recurse for left subtree first If root->data
		 * is greater than k1, then only we can get o/p keys in left subtree
		 */
		if (k1 < node.getData() & ++counting>0) {
			range(node.getLeft(), k1, k2);
		}
		/* if root's data lies in range, then prints root's data */
		if ((k1 <= node.getData() & ++counting>0 )&& (k2 >= node.getData() & ++counting>0)) {
			//System.out.print(node.getData() + " ");
		}
		/*
		 * If root->data is smaller than k2, then only we can get o/p keys in right
		 * subtree
		 */
		if (k2 > node.getData() & ++counting>0) {
			range(node.getRight(), k1, k2);
		}
	}
	
	/**
	 * This method creates 100 random numbers,searches them in the binary search tree and prints the time it took.
	 */
	
	public void randomSearchCounter() {
		counting = 0;
		Random randNumber = new Random();
		//gets the current time in nanoseconds.
		long start = System.nanoTime();
		//searches 100 random numbers in the BST.
		for (int i = 0; i < 100; i++) {
			search(randNumber.nextInt());
		}
		long end = System.nanoTime();
		System.out.println("Dynamic Implem: The time it took to search " + 100 + " random numbers was: " + (end - start) + " ns");
		System.out.println("Total if's: " + counting / 100);
	}
	
	/** This method first searches 100 random numbers in the range of 100 and then searches again with a range of 1000. 
	 * 
	 */
	
	public void randomRangeCounter() {
		counting=0;
		Random randNumber = new Random();
		long start = System.nanoTime();
		//searches 100 random numbers.
		for (int i = 0; i < 100; i++) {
			int temp = randNumber.nextInt();
			range(temp, temp + 100);
		}
		long end = System.nanoTime();
		//prints the total time it took to search the numbers.
		System.out.println("Dynamic Implem: The time it took for the range method for the range of " + 100 + " for 100 random numbers was: " + (end - start) + " ns");
		System.out.println("Total if's: " + counting / 100);

		counting=0;
		start = System.nanoTime();
		//searches 100 random numbers.
		for (int i = 0; i < 100; i++) {
			int temp = randNumber.nextInt();
			range(temp, temp + 1000);
		}
		end = System.nanoTime();
		//prints the total time it took to search the numbers.
		System.out.println("Dynamic Implem: The time it took for the range method for the range of " + 1000 + " for 100 random numbers was: " + (end - start) + " ns");
		System.out.println("Total if's: " + counting / 100);
		
	}
	/**
	 * Inserts an array of integers to the Binary search Tree.
	 * @param a the array of integers to be inserted.
	 */
	
	public void insertFromFile(int a[]) {
		int i = 0;
		counting = 0;
		while (i < a.length & ++counting > 0) {
			insert(a[i]);
			i++;
		}
		System.out.println("Total if's: " + counting / N);
}

	void deleteKey(int key) 
    { 
        root = deleteRec(root, key); 
        counting=counting+1;
    } 
  
    /* A recursive function to insert a new key in BST */
    Node deleteRec(Node root, int key) { 
        /* Base Case: If the tree is empty */
        if (root == null & ++counting > 0)  return root; 
  
        /* Otherwise, recur down the tree */
        if (key < root.getData() & ++counting > 0) { 
            root.setLeft(deleteRec(root.getLeft(), key)); 
        counting=counting+1;
        }
        else if (key > root.getData() & ++counting > 0) { 
            root.setRight(deleteRec(root.getRight(), key)); 
            counting=counting+1;
        }
        // if key is same as root's key, then This is the node 
        // to be deleted 
        else
        { 
            // node with only one child or no child 
            if (root.getLeft() == null & ++counting > 0) 
                return root.getRight(); 
            else if (root.getRight() == null & ++counting > 0) 
                return root.getLeft(); 
  
            // node with two children: Get the inorder successor (smallest 
            // in the right subtree) 
            root.setData(minValue(root.getRight())); 
  
            // Delete the inorder successor 
            root.setRight(deleteRec(root.getRight(), root.getData())); 
        } 
  
        return root; 
    } 
	
    int minValue(Node root) 
    { 
        int minv = root.getData(); 
        while (root.getLeft() != null & ++counting > 0) 
        { 
            minv = root.getLeft().getData(); 
            counting=counting+1;
            root = root.getLeft();
            counting=counting+1;
        } 
        return minv; 
    } 
	
    public float[][] insertToTable(int[] a,int[] b,int[] c){ // The name was intended to be in(t)TheTable but the compiler was not accepted.
		float[][] testingValues= new float [3][N/100];
		
		for(int i=0; i<N;i=i+100) {
			this.counting=0;
			for(int j=i;j<i+100;j++) {
				this.insert(a[j]);
			}
			testingValues[0][i/100]=this.counting/100;
			this.counting=0;
			
			for(int k=i/2; k<i/2+50 ;k++) {
			this.search(b[k]);
			}
			testingValues[1][i/100]=this.counting/50;
			this.counting=0;
			for(int k=0; k<50;k++) {
				this.deleteKey(c[k]);
				}
			testingValues[2][i/100]=this.counting/50;
		}	
		//System.out.print("gg");
		return testingValues;
	}
}
