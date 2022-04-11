package ddl;

/**
 * Class used for the implementation of a double linked list  
 * 
 * @author Ioannis Lamprinidis
 *
 */

public class Node {
	private String data;
	private Node next;
	private Node prev;	
	final int maxchar=80;
	
	/**
	 * The constructor of the Node class which creates a node
	 * 
	 * @param data is the line to be saved
	 * @param next is the next item on the double linked list
	 * @param prev is the previous item on the double linked list
	 */
	
	public Node(String data, Node next, Node prev) {
		try {
		this.data=data.substring(0, maxchar);
		}catch(IndexOutOfBoundsException e) {
			this.data=data;
		}
		this.next=next;
		this.prev=prev;
	}
	/**The default constructor of the Node class
	 * 
	 * 
	 */
	public Node() {
		this.data="";
		this.next=null;
		this.prev=null;
	}
	/**
	 * returns the string that is saved on this node
	 * @return returns the line
	 */
	
	public String getData() {
		return data;
	}
/**
 * sets the string on this node
 * @param data
 */
	public void setData(String data) {
		this.data = data;
	}
/**
 * returns the next node
 * @return
 */
	public Node getNext() {
		return next;
	}
/**
 * set the next node of the current node
 * @param next
 */
	public void setNext(Node next) {
		this.next = next;
	}

	/**
	 * returns the previous node 
	 * @return
	 */
	public Node getPrev() {
		return prev;
	}
	/**
	 * sets the previous node of the current node
	 * @param prev
	 */
	
	public void setPrev(Node prev) {
		this.prev = prev;
	}
	

	
}
