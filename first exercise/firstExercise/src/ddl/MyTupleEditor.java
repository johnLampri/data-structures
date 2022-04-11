package ddl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
/**
 * This class tokenizes every node of the double linked list, stores it into tuples and stores all the
 * tuples in a binary file 
 * @author Ioannis Lamprinidis
 *
 */
public class MyTupleEditor {
	private ArrayList<Tuple> arrayTuple;
	private final int pagesize = 128;
	private final int minStringSize = 5;
	private final int maxStringSize = 20;
/**
 * The default constructor of this class
 */
	public MyTupleEditor() {
		arrayTuple = new ArrayList<Tuple>();
	}

	/**
	 * returns the array of tuples
	 * @return
	 */
	public ArrayList<Tuple> getArrayTuple() {
		return arrayTuple;
	}
	/**
	 * adds a tuple to arrayTuple
	 * @param Tuple the tuple to be added to the array
	 */
	public void addTuple(Tuple Tuple) {
		this.arrayTuple.add(Tuple);
	}
	
	/**
	 * returns how many bytes will be written every time to the disk
	 * @return
	 */
	public int getPagesize() {
		return pagesize;
	}

	/**
	 * returns the minimum size of a token to be written on the binary file
	 * @return
	 */
	public int getMinStringSize() {
		return minStringSize;
	}
/**
 * returns the maximum size of a token to be written on the binary file
 * @return
 */
	public int getMaxStringSize() {
		return maxStringSize;
	}

	/**
	 * creates an array of tuples from a double linked list
	 * @param head the head of the Double Linked List
	 */
	
	public void createTuplesArray(Node head) {
		Node tmp = head;
		int line_count = 0;
		arrayTuple.clear();
		while (tmp != null) {
			line_count++;
			String[] tokens = tmp.getData().split(",\\s+|\\s*\\\"\\s*|\\s+|\\.\\s*|\\s*\\:\\s*|\\-");
			for (int i = 1; i <= tokens.length; i++) {
				if (tokens[i - 1].length() >= minStringSize) {

					if (tokens[i - 1].length() >= maxStringSize) {
						addTuple(new Tuple(tokens[i - 1].substring(0, 19), line_count));
					} else {
						while (tokens[i - 1].length() < 20) {
							tokens[i - 1] = tokens[i - 1] + ' ';
						}
						addTuple(new Tuple(tokens[i - 1], line_count));
					}
				}
			}
			tmp = tmp.getNext();
		}
		Collections.sort(arrayTuple);
		try {
			writeToDisk(arrayTuple);

		} catch (FileNotFoundException e) {
			System.out.println("The file could not be created.");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("There was a problem with the file.");
			return;
		}
	}
	public void writeToDisk(ArrayList<Tuple> t) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		File file = new File("indexFile.txt.ndx");
		//printWriter is used to clear the file
		PrintWriter pw = new PrintWriter(file);
		pw.close();
		
		RandomAccessFile MyFile = new RandomAccessFile(file, "rw");
		int itemsWritten = 0;
		boolean flag = false;//flag is used to check if we have reached the end of the array
		byte items[] = new byte[this.maxStringSize];
		byte[] buf;
		byte[] pageSize = new byte[pagesize];
		int pagesWritten = 0;
		//move the file pointer at the start of the file
		MyFile.seek(0);
		while (itemsWritten < t.size()) {
			//In this while we get from each item in the tuple array,it's string and number in their ASCII value and write it on our
			//output stream and when there are enough items to fill a page, the output stream is written to the binary file
			items = t.get(itemsWritten).getWord().getBytes();
			dos.write(items, 0, maxStringSize);
			dos.writeInt(t.get(itemsWritten).getLine());
			if ((itemsWritten + 1) == t.size()) {
				flag = true;
			}
			if (((itemsWritten + 1) % (pagesize / (maxStringSize + 4))) == 0 || flag) {
				//checks if we have enough items to fill a page or we have reached the end of the array
				buf = baos.toByteArray();
				System.arraycopy(buf, 0, pageSize, 0, buf.length);

				MyFile.write(pageSize);

				Arrays.fill(buf, (byte) 0);
				Arrays.fill(pageSize, (byte) 0);
				baos.reset();
				pagesWritten = pagesWritten + 1;
			}
			Arrays.fill(items, (byte) 0);
			itemsWritten = itemsWritten + 1;

		}
		System.out.println("Total pages Written: " + pagesWritten);
		MyFile.close();

		dos.close();
		baos.close();

	}
/**
 * reads an array of tuples from a binary file and prints it to the screen
 * @throws IOException
 */
	public void readFromDisk() throws IOException {

		File file = new File("indexFile.txt.ndx");
		RandomAccessFile MyFile = new RandomAccessFile(file, "r");
		byte[] ReadData = new byte[pagesize];
		int times = 0;
		byte[] br = new byte[maxStringSize];
		String temp;
		byte[] flag = new byte[maxStringSize];
		boolean bflag = true;//becomes false when we read in a page 20 consecutive bytes with the value 0
		//initialize flag which will be used to check if the bytes read are data to be printed or we have reached the end
		Arrays.fill(flag, (byte) 0);
		
		MyFile.seek(0);
		MyFile.read(ReadData);
		ByteArrayInputStream bais = new ByteArrayInputStream(ReadData);
		DataInputStream dis = new DataInputStream(bais);
		//marks the start of the input stream
		dis.mark(pagesize);
		//if every item on the file has not be read or we have read the last page
		while (bflag && (times * (maxStringSize + 4)) < MyFile.length()) {
			dis.read(br, 0, br.length);
			//if the byte array read are equal to flag then end has been reached
			if (Arrays.equals(br, flag)) {
				bflag = false;
			} else {
				//creates a new string and prints it and the line it is found
				temp = new String(br);
				System.out.print(temp);
				System.out.println(dis.readInt());
			}
			//checks if a page has been read
			//if true it initializes and resets the offset of our output stream 
			if (((times + 1) % (pagesize / (maxStringSize + 4))) == 0) {
				Arrays.fill(ReadData, (byte) 0);
				MyFile.read(ReadData);
				dis.reset();
			}
			times = times + 1;
		}
		dis.close();
		bais.close();
		MyFile.close();
	}
	
	/**
	 * Searches in which lines there is a specific word serially  
	 * @param search the word to be searched
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void readSerial(String search) throws IOException,FileNotFoundException {
		File file = new File("indexFile.txt.ndx");
		RandomAccessFile MyFile = new RandomAccessFile(file, "r");
		byte[] ReadData = new byte[pagesize];
		int times = 0;
		int timesFileWasAccessed = 1;
		byte[] br = new byte[maxStringSize];
		String temp;
		byte[] flag = new byte[maxStringSize];
		boolean bflag = true;//checks if the method has to stop
		boolean beenReadOnce = false;
		boolean theWordHasBeenFound = false;

		Arrays.fill(flag, (byte) 0);
		MyFile.seek(0);
		MyFile.read(ReadData);
		ByteArrayInputStream bais = new ByteArrayInputStream(ReadData);
		DataInputStream dis = new DataInputStream(bais);
		dis.mark(pagesize);
		
		while (bflag && (times * (maxStringSize + 4)) < MyFile.length()) {
			dis.read(br, 0, br.length);
			if (Arrays.equals(br, flag) || (beenReadOnce ^ theWordHasBeenFound)) {
				//checks if all the bytes read are 0 or if the word that is being searched has been found and all it's lines have
				//been printed
				bflag = false;
			} else {
				temp = new String(br);
				//checks if the word searched has been found
				if (search.compareTo(temp.replaceAll("\\s+", "")) == 0) {
					if (beenReadOnce == false) {
						//if the word has not been read once prints the text and the boolean variables become true
						System.out.print("The word " + search + " you searched for is found in the line(s): ");
						beenReadOnce = true;
						theWordHasBeenFound = true;
						System.out.print(dis.readInt());
					} else {
						System.out.print("," + dis.readInt());
					}
				} else if(search.compareTo(temp.replaceAll("\\s+", "")) > 0){
					//if the word that is being searched has a bigger value lexicographically skips the next 4 bytes
					theWordHasBeenFound = false;
					dis.skip(4);
				}else {
					//if the word that is being searched has a less value lexicographically means that the word is not in the file
					//because the file is sorted
					bflag=false;
				}

			}
			if (((times + 1) % (pagesize / (maxStringSize + 4))) == 0) {
				Arrays.fill(ReadData, (byte) 0);
				MyFile.read(ReadData);
				dis.reset();
				timesFileWasAccessed = timesFileWasAccessed + 1;
			}
			times = times + 1;
		}
		if (beenReadOnce == false) {
			System.out.print("the word was not found.");
		}
		dis.close();
		bais.close();
		MyFile.close();
		System.out.println();
		System.out.println("Time the disk was accessed: " + timesFileWasAccessed);
	}
/**
 * searches a specific word from the binary file by using the binary search algorithm.
 * @param search the word to be searched
 * @throws FileNotFoundException
 * @throws IOException
 */
	public void readbinary(String search) throws FileNotFoundException, IOException {
		File file = new File("indexFile.txt.ndx");
		RandomAccessFile MyFile = new RandomAccessFile(file, "r");
		byte[] ReadData = new byte[pagesize];
		int timesFileWasAccessed = 1;
		byte[] br = new byte[maxStringSize];
		String temp = "";
		byte[] flag = new byte[maxStringSize];
		boolean exit = false;
		boolean beenReadOnce = false;
		boolean theWordHasBeenFound = false;
		long currentPage = ((MyFile.length() / pagesize) / 2) * pagesize;
		long leftend = 0;
		long rightend = MyFile.length() - 1;
		int tempInt = 0;

		Arrays.fill(flag, (byte) 0);
		//moves the file pointer at the middle of the list
		MyFile.seek(currentPage);
		MyFile.read(ReadData);
		ByteArrayInputStream bais = new ByteArrayInputStream(ReadData);
		DataInputStream dis = new DataInputStream(bais);

		dis.mark(pagesize);
		while (exit == false) {
			//reads the word and it's line 
			dis.read(br, 0, br.length);
			tempInt = dis.readInt();
			temp = new String(br);
			if (search.compareTo(temp.replaceAll("\\s+", "")) < 0) {
				//if the word that is being searched has less value lexicographically than the first word of the page,
				//we search the left half of the area that currently is being searched 
				rightend = currentPage;
				currentPage = (((rightend - leftend) / pagesize) / 2) * pagesize;
			} else if (search.compareTo(temp.replaceAll("\\s+", "")) > 0) {
				//if the word that is being searched has a bigger value lexicographically, we read the last word of the page
				dis.skip(((pagesize / (maxStringSize + 4)) - 2) * (maxStringSize + 4));
				dis.read(br, 0, br.length);
				tempInt = dis.readInt();
				if (Arrays.equals(br, flag)) {
					//if it is null the method ends
					exit = true;
				} else {
					//we compare the word being searched with the last word of the page
					temp = new String(br);
					if (search.compareTo(temp.replaceAll("\\s+", "")) > 0) {
						//if the word that is being searched has a bigger value lexicographically, we search the right half of the 
						//area that currently is being searched
						leftend = currentPage;
						currentPage = (((leftend + rightend) / pagesize) / 2) * pagesize;
					} else if (search.compareTo(temp.replaceAll("\\s+", "")) < 0) {
						//if the word that is being searched has less value lexicographically, it means that the word is in this page
						//or there is not in the file
						dis.reset();
						//every item of the node is checked
						for (int i = 0; i < (pagesize / (maxStringSize + 4)); i++) {
							dis.read(br, 0, br.length);
							temp = new String(br);
							tempInt = dis.readInt();
							if (search.compareTo(temp.replaceAll("\\s+", "")) == 0) {
								if (beenReadOnce == false) {
									System.out.print("The word " + temp.replaceAll("\\s+", "")+ " has been found at the lines: " + tempInt);
									beenReadOnce = true;
								} else {
									System.out.print(", " + tempInt);
								}
							}
						}
						exit = true;
					} else {
						theWordHasBeenFound = true;
						exit = true;
					}
				}
			} else {
				theWordHasBeenFound = true;
				exit = true;
			}
			dis.reset();
			if (theWordHasBeenFound == false) {
				MyFile.seek(currentPage);
				MyFile.read(ReadData);
				timesFileWasAccessed = timesFileWasAccessed + 1;
			}

			if (currentPage == leftend || currentPage == rightend) {
				//if the above check is true, it means that the word is not found in the file
				exit = true;
				theWordHasBeenFound = false;
			}

		}
		boolean searchleft = true;
		boolean searchright = true;
		//we need the value of currentPage, so we use leftend and rightend as temporary values
		leftend = currentPage;
		rightend = currentPage;
		if (theWordHasBeenFound == true) {
			while (searchleft == true) {
				//Prints the contents of the current page and checks if the word searched is found at the start of this page.
				//If it is found then it continues to search if the word is found at previous lines.
				for (int i = 0; i < (pagesize / (maxStringSize + 4)); i++) {
					dis.read(br, 0, br.length);
					temp = new String(br);
					tempInt = dis.readInt();
					if (search.compareTo(temp.replaceAll("\\s+", "")) == 0) {
						if (beenReadOnce == false) {
							System.out.print("The word " + temp.replaceAll("\\s+", "")
									+ " has been found at the lines: " + tempInt);
							beenReadOnce = true;
						} else {
							System.out.print(", " + tempInt);
						}

					} else {
						if (i == 0)
							searchleft = false;
					}

				}
				leftend = leftend - pagesize;

				if (leftend >= pagesize && searchleft == true) {
				//checks if we have reached the first page or if the first word of the page is the word that is being searched.
					MyFile.seek(leftend);
					dis.reset();
					MyFile.read(ReadData);
					timesFileWasAccessed = timesFileWasAccessed + 1;

				} else {
					searchleft = false;
				}
			}
			//checks in the page that the word was found the first time,if the last word is the word that is being searched,
			//to see if the word that is being searched is found on later lines 
			MyFile.seek(currentPage);
			MyFile.read(ReadData);
			dis.reset();
			dis.skip(((pagesize / (maxStringSize + 4)) - 1) * (maxStringSize + 4));
			dis.read(br, 0, br.length);
			temp = new String(br);
			if (search.compareTo(temp.replaceAll("\\s+", "")) == 0) {
				rightend = rightend + pagesize;
				MyFile.seek(rightend);
				dis.reset();
				MyFile.read(ReadData);
				timesFileWasAccessed = timesFileWasAccessed + 1;
			} else {
				searchright = false;
			}
			while (searchright == true) {
				//reads every item of the page to check if the word that is being searched 
				for (int i = 0; i < (pagesize / (maxStringSize + 4)); i++) {
					dis.read(br, 0, br.length);
					temp = new String(br);
					tempInt = dis.readInt();
					if (search.compareTo(temp.replaceAll("\\s+", "")) == 0) {
						if (beenReadOnce == false) {
							System.out.print("The word " + temp.replaceAll("\\s+", "")+ " has been found at the lines: " + tempInt);
							beenReadOnce = true;
						} else {
							System.out.print("," + tempInt);
						}
					} else {
						if (i == (pagesize / (maxStringSize + 4)) - 1)
							searchright = false;
					}
				}
				rightend = rightend + pagesize;
				if (rightend <= MyFile.length() - pagesize && searchright == true) {
					MyFile.seek(rightend);
					dis.reset();
					MyFile.read(ReadData);
					timesFileWasAccessed = timesFileWasAccessed + 1;
				} else {
					searchright = false;
				}
			}
		} else if(beenReadOnce){
			//do nothing
		}else{
			
			System.out.print("the word was not found");
		}
		System.out.println();
		System.out.println("Total times the file was accessed: "+timesFileWasAccessed);
		MyFile.close();
	}
}
