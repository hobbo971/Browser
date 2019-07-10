package Browser;

import java.io.IOException;

public class runBrowser {
	public static void main(String args[]) {
		Browser Williams = new Browser(); 
		try {
			Williams.browserLayout(); //runs browser
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
