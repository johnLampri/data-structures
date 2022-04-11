package ddl;






/**
 * A simple Double Linked List with it's basics methods
 * @author Ioannis Lamprinidis
 *
 */


public class DoubleLinkedList {
	private Node head;
	
	/**
	 * The default constructor of the Double Linked List
	 */
	
	public DoubleLinkedList() {
		this.head=null;
		
	}
	/**
	 * this method inserts a new node(with data as it's content) before another node of the double linked list
	 * @param current is the node that is used as our reference point
	 * @param data is the context of the new node that will be inserted
	 */
	public void insertAtBack(Node current,String data ) {
		//checks if the there are nodes in the list
		if(head!= null) {
		if (current==null) {
			System.out.println("The current Node cannot be null");
			return;
		}
		
		Node temp=new Node(data,current,current.getPrev());
		//checks if the current node is at the start of the list
		if((current.getPrev()) != null) {
			current.getPrev().setNext(temp);
		}else {
				head=temp;
			}
		current.setPrev(temp);
		}else {
			Node temp=new Node(data,null,null);
			this.head=temp;
	}
	}
	/**
	 * this method inserts a new node after another node of the double linked list
	 * @param current is the node of the double linked list that we use as a reference point
	 * @param data is the context of the new node that will be inserted
	 */
	public void insertAfter(Node current,String data) {
		//checks if the list is empty
		if(head !=null) {
		if (current==null) {
			System.out.println("The current Node cannot be null");
			return;
		}
		Node temp=new Node(data,current.getNext(),current);
		current.setNext(temp);
		//checks if the node is at the end of the list
		if(temp.getNext() !=null)
			temp.getNext().setPrev(temp);
		
	}else {
		Node temp=new Node(data,null,null);
		this.head=temp;
	}
	}
	/**
	 * Is used to fill the double linked list
	 * @param current the node which is used as a reference point
	 * @param data used for the creation of the new node that will be inserted
	 * @return the inserted node
	 */
	public Node insert(Node current,String data) {
		Node temp=new Node(data,null,current);
		if(this.head!=null) {
		current.setNext(temp);		
		
		}else {
			this.head=temp;
		}
		return temp;
	}
	/**
	 * prints the word that is stored on the node
	 * @param node the node which context will be printed
	 */
	public void print(Node node) {
		if(node!=null) {
		System.out.println(node.getData());
		}else {
			System.out.println("The node is null. Check the path you entered.");
		}
		
		return;
	}
	/**
	 * Prints the word stored in each node in the double linked list
	 * @param node the node where the printing will start
	 * @param boo is used to check if we want to print the number of the line which the string is found 
	 * @param line stores from which line the printing will start
	 */
	
	public void printlist(Node node, boolean boo, int line) {
		while(node!=null) {
			if(boo) {
				System.out.print(line+")");
				line=line+1;
			}
			this.print(node);
			node=node.getNext();
		}
	}
	/**
	 * deletes a node of the double linked list
	 * @param node the node to be deleted
	 * @return returns the next position of the double linked list
	 */
	public Node delete(Node node) {
		//checks if the node to be deleted is null
		if(node== null) {
			 System.out.println("There is not a node to be deleted");
			 return null;
		 }else if(node.getPrev()==null) {
			 //if the node to be deleted is the head of the list,the next node becomes
			 //becomes the head
			this.head=node.getNext();
			if(this.head!=null)
			this.head.setPrev(null);
			node.setNext(null);
			return this.head;
		 }else if(node.getNext()==null){
			 //checks if the node to be deleted is at the end of the list and returns the previous item of the list as the current 
			 //position
			 node.getPrev().setNext(null);
			 Node temp=node.getPrev();
			 node.setPrev(null);
			 return temp;
		 }else{ 
			Node temp=node.getNext();
			node.getNext().setPrev(node.getPrev());
			node.getPrev().setNext(node.getNext());
			node.setNext(null);
			node.setPrev(null); 
			//the current position will be the next item of the list
			return temp;
		 }
	}
	/**
	 * This method prints the total lines and characters of the file
	 * @param node the node from which the method will start(typically the head of the double linked list) 
	 */
	public void printTotalLinesAndChars(Node node) {
		int line=0;
		int numOfChars=0;
		while(node!=null) {
			//gets the length of the string in the node and increases the number of total lines
			numOfChars=numOfChars+node.getData().length();
			line=line+1;
			node=node.getNext();
		}
		System.out.println("The total number of lines is: "+line+" and the total number of characters is: "+numOfChars);
	}
	
	/**
	 * returns the head of the double linked list
	 * @return the head
	 */
	public Node getHead() {
		return this.head;
	}
	/**
	 * sets a node as the head of the double linked list
	 * @param node the new head of the double linked list
	 */
	public void setHead(Node node) {
		this.head=node;
	}
}
