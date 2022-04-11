package bst;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Random;
	/**
	 * This Class instantiates every class needed for our measurements.
	 * @author giannis lam
	 *
	 */
public class Console {
	//The instance of the array implementation of the BST.
	private bstArrayImplementation bstArray;
	//The instance of the array implementation of the BST.
	private BstDynamicImplementation bstDynamic;
	//The instance of the sorted array implementation.
	private BinarySearch bsearch;
	//How many integers are in the file. 
	private final int N=100;
	//This array will be used to instantiate the BinarySearch class.
	private static int a[];
	
	/**
	 * Empty constructor to instantiate this class
	 */
	public Console() {
		bstArray=new bstArrayImplementation();
		bstDynamic=new BstDynamicImplementation();
		bsearch=new BinarySearch();
		a=new int[N];
	}
	/**
	 * Returns the array to be used for the instantiation of the BinarySearch
	 * class
	 * @return
	 */
	public bstArrayImplementation getBstArray() {
		return bstArray;
	}
	
	
	/**
	 * Sets the array to be used for the instantiation of the BinarySearch class
	 * @param bstArray
	 */
	public void setBstArray(bstArrayImplementation bstArray) {
		this.bstArray = bstArray;
	}
	
	/**
	 * This class uses MappedByteBuffer to quickly get the numbers needed for our tests.
	 * The max size of the file to be read is 2gb
	 */
	
	public static void insertFromFile() {
		try {
			//Creates a file channel so the file can be read.
			FileChannel channel = new FileInputStream("testnumbers_100_BE.bin").getChannel();
			//a byte buffer to read easily the bytes in a binary file.
			MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
			channel.position(0);
			int i = 0;
			//read every number of the file and stores it in an array.
			while (i < buffer.capacity() - 1) {
				int temp = buffer.getInt();
				a[i/4]=temp;
				i=buffer.position();
			}
			channel.close();

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
	}
	
	//our main method.
	public static void main(String[] args) {
		Console con=new Console();
		//gets an array with integers from the file.
		insertFromFile();
		//measures the time needed for the insert method of a BST implemented dynamically.
		con.bstArray.inorder();
		int temp[]=con.bstArray.getArray();
		con.bsearch.setArray(temp);
		Arrays.sort(con.bsearch.getArray());
		long start=System.nanoTime();
		con.bstDynamic.insertFromFile(a);
		long end=System.nanoTime();
		System.out.println("Dynamic Implem: The time for it took to insert "+con.bstArray.getN()+" numbers was: "+(end-start)+" ns");
		//measures the time needed for the insert method of a BST implemented dynamically.
		start=System.nanoTime();
		con.bstArray.insertFromFile(a);
		end=System.nanoTime();
		System.out.println("Array Implem: The time for it took to insert "+con.bstArray.getN()+" numbers was: "+(end-start)+" ns");
		//calculates the time search method needs for every implementation.
		con.bstArray.randomSearchCounter();
		con.bstDynamic.randomSearchCounter();
		con.bsearch.randomSearchCounter();
		con.bstArray.randomRangeCounter();		
		con.bstDynamic.randomRangeCounter();
		con.bsearch.randomRangeCounter();
		
	}
	
}
