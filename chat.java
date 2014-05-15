import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class chat extends JFrame {

	public JFrame chatWindow;
	public JFrame checkConnectionWindow;

	private JTextArea messageHistory;
	private JScrollPane scrollPane1;

	private JTextArea messageBox;
	private JScrollPane scrollPane2;

	private String meMessage;
	private static String FriendMessage = "";
	private ArrayList<String> history = new ArrayList<String>();
	private JButton btnSubmit;
	private Chat chat2;
	private static MainAppGui mainApp;
	public String name;
	private static String id;

	// private ConnectionConfiguration config = new ConnectionConfiguration(
	// "talk.google.com", 5222, "gmail.com");
	// private XMPPConnection connection = new XMPPConnection(config);
	public static XMPPConnection connection;

	// --------------------------------------------------- main() ---
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				System.out.println("yes in in main for chat");

				// //---this sets the chat to visible---
				try {
					// window.checkConnectionWindow.setVisible(false);
					chat window = new chat(mainApp,id, connection);
					window.chatWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// --------------------------------------------------- Constructor() ---
	/**
	 * @wbp.parser.constructor
	 */
	public chat(MainAppGui mainApp, String id, XMPPConnection connection) {

		initialize(mainApp);
        //changed
		//getsMessage e = new getsMessage();
		//btnSubmit.addActionListener(e);
		this.connection = connection;
		this.id = id;
		setId(id);
		ChatManager chatmanager = connection.getChatManager();
		chat2 = chatmanager.createChat(id, new MessageListener() {

			public void processMessage(Chat chat, Message message) {

				// this is the information which is printed out for
				// the
				// user to see

				FriendMessage = "\n[" + message.getFrom().split("@")[0] + "] "
						+ message.getBody();
				// System.out.println("this is the friend message");
				System.out.println(FriendMessage);
				history.add(FriendMessage);
				System.out.println(history);
				messageHistory.append(FriendMessage);

			}
		});
	}
	
	public String getId () {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	// --------------------------------------------------- Constructor() 2 ---
	public chat(MainAppGui mainApp, String id) {

		// ------------------------login in to Google chat--

		// login code for checking to make sure someone logs in
		// correctly

		//ConnectionConfiguration config = new ConnectionConfiguration(
		//		"talk.google.com", 5222, "gmail.com");
		//connection = new XMPPConnection(config);
		// try {
		// System.out.println("Trying to connect...");
		// connection.connect();
		//
		// System.out.println("Trying to login...");
		// connection.login("slmendis@gmail.com", "pendragon15");
		// // See if you are authenticated
		// System.out.println(" Login successful? "
		// + connection.isAuthenticated());
		//
		// } catch (XMPPException e1) {
		// System.out.println("somthing went wrong");
		// }

		// ------------------------connected friends--

		// this connects a person with another person via chat
		
		setId(id);
		ChatManager chatmanager = connection.getChatManager();
		chat2 = chatmanager.createChat(id, new MessageListener() {

			public void processMessage(Chat chat, Message message) {

				// this is the information which is printed out for
				// the
				// user to see

				FriendMessage = "\n[" + message.getFrom().split("@")[0] + "] "
						+ message.getBody();
				// System.out.println("this is the friend message");
				System.out.println(FriendMessage);
				history.add(FriendMessage);
				System.out.println(history);
				messageHistory.append(FriendMessage);

			}

		});

		initialize(mainApp);

//		getsMessage e = new getsMessage();
//		btnSubmit.addActionListener(e);

	}

	// --------------------------------------------------- initialize() ---
	private void initialize(MainAppGui mainApp) {

		this.mainApp = mainApp;

		// -------------------------- chatframe() ---
		chatWindow = new JFrame();
		chatWindow.getContentPane().setBackground(new Color(173, 216, 230));
		chatWindow.setTitle("Friends Name");
		chatWindow.setBounds(100, 100, 245, 450);
		chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chatWindow.getContentPane().setLayout(null);

		// -------------------------- message history ---
		messageHistory = new JTextArea();
		messageHistory.setToolTipText("all messages will be displayed here");
		messageHistory.setLineWrap(true);
		messageHistory.setWrapStyleWord(true);

		scrollPane1 = new JScrollPane(messageHistory);
		scrollPane1
				.setToolTipText("Program-generated messages will be displayed here");
		scrollPane1.setBounds(5, 25, 220, 300);
		scrollPane1
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chatWindow.getContentPane().add(scrollPane1);

		JLabel lblMessages = new JLabel("Message History");
		lblMessages.setBounds(65, 5, 100, 10);
		chatWindow.getContentPane().add(lblMessages);

		// -------------------------- message box ---

		messageBox = new JTextArea();
		messageBox.setToolTipText("write your message here");
		messageBox.setLineWrap(true);
		messageBox.setWrapStyleWord(true);
		

		scrollPane2 = new JScrollPane(messageBox);
		scrollPane2
				.setToolTipText("Program-generated messages will be displayed here");
		scrollPane2.setBounds(5, 330, 220, 50);
		scrollPane2
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chatWindow.getContentPane().add(scrollPane2);

		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(65, 391, 89, 23);
		chatWindow.getContentPane().add(btnSubmit);
		btnSubmit.addActionListener(new getsMessage());

	}
	//changed

//	private class meMessage implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			meMessage = "[me] " + messageBox.getText();
//
//		}

//	}

	// ----gets text from messageBox and adds it to messageHistory---
	private class getsMessage implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// System.out.println("code here");
			String user = connection.getUser();
			user = user.substring(0,user.indexOf(".com"));
			meMessage = user + ": "+ messageBox.getText() + "\n";

			String meMessage2 = ("[me] " + meMessage);
			System.out.println(meMessage2);
			addToHistory(meMessage2);

			Message msg;

			String name2 = connection.getUser();
			int i = name2.indexOf("/");
			name2 = name2.substring(0, i);
			System.out.println(name2);
			try {

				// this takes in a message from the scanner, puts it into a
				// message format and then sends it out to chat
				msg = new Message(name2, Message.Type.chat);

				msg.setBody(meMessage);
				

				chat2.sendMessage(msg);

			} catch (XMPPException f) {
				System.out.println("Failed to send message");
			}

			messageHistory.append( meMessage); 
			
			messageBox.setText("");

		}

	}

	// ----------adds the text from the messageBox to history---
	public void addToHistory(String text) {

		try {
			history.add(text);
			System.out.println(history);
		} catch (NullPointerException e) {
			System.out
					.println("null pointer was caught when adding to history");
		}
	}
	// public void setVisible(chat newChat) {
	// try {
	// // window.checkConnectionWindow.setVisible(false);
	// n.chatWindow.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
}
