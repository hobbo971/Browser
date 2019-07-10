package Browser;
import java.awt.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ListModel;

public class History {
	
	

	public static void logURL(String urlString) throws IOException { // allows a string to be passed in
		
		if(urlString != null){
		PrintWriter out = new PrintWriter(new FileOutputStream("history.txt", true) );
		out.println(urlString); // writes the url it fetches into the txt file
		out.close();
		
		
		}

	}


}


