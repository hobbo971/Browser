package Browser;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.HyperlinkEvent.EventType;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.io.*;

public class Browser extends JFrame {

	private JTextField url = new JTextField(getHome(),
			40); /*
					 * set the initial page to google
					 */
	JEditorPane display = new JEditorPane();
	private JScrollPane scroll = new JScrollPane(display);
	private JButton search = new JButton("Search");
	private JButton back = new JButton("Back");
	private JButton forward = new JButton("Forward");
	private JButton home = new JButton("Home");
	private JButton reload = new JButton("Reload");
	private JButton bookmark = new JButton("Bookmark");
	private Stack<String> backStack = new Stack();
	private Stack<String> forwardStack = new Stack();
	private String homeURL = getHome();
	private JButton sethome = new JButton("setHome");
	private JButton history = new JButton("history");

	public void browserLayout() throws IOException { // sets up the browser

		setTitle("Chrome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 880);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		display.setEditable(false);
		addBrowserFunction(getContentPane()); // passes in content pane which
												// has the JScrollPane with
												// display passed in
		display.setEditorKit(JEditorPane.createEditorKitForContentType(
				"text/html")); /* allows user to click on links */ // page
		display.setPage(getHome());
		backStack.push(getHome());
		History.logURL(url.getText());//
	}

	/**
	 * @param contentPane
	 */
	public void addBrowserFunction(Container contentPane) {

		contentPane.add(new JScrollPane(display), "Center");

		JPanel panel = new JPanel(); // add all the buttons to the panel onto
										// the contentPane
		panel.add(new JLabel("URL"));
		panel.add(url);
		panel.add(search);
		panel.add(back);
		panel.add(forward);
		panel.add(reload);
		panel.add(home);
		panel.add(bookmark);
		panel.add(sethome);


		contentPane.add(panel, BorderLayout.NORTH);

		completeActionListener(); // calls all the actionListners

	}

	private void completeActionListener() {

		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					while (!forwardStack.isEmpty()) { // once a search is
														// inputed then you
														// shouldn't be able to
														// go forward
						forwardStack.pop();
					}
					backStack.push(url.getText()); // adds the new search to the
													// backwards stack
					display.setPage(url.getText());
					History.logURL(url.getText()); // passes in the URL to the
					// method

				} catch (Exception error) {

					System.out.println("error");
				}

			}
		});

		forward.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					if (forwardStack.isEmpty()) {
						return;
					} else {
						backStack.push(url.getText()); // adds the current page
														// to back stack
						display.setPage(forwardStack.peek()
								.toString()); /*
												 * sets display to top of
												 * forward stack
												 */
						url.setText(forwardStack.peek());
						History.logURL(url.getText()); // logs History
						forwardStack.pop(); // removes the top of the stack
					}
				} catch (IOException e1) {

					System.out.println("error");
				}

			}
		});

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (backStack.size() == 1) {
						return; // returns no URL if no url to go back to
					}

					forwardStack.push(backStack.peek().toString()); // adds the
																	// current
																	// URL to
																	// forward
																	// stack

					backStack.pop(); // removes top URL of stack
					display.setPage(backStack.peek().toString()); // sets pages
																	// to top of
																	// back
																	// stack

					url.setText(backStack.peek()); // set the url to top of
													// stack so displays upto
													// date url on the browser

					History.logURL(url.getText()); // logs the history

				} catch (Exception error) {

					System.out.println("error");
				}

			}
		});

		home.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				while (!forwardStack.isEmpty()) { // once a home is inputed then
													// you shouldn't be able to
													// go forward
					forwardStack.pop();
				}
				try {
					url.setText(homeURL); // sets URL to google
					History.logURL(url.getText()); // logs history
					display.setPage(url.getText());// displays the home page
					backStack.push(url.getText());// adds it to back stack
				} catch (IOException e1) {

					e1.printStackTrace();
				}

			}
		});

		reload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					display.setPage(url.getText()); // refreshes page by
													// resetting the page to the
													// current URL
				} catch (IOException e1) {

					e1.printStackTrace();
				}

			}
		});

		bookmark.addActionListener(new ActionListener() { // adds a bookmark to
															// txt file to save
															// the users
															// favourite pages

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Bookmark.getBookmark(url.getText()); // calls method from
															// history class,
															// passes in URL
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

			}
		});

		sethome.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				homeURL = url.getText();
				setHome(homeURL);

			}
		});

		display.addHyperlinkListener(new HyperlinkListener() {

			@Override
			public void hyperlinkUpdate(HyperlinkEvent action) {
				if (action.getEventType() == HyperlinkEvent.EventType.ACTIVATED) { // checks
																					// for
																					// the
																					// event
																					// type

					try {
						while (!forwardStack.isEmpty()) { // once a link is
															// inputed then you
															// shouldn't be able
															// to go forward
							forwardStack.pop();
						}
						backStack.push(action.getURL().toString()); // adds the
																	// URL to
																	// stack
						url.setText(backStack.peek().toString()); // sets URL to
																	// display
																	// in the
																	// browser
						display.setPage(action.getURL());

					} catch (Exception error) {

						System.out.println("error");
					}

				}

			}
		});
		
		

	}

	public String getHome() {
		try {
			FileReader file = new FileReader("home.txt");
			Scanner load = new Scanner(file);// creates a new scanner to read
												// home.txt
			homeURL = load.nextLine();	// sets the home url to what is in the txt file
			load.close();//closes file
		} catch (FileNotFoundException e) {//
			System.out.println("No home file");
		}
		return homeURL;

	}

	public void setHome(String home) {
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream("home.txt", false));
			out.println(home); // writes the url it fetches into the txt file
			out.close();
		} catch (IOException error) {
			System.out.println("Can not set home");
			// catches error
		}

	}

}
