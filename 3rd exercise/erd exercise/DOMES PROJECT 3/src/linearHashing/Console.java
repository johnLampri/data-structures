package linearHashing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is used to instantiate every class to needed for our tests. 
 * @author giannis lam
 *
 */

public class Console {
	LinearHashing LH1;
	LinearHashing LH2;
	BstDynamicImplementation bst;
	final private static int N = 10000;

	/**
	 * 
	 * @param itsBucketSize
	 * @param initPages
	 */
	
	public Console(int itsBucketSize, int initPages) {
		LH1 = new LinearHashing(itsBucketSize, initPages, (float) 0.8);
		LH2 = new LinearHashing(itsBucketSize, initPages, (float) 0.5);
		bst = new BstDynamicImplementation();
	}
/*
 * THis code was to copy every column more easily and make the diagrams from the data.
	public static void printTest(float a[][],float b[][],float c[][]) {
	//	int temp[] = new int[N / 100];
	//	for (int i = 0; i < N / 100; i++) {

		//temp[i] = i * 100 + 100;
		//	System.out.println(temp[i]);
//
		//}

		//System.out.println();

		for (int i = 0; i < N / 100; i++) {

			System.out.println(a[0][i]);

		}
		System.out.println();
		for (int i = 0; i < N / 100; i++) {
			System.out.println(a[1][i]);

		}
		System.out.println();
		for (int i = 0; i < N / 100; i++) {

			System.out.println(a[2][i]);
		}
		System.out.println();

		for (int i = 0; i < N / 100; i++) {

			System.out.println(b[0][i]);

		}
		System.out.println();
		for (int i = 0; i < N / 100; i++) {
			System.out.println(b[1][i]);

		}
		System.out.println();
		for (int i = 0; i < N / 100; i++) {

			System.out.println(b[2][i]);
		}
		System.out.println();
		for (int i = 0; i < N / 100; i++) {

			System.out.println(c[0][i]);

		}
		System.out.println();
		for (int i = 0; i < N / 100; i++) {
			System.out.println(c[1][i]);

		}
		System.out.println();
		for (int i = 0; i < N / 100; i++) {

			System.out.println(c[2][i]);
		}
	}
**/
	
	/**
	 * this method is used to print ouur data in columns side by side.
	 * @param a An array with the data for the LH with u>80%.
	 * @param b An array with the data for the LH with u>50%.
	 * @param c An array with the data for the BST.
	 */
	
	public static void print(float a[][], float b[][], float c[][]) {

		System.out.println(
				"Input size(N) | LH u>80% avg. comp. per ins. | LH u>80% comp. per search | LH u>80% comp. per deletion | LH u>50% avg. comp. per ins. | LH u>50% comp. per search | LH u>50% comp. per deletion | BST avg. comp. per ins. | BST comp. per search | BST comp. per deletion");
		int temp[] = new int[N / 100];
		for (int i = 0; i < N / 100; i++) {

			temp[i] = i * 100 + 100;
			System.out.printf(
					" %12d | %28.2f | %25.2f | %27.2f | %28.2f | %25.2f | %27.2f | %23.2f | %20.2f | %22.2f \n",
					temp[i], a[0][i], a[1][i], a[2][i], b[0][i], b[1][i], b[2][i], c[0][i], c[1][i], c[2][i]);

		}
	}

	public static void main(String[] args) {
		//Instantiate the variables 
		Console con = new Console(10, 100);
		int insertions[] = con.LH1.insertFromFile();
		ArrayList<Integer> inTheTable = new ArrayList<>();
		int searches[] = new int[N / 2];
		int deletions[] = new int[N / 2];
		//insert The integers to an array.(To be used for the creation of the deletion array.)
		for (int i = 0; i < con.LH1.getN(); i = i + 100) {

			for (int j = i; j < i + 100; j++) {
				inTheTable.add(insertions[j]);
			}
			
			//Generates random numbers picked from the insertions array, to be used for testing the search methods.
			for (int k = i/2; k < i / 2 + 50; k++) {
				int randomNum = ThreadLocalRandom.current().nextInt(0, inTheTable.size() / 2);
				int temp = inTheTable.get(randomNum).intValue();
				searches[k] = temp;
			}
			////Generates random numbers picked from the insertions array, to be used for testing the search methods.
			for (int k = i / 2; k < i / 2 + 50; k++) {
				int randomNum = ThreadLocalRandom.current().nextInt(0, inTheTable.size() / 2);
				int temp = inTheTable.get(randomNum).intValue();
				inTheTable.remove(randomNum);
				deletions[k] = temp;
			}

		}
		//printTest(con.LH1.insertToTable(insertions,searches,deletions),con.LH2.insertToTable(insertions,searches,deletions),con.bst.insertToTable(insertions,searches,deletions));
		print(con.LH1.insertToTable(insertions,searches,deletions),con.LH2.insertToTable(insertions,searches,deletions),con.bst.insertToTable(insertions,searches,deletions));
	}

}
