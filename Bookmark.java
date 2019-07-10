package Browser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Bookmark {

	public static void getBookmark(String bookmarkURL) throws FileNotFoundException {

		
		PrintWriter out = new PrintWriter(new FileOutputStream("Bookmarks.txt", true));
		out.println(bookmarkURL); // writes the URL it fetches into the txt file
		out.close();

	}
}
