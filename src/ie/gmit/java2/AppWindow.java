package ie.gmit.java2;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class AppWindow extends JFrame {
	// Member Variables
	// JComponents
	private JFrame frame;
	private JPanel contentPane;
	private JTextArea textArea = new JTextArea();
	// Parserator and ParseFile and ParseURL Objects
	private Parserator parser = null;
	private ParseFile parseFile;
	private ParseURL parseURL;

	// ArrayList
	private List<String> list = new ArrayList<String>();
	// Normal Variables
	private String newLine = "\n";
	private String file = null;
	private String url = null;
	private String str1 = "";
	private String str2 = "";
	private String str3 = "";

	// Constructor that is called in main
	public AppWindow() {
		createGUI();
	}
	
	// This method creates the frame and the buttons
	public void createGUI() {
		// UIManager to get current system theme
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		// Setting frame attributes
		setTitle("Text Analyser");
		setResizable(false);
		setDefaultCloseOperation(3);
		setBounds(100, 100, 620, 350);
		setLocationRelativeTo(null);

		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 590, 240);

		this.contentPane.add(scrollPane);
		this.textArea.setEditable(false);

		scrollPane.setViewportView(this.textArea);

		JButton fileMenu = fileButton();
		fileMenu.setBounds(10, 10, 110, 25);
		this.contentPane.add(fileMenu);

		JButton urlButton = urlButton();
		urlButton.setBounds(130, 10, 110, 25);
		this.contentPane.add(urlButton);

		JButton btnStats = statsButton();
		btnStats.setBounds(490, 10, 110, 25);
		this.contentPane.add(btnStats);
		// Hides this button until a file or a url has been parsed

		JButton btnExit = exitButton();
		btnExit.setBounds(492, 288, 110, 25);
		this.contentPane.add(btnExit);

		JButton printButton = printButton();
		printButton.setBounds(250, 10, 110, 25);
		this.contentPane.add(printButton);

		JButton searchButton = searchButton();
		searchButton.setBounds(370, 10, 110, 25);
		this.contentPane.add(searchButton);
	}

	// Search Button opens up a JDialog with some search options
	private JButton searchButton() {
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create the new content panel
				JPanel contentPanel = new JPanel();
				// Create a JDialog
				JDialog jd = new JDialog();
				// Set the dimensions
				jd.setBounds(400, 500, 400, 500);
				// Set the layout/border
				jd.getContentPane().setLayout(new BorderLayout());
				contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				jd.getContentPane().add(contentPanel, "Center");
				contentPanel.setLayout(null);
				jd.setModal(true);
				jd.setLocationRelativeTo(frame);
				jd.setTitle("Search Options");
				jd.setResizable(false);

				// Text area to print out the content
				JTextArea searchOut = new JTextArea();
				contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				jd.setContentPane(contentPanel);

				// Create scroll pane
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(20, 130, 350, 290);
				contentPanel.add(scrollPane);
				searchOut.setEditable(false);

				// Set the scroll pane to the text area
				scrollPane.setViewportView(searchOut);

				if (list.isEmpty()) {
					textArea.append("Please parse a File or URL first before you search the array." + newLine);
					return;
				} else {
					// Buttons are listed as A - H for ease of use
					// Button A - Searches the array for a string and returns true
					// or false if the string is in the array
					JButton buttonA = new JButton("Search Array");
					buttonA.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							// JOption pane to get input from the user
							String s = (String) JOptionPane.showInputDialog(frame, "Enter in a String to search",
									"Search Array", JOptionPane.PLAIN_MESSAGE, null, null, null);

							// Check to see if the input is null
							if ((s != null) && (s.length() > 0)) {
								// If not call method
								searchOut.append("Is String in the file: " + parser.contains(s) + newLine);

							}
						}
					});
					buttonA.setBounds(20, 10, 110, 25);
					buttonA.setToolTipText("Check if String is in the array.");
					contentPanel.add(buttonA);

					// Button B - Returns the size of the array
					JButton buttonB = new JButton("Array Size");
					buttonB.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							searchOut.append("Size of the Array: " + parser.count() + newLine);
						}
					});
					buttonB.setBounds(140, 10, 110, 25);
					buttonB.setToolTipText("Count the total size of the array.");
					contentPanel.add(buttonB);

					// Button C - Asks the user to enter in a String and counts the
					// number of times that String appears in the array
					JButton buttonC = new JButton("Occurences");
					buttonC.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							String s = (String) JOptionPane.showInputDialog(frame, "Enter in a String",
									"Number of Occurences of String", JOptionPane.PLAIN_MESSAGE, null, null, null);

							if ((s != null) && (s.length() > 0)) {
								searchOut.append(
										"Occurences of String in the array: " + parser.countOccurrences(s) + newLine);
							}
						}
					});
					buttonC.setBounds(260, 10, 110, 25);
					buttonC.setToolTipText("Count the number of occurences of a String in the array.");
					contentPanel.add(buttonC);

					// Button D - Ask the user to enter in a String and returns the
					// first index that it appears at in the array
					JButton buttonD = new JButton("First Index");
					buttonD.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							String s = (String) JOptionPane.showInputDialog(frame, "Enter in a String",
									"First Index of a String", JOptionPane.PLAIN_MESSAGE, null, null, null);

							if ((s != null) && (s.length() > 0)) {
								searchOut.append("The first index of: " + parser.getFirstIndex(s) + newLine);
							}
						}
					});
					buttonD.setBounds(20, 50, 110, 25);
					buttonD.setToolTipText("Get the first index of a String in the array.");
					contentPanel.add(buttonD);

					// Button E - Asks the user to enter a String and returns the
					// last index that is appears at in the array
					JButton buttonE = new JButton("Last Index");
					buttonE.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							String s = (String) JOptionPane.showInputDialog(frame, "Enter in a String",
									"Last Index of a String", JOptionPane.PLAIN_MESSAGE, null, null, null);

							if ((s != null) && (s.length() > 0)) {
								searchOut.append("The last index of: " + parser.getLastIndex(s) + newLine);
							}
						}
					});
					buttonE.setBounds(140, 50, 110, 25);
					buttonE.setToolTipText("Get the last index of a s String in the array.");
					contentPanel.add(buttonE);

					// Button F - Asks the user to enter in a String and it returns
					// the indices of all occurrences of that String in the array
					JButton buttonF = new JButton("Indices");
					buttonF.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							String s = (String) JOptionPane.showInputDialog(frame, "Enter in a String",
									"Return the Indices of all occurences", JOptionPane.PLAIN_MESSAGE, null, null,
									null);
							int[] indices = null;
							if ((s != null) && (s.length() > 0)) {
								indices = parser.getAllIndices(s);
								searchOut.append("Indices of occurences: ");
								for (int i = 0; i < indices.length; i++) {
									searchOut.append(indices[i] + " ");
								}
								searchOut.append(newLine);
							}
						}
					});
					buttonF.setBounds(260, 50, 110, 25);
					buttonF.setToolTipText("Return the indices of all occurences of the String in the array.");
					contentPanel.add(buttonF);

					// Button G - Asks the user to enter a String and then removes
					// it from the array
					JButton buttonG = new JButton("Delete (String)");
					buttonG.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							String s = (String) JOptionPane.showInputDialog(frame, "Enter in a String",
									"Delete a String from the array", JOptionPane.PLAIN_MESSAGE, null, null, null);

							if ((s != null) && (s.length() > 0)) {
								parser.delete(s);
								searchOut.append("The String (" + s + ") has been deleted from the array." + newLine);
							}
						}
					});
					buttonG.setBounds(20, 90, 110, 25);
					buttonG.setToolTipText("Delete the specified String from the array.");
					contentPanel.add(buttonG);

					// Asks the user to enter the index that they want to remove
					// from the array
					JButton buttonH = new JButton("Delete (Index)");
					buttonH.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							int i = Integer.parseInt((String) JOptionPane.showInputDialog(frame, "Enter in a String",
									"Delete the String at the specified index", JOptionPane.PLAIN_MESSAGE, null, null,
									null));

							parser.delete(i);
							searchOut.append(
									"The String at index (" + i + ") has been deleted from the array." + newLine);
						}
					});
					buttonH.setBounds(140, 90, 110, 25);
					buttonH.setToolTipText("Delete the String at the specified index.");
					contentPanel.add(buttonH);

					// Button to close the JDialog
					JButton searchExit = new JButton("Exit");
					searchExit.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jd.dispose();
						}
					});
					searchExit.setBounds(262, 430, 110, 25);
					contentPanel.add(searchExit);
					// Set the Dialog to visible when action listener is called
					jd.setVisible(true);
				}
			}
		});

		return searchButton;
	}

	// Prints out the contents of the array by appending the text area with each
	// element of the array
	private JButton printButton() {
		JButton printButton = new JButton("Print...");
		printButton.setToolTipText("Print Array Contents");
		printButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// Check if the list is null 
				if (list.isEmpty()) {
					// If it is null tell the user to parse a File or URL
					textArea.append("Please parse a File or URL to view their contents." + newLine);
					return;
				} else {
					// Print the array to the text area
					textArea.setText(null);
					for (String string : list) {
						// Add a new line character after every element in the array
						textArea.append(string + newLine);
					}
				}
			}
		});
		return printButton;
	}

	private JButton exitButton() {
		// Button to stop the program
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		return btnExit;
	}

	private JButton statsButton() {
		// Stats - Gives some stats about the File an URL
		JButton btnStats = new JButton("Stats");
		btnStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// JPanel for stats
				JPanel contentPanel = new JPanel();
				// Create new JDialog
				JDialog jd = new JDialog();
				jd.setBounds(300, 400, 250, 300);
				jd.getContentPane().setLayout(new BorderLayout());
				contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				jd.getContentPane().add(contentPanel, "Center");
				contentPanel.setLayout(null);
				jd.setModal(true);
				jd.setLocationRelativeTo(frame);
				jd.setTitle("Stats");
				jd.setResizable(false);
				
				// Create a text area where the stats will be displayed
				JTextArea statsOut = new JTextArea();
				contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				jd.setContentPane(contentPanel);
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(10, 10, 225, 220);
				
				contentPanel.add(scrollPane);
				statsOut.setEditable(false);

				scrollPane.setViewportView(statsOut);
				
				// Check to see if the list is null, if true output to the main text area and exit the method
				if (list.isEmpty()) {
					textArea.append("Please parse a File or URL to view stats." + newLine);
					return;
				} else {					
					// Otherwise append the stats text area with the strings that get instantiated when a File or URL is parsed
					statsOut.append(str1 + newLine);
					statsOut.append(str2 + newLine);
					statsOut.append(str3 + newLine);

					// Button to close the JDialog
					JButton searchExit = new JButton("Exit");
					searchExit.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jd.dispose();
						}
					});
					searchExit.setBounds(126, 237, 110, 25);
					contentPanel.add(searchExit);

					jd.setVisible(true);
				}
			}
		});
		return btnStats;
	}

	private JButton urlButton() {
		// Parse URL button
		JButton parseButton = new JButton("Parse a URL");
		parseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// JOptionPane to prompt the user to enter the url
				url = (String) JOptionPane.showInputDialog(frame, "Enter in a URL to parse", "Parse a URL",
						JOptionPane.PLAIN_MESSAGE, null, null, null);
				// Checks if the String entered is not null
				if ((url != null) && (url.length() > 0)) {
					// Instantiates the Parserator interface as a ParseURL
					// object
					// and passes the String to the constructor that calls the
					// parse method
					parser = new ParseURL(url);

					// Casting the parseURL object to the Parserator object so
					// they point to the same instance
					// I'm casting the object so I can call a method from the
					// parseURL object that isn't in the Parserator interface
					// So I can manipulates the Variables that are in the parser
					// instance
					parseURL = (ParseURL) parser;

					// Instantiating the ArrayList to a copy of the ArrayList in
					// the parser object
					list = parser.getContents();
					
					str1 = "";
					str2 = "";
					str3 = "";
					// Calls a method in the parseURL class that gets the IP of
					// the entered string - this is for the stats
					str1 = "Ip address of: " + url + newLine +" is " + parseURL.getIp(url);
				}
			}
		});
		return parseButton;
	}

	private JButton fileButton() {
		// Parse File Button
		JButton fileMenu = new JButton("Select a file");
		fileMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// gets the current directory that the program is running in
				String curDir = System.getProperty("user.dir");

				// New File Chooser
				JFileChooser fc = new JFileChooser(curDir);

				// Opens the File Chooser
				fc.showOpenDialog(AppWindow.this);

				// New File object that gets the selected file from the File
				// Chooser
				File selected = fc.getSelectedFile();
				// Checks if the selected file isn't null
				if (selected != null) {
					// Sets a string variable to the file name
					file = selected.getAbsolutePath();
				}
				// If the String isn't null then parse the file
				if ((file != null) && (file.length() > 0)) {
					// Instantiates the Parserator object as a ParseFile Object
					// and passes the String to the constructor
					// that calls the parse method
					parser = new ParseFile(file);

					// Casting the parseFile object to the Parserator object so
					// they point to the same instance
					parseFile = (ParseFile) parser;

					// Instantiating the ArrayList to a copy of the ArrayList in
					// the parser object
					list = parser.getContents();
					
					// Set the strings to null for the stats
					str1 = "";
					str2 = "";
					str3 = "";
					
					str1 = "Number of lines: " + parseFile.countLines(file);
					str2 = "File size: " + parseFile.getFileSize(file);
					str3 = "Number of words: " + parseFile.count();
				}
			}
		});
		return fileMenu;
	}
}
