package ie.gmit.java2;

import java.awt.EventQueue;

public class Runner {
	
	public static void main(String[] args) {
		// Runnable to run the AppWindow
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// New AppWindow object that calls the AppWindow constructor thats calls
				// the createGUI() method
				AppWindow frame = new AppWindow();
				// Sets the frame to visible
				frame.setVisible(true);
			}
		});
	}
}
