package ddl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
/**
 * This class is our main program and combines all our classes into one
 * @author Ioannis Lamprinidis
 *
 */
public class MyEditor {
	private DoubleLinkedList editor;
	private Node current;
	private MyTupleEditor toupleEditor;
	/**
	 * the default constructor of this class. Initializes the double linked list,
	 * the current node of the double linked list, and the tuple editor
	 */
	public MyEditor() {
		editor=new DoubleLinkedList();
		current=editor.getHead();
		toupleEditor=new MyTupleEditor();
	}
	
	/**
	 * saves the double lined list to the file
	 * @param file the file where the double linked list will be saved
	 * @param node a node of the double linked list from which the save method will start
	 * @throws IOException if the file cannot be opened
	 */
	public static void save(File file,Node node) throws IOException {
		BufferedWriter bw=new BufferedWriter(new FileWriter(file));
		while(node!=null) {
		bw.write(node.getData() + "\n");
		bw.flush();
		node=node.getNext();
		}
		System.out.println("The program has been saved.");
		bw.close();
		}
	
	public static void main(String[] args) {
		//our main program. It opens the file if the user entered a correct path,otherwise it creates an empty file
		MyEditor me=new MyEditor();
		String fileName="emptyTextfile.txt";
		if(args.length>0)
			fileName=args[0];
		String line = null;
        String stringTemp="";
        boolean countingLines=false;
        int lineOfText;
		File file = new File(fileName);
		try {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        while((line=br.readLine())!= null) {
        	me.current=me.editor.insert(me.current, line);
        	}
        br.close();
		}catch(FileNotFoundException e) {
			System.out.println("An empty file has benn created.Please don't forget to save");
		}catch(IOException e) {
			System.out.println("There was a problem with accessing the file.");
		}
		//The scanner class is used to get input from the user and the the input is checked if it is a correct command
        Scanner scan=new Scanner(System.in);
        String userOption= "";
        
        me.current=me.editor.getHead();
    	lineOfText=1;
        
        while(userOption!="q" && userOption!="x"){
        while(!scan.hasNext()) {
        	
        }
        
        userOption=scan.nextLine();
        
        
        switch(userOption) {
        	case "^":
        	//go to the first line
        	me.current=me.editor.getHead();
        	lineOfText=1;
        	System.out.println("OK");
        		break;
        	case "$":
        		//go to the last line
        		while(me.current!= null && me.current.getNext() !=null) {
        			me.current=me.current.getNext();
        			lineOfText=lineOfText+1;
        		}
        		System.out.println("OK");
        		break;
        	case "-":
        		//go to the previous line
        		if(me.current!=null &&   me.current.getPrev()!= null) {
        			me.current=me.current.getPrev();
        			System.out.println("OK");
        			lineOfText=lineOfText-1;
        		}else if(me.current==null) {
        			System.out.println("The list is empty.");
        		}else{
        			System.out.println("You are at the start of the text file.");
        		}
        		
        		break;
        		
        	case "+":
        		//go to the next line
        		if(me.current!=null && me.current.getNext()!= null) {
        			me.current=me.current.getNext();
        			System.out.println("OK");
        			lineOfText=lineOfText+1;
        		}else if(me.current==null) {
        			System.out.println("The list is empty.");
        		}else{
        			System.out.println("You are at the end of the text file.");
        		}
        		
        		break;
        		
        	case "a":
        		//enter a new line after the current one
        		System.out.println("Please Enter the text for the new line");
        		 while(!scan.hasNext()) {
        	        	
        	        }
        		stringTemp=scan.nextLine();
        		me.editor.insertAfter(me.current, stringTemp);
        		if(me.current==null)
        			me.current=me.editor.getHead();
        		break;
        	case "t":
        		//enter a new line before the current one 
        		System.out.println("Please Enter the text for the new line");
       		 while(!scan.hasNext()) {
       	        	
       	        }
       		 stringTemp=scan.nextLine();
       		me.editor.insertAtBack(me.current, stringTemp);
       		if(me.current==null)
    			me.current=me.editor.getHead();
        		break;
        	case "d":
        		//deletes the current line
        		if(me.editor.getHead()!=null) {
        			if(me.current.getNext()!=null) {
        				if(lineOfText==1) {
        					lineOfText=1;
        				}else {
        				lineOfText=lineOfText+1;
        				}
        			}else {
        				lineOfText=lineOfText-1;
        			}
        		}else {
        			lineOfText=0;
        		}
        		
        		me.current=me.editor.delete(me.current);
        		
        		System.out.println("Deleted");
        		break;
        		
        	case "l":
        		//prints all lines
        		me.editor.printlist(me.editor.getHead(),countingLines,1);
        		
        		break;
        		
        	case "n":
        		//
        		if(!countingLines) {
        		countingLines=true;
        		System.out.println("The index of the line will be shown");
        		}else {
        			countingLines=false;
        			System.out.println("The index of the line will not be shown");
        		}
        		break;
        		
        	case "p":
        		if(countingLines) {
        			System.out.print(lineOfText +")");
        		}
        		
        		me.editor.print(me.current);
        		break;
        		
        	case "q":
        		System.out.println("Thanks for using the Editor.");	
        		userOption="q"; //why
        		break;
        	case "w":
        		try {
        		save(file,me.editor.getHead());
        		}catch(IOException e) {
        			System.out.println("File could not be saved.");
        		}
        		break;
        		
        	case "x":
			try {
				save(file,me.editor.getHead());
				System.out.println("Thanks for using the Editor.");	
        		userOption="x";
			} catch (IOException e) {
				System.out.println("File could not be saved.");
				e.printStackTrace();
			}
        		
        		break;
        		
        	case "=":
        		System.out.println("The number of the current line is: "+lineOfText);
        		break;
        		
        	case "#":
        		me.editor.printTotalLinesAndChars(me.editor.getHead());
        		break;
        	case "c":
        		me.toupleEditor.createTuplesArray(me.editor.getHead());
        		break;
        	case "v":
			try {
				me.toupleEditor.readFromDisk();
			} catch (IOException e) {
				System.out.println("Could not read the file.");
				e.printStackTrace();
			}
        		break;
        	case "s":
        		System.out.println("Please Enter the word to be searched serially.");
          		 while(!scan.hasNext()) {
          	        	
          	        }
          		 stringTemp=scan.nextLine();
			try {
				me.toupleEditor.readSerial(stringTemp);
			 }catch(FileNotFoundException e) {
				System.out.println("The file could not be read.");
			}catch (IOException e) {
				System.out.println("There was a problem with the file(it may be empty).");
				e.printStackTrace();
			}
        		break;
        	case "b":
        		System.out.println("Please Enter the word to be searched with binary search.");
         		 while(!scan.hasNext()) {
         	        	
         	        }
         		 stringTemp=scan.nextLine();
       		try {
				me.toupleEditor.readbinary(stringTemp);
			} catch (FileNotFoundException e) {
				System.out.println("The file could not be read.");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("There was a problem with the file(it may be empty).");
				e.printStackTrace();
			}
       		break;
        	default:
        		System.out.println("Bad command");
        		continue;
        	
        }
        }
        scan.close();
	}

}
