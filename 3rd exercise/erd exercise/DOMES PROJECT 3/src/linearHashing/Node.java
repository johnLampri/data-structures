package linearHashing;
/**
 * This class is used to create a binary search tree dynamically.
 * @author Ioannis Lamprinidis
 *
 */
public class Node {
	//The data that is stored in each element of the array.
	private int data;
	//The instances of the left and right subtree.
	private Node left, right;

	/**
	 *  Constructor to create a new binary tree node having given key
	 * @param key
	 */
	Node(int key)
	{
		data = key;
		left = right = null;
	}
	
	/**
	 * Returns the stored integer.
	 * @return Returns the integer stored in the node.
	 */
	public int getData() {
		return data;
	}

	/**
	 * Sets the data stored on the Node.
	 * @param data The data to be stored.
	 */
	
	public void setData(int data) {
		this.data = data;
	}

	/**
	 * Returns the left subtree.
	 * @return the root of the left subtree.
	 */
	
	public Node getLeft() {
		return left;
	}

	/**
	 * Sets the left Node.
	 * @param left the Node to be set.
	 */
	public void setLeft(Node left) {
		this.left = left;
	}

	/**
	 * Returns the right subtree.
	 * @return the root of the right subtree.
	 */
	
	public Node getRight() {
		return right;
	}

	/**
	 * Sets the right Node.
	 * @param right the right Node to be set.
	 */
	public void setRight(Node right) {
		this.right = right;
	}
	
	
}
