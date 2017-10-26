package ie.gmit.java2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ParseFile implements Parserator {
	// Member Variables
	private List<String> fileContents = new ArrayList<String>();
	
	// Empty Constructor
	public ParseFile() {

	}
	
	// Constructor that calls the parse method
	public ParseFile(String file) {
		this.parse(file);
	}
	
	// Parse method that parses the specified File
	@Override
	public void parse(String file) {
		// Buffered Reader
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		String s = "";
		
		// Instantiating the Buffered Reader as a InputStreamReader and FileInputStream reader type
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			// Iterates through the file
			while ((s = br.readLine()) != null) {
				sb.append(s + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException ioe) {
			}
		}
		
		// I'm using the Jsoup external library to parse the String
		Document doc = Jsoup.parse(sb.toString());
		String text = doc.body().text();
		// Call addItem method to add the String to the array
		addItem(text);
	}
	
	// Methods
	// Adds s to the array
	public void addItem(String s) {
		String[] sa = s.split("\\s+");
		fileContents.addAll(Arrays.asList(sa));
	}

	// Count the number of lines in the file
	public int countLines(String filename){
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			try {
				while ((readChars = is.read(c)) != -1) {
					empty = false;
					// Iterates through the file 
					for (int i = 0; i < readChars; ++i) {
						// Increments count every time there is a new line character
						if (c[i] == '\n') {
							++count;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Returns the file size of a String parameter
	public String getFileSize(String fileStr) {
		String modifiedFileSize = null;
		// Creates a new File object from the String
		File file = new File(fileStr);
		double fileSize = 0.0;
		// Checks to see if the file is actually a file
		if (file.isFile()) {
			// Get the file length in bytes
			fileSize = (double) file.length();
			// Return a string with the file size in either bytes, kilobytes or megabytes
			if (fileSize < 1024) {
				// If the file size is less than 1024 bytes, concatenate the String with a 'B'
				modifiedFileSize = String.valueOf(fileSize).concat("B");
			} else if (fileSize > 1024 && fileSize < (1024 * 1024)) {
				// If the file size is greater than 1024 and less than 1024 * 1024, convert the bytes to kilobytes 
				// and concatenate the String with a 'KB'
				modifiedFileSize = String.valueOf(Math.round((fileSize / 1024 * 100.0)) / 100.0).concat("KB");
			} else {
				// Else convert the bytes to megabytes and concatenate the String with a 'MB'
				modifiedFileSize = String.valueOf(Math.round((fileSize / (1024 * 1204) * 100.0)) / 100.0).concat("MB");
			}
		} else {
			// If it isn't a file return 'Unknown'
			modifiedFileSize = "Unknown";
		}

		return modifiedFileSize;
	}
	
	// Implemented methods from the interface
	// Return a copy of the ArrayList 
	@Override
	public List<String> getContents() {
		List<String> tempList = new ArrayList<String>();
		for (int i = 0; i < fileContents.size(); i++) {
			tempList.add(fileContents.get(i));
		}

		return tempList;
	}
	
	// Checks to see if the String is in the array
	// Returns true if it is in the array otherwise returns false
	@Override
	public boolean contains(String s) {
		if (fileContents.contains(s)) {
			return true;
		}
		return false;
	}
	
	// Returns the size of the array
	@Override
	public int count() {
		return fileContents.size();
	}
	
	// Counts the number of Occurrences of s in the array
	@Override
	public int countOccurrences(String s) {
		return Collections.frequency(fileContents, s);
	}
	
	// Returns the index of the first occurrence of s
	@Override
	public int getFirstIndex(String s) {
		return fileContents.indexOf(s);
	}
	
	// Returns the index of the last occurrence of s
	@Override
	public int getLastIndex(String s) {
		return fileContents.lastIndexOf(s);
	}
	
	// Returns the indices of all occurrences of s
	@Override
	public int[] getAllIndices(String s) {
		// New ArrayList of type integer 
		ArrayList<Integer> indices = new ArrayList<>();
		// Iterate through the fileContents array
		for (int i = 0; i < fileContents.size(); i++) {
			// If s is equal to the value at index i of fileContents add the index(i)
			// to the integer ArrayList
			if (fileContents.get(i).equals(s)) {
				indices.add(i);
			}
		}
		// Instantiate new int array to the size of the integer ArrayList
		int[] result = new int[indices.size()];
		// Iterate through the Integer array list
		for (int i = 0; i < indices.size(); i++) {
			// Assign the values at i in the ArrayList to the int array
			result[i] = indices.get(i);
		}
		return result;
	}
	
	// Delete the specified String from the array
	@Override
	public void delete(String s) {
		fileContents.remove(s);
	}
	
	// Delete the specified index from the array
	@Override
	public void delete(int i) {
		fileContents.remove(i);
	}
	
	// Check to see if the ArrayList is empty
	@Override
	public boolean isEmpty() {
		return fileContents.isEmpty();
	}
}
