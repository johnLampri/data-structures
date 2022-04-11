package ddl;
/**
 * This class is used for storing a token and the line it is found 
 * @author Ioannis Lamprinidis
 */
public class Tuple implements java.lang.Comparable<Tuple>{
	
	private String word;
	private int line;
	/**
	 * The constructor of the class Tuple
	 * @param word the token to be saved
	 * @param line the line that the word is found
	 */
	
	public Tuple(String word,int line){
		this.word=word;
		this.line=line;
	}
	
	/**
	 * returns the token that is saved on this tuple
	 * 
	 * 
	 */
	
	public String getWord() {
		return word;
	}

	/**
	 * changes the token of this tuple
	 * 
	 * @param word The token to be set on this tuple
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * returns the line the word was found 
	 * @return
	 */
	public int getLine() {
		return line;
	}

	/**
	 * changes the line that the word is found
	 * @param line
	 */

	public void setLine(int line) {
		this.line = line;
	}

/**
 * compares two tuples by using their token
 * 
 */
public int compareTo(Tuple o) {
		if(this.word.compareTo(o.getWord())<0) {
			return -1;
		}
		else if(this.word.compareTo(o.getWord())>0) {
			return 1;
		}else {
			return 0;
		}
	}
	
	
	
}
