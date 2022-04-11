package bst;

/**
 * This interface has the standard methods for the implementation of a Binary Search Tree and the method range
 * Comment: These implementations of the binary search tree are not programmed to handle the same number twice.
 * @author giannis lam
 *
 */
public interface BinarySearchTree {

	/**
	 * This method is used to insert a single integer in our Binary Search Tree.
	 * @param search The integer to be inserted.
	 */
	public void insert(int search);

	/**
	 * This method is used to search a specific integer in our tree. If the number is not found, an appropriate message is printed.
	 * @param k The integer to be searched.
	 */
	
	public void search(int k);
	
	/**
	 * prints all the values stored in the Binary Search Tree in a sorted manner(out tree handles integers so the comparison is based on their values).
	 */
	
	public void inorder();
	
	/**
	 * prints all the values stored in the Binary Search Tree that are between the range of k1 and k2.
	 *@param k1  the least possible value that can be printed.
	 *@param k2  the maximum possible value that can be printed.
	 */
	public void range(int k1, int k2);
}
