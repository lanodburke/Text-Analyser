package ie.gmit.java2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ParseURL implements Parserator {
	// Member variables
	private List<String> fileContents = new ArrayList<String>();
	
	// Blank Constructor
	public ParseURL() {
		
	}
	
	// Constructor that calls the parse method
	public ParseURL(String url) {
		this.parse(url);
	}
	
	// Parse method that parses the specified URL
	@Override
	public void parse(String urlString) {
		// URL object 
		URL url = null;

		try {
			// Instantiate the URL 
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			try {
				// If Malformed URL set the URL to google.com
				url = new URL("http://google.com");
			} catch (MalformedURLException e1) {
				System.out.println("Was wrong - google.com");
			}
		}
		
		// BufferedReader
		BufferedReader br = null;
		try {
			// Instantiate the Buffered Reader to the URL and open the stream
			br = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Declare and Instantiate s and StringBuffer
		String s = "";
		StringBuffer sb = new StringBuffer();
		
		try {
			// Iterate through the URL
			while ((s = br.readLine()) != null) {
				// Add whitespace after each string
				sb.append(s + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
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
	
	// Returns the IP address of the given domain
	public String getIp(String url){
		InetAddress address = null;
		try {
			address = InetAddress.getByName(new URL(url).getHost());
		} catch (UnknownHostException | MalformedURLException e) {
			try {
				// If it is a malformed URL it sets the URL to google.com
				address = InetAddress.getByName(new URL("http://google.com").getHost());
			} catch (UnknownHostException | MalformedURLException e1) {
				
				e1.printStackTrace();
			}
		}
		String ip = address.getHostAddress();
		return ip;
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
	public boolean isEmpty(){
		return fileContents.isEmpty();
	}

}
