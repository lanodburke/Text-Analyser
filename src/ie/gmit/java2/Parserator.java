package ie.gmit.java2;

import java.util.ArrayList;
import java.util.List;
// Parserator interface
public interface Parserator {
	// Declaring fileContents ArrayList because I want both of the classes to implement it
	List<String> fileContents = new ArrayList<String>();
	
	// Parse method
	void parse(String file);
	
	// All of the ArrayList methods
	List<String> getContents();

	boolean contains(String s);

	int count();

	int countOccurrences(String s);

	int getFirstIndex(String s);

	int getLastIndex(String s);

	void delete(String s);

	void delete(int i);

	int[] getAllIndices(String input);
	
	boolean isEmpty();

}