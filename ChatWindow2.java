/*
 * Names: Margaret Orr, Sam Mendis, Garbo Cheng Ye, Eric Johnston
 * Computing Ids: mmo7kd, slm7kd, gc2bt, etj5ne
 * Lab Section: 102
 * Group Number: 38
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import java.awt.Font;

public class ChatWindow2 extends JFrame {

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
	private Chat chat1;
	private static MainAppGui mainApp;
	public String name;
	private String id2;
	private String id3;
	public JButton btnSaveMessagesAnd;
	public static XMPPConnection connection;

	// Main method:
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			}
		});
	}

	// Constructor:
	public ChatWindow2(MainAppGui mainApp, final String id, final String name,
			XMPPConnection connection) {

		this.id2 = id;
		this.name = name;
		this.id3 = connection.getUser().split("/")[0];

		this.connection = connection;
		// this connects a person with another person via chat
		try {
			ChatManager chatmanager = connection.getChatManager();
			chat1 = chatmanager.createChat(id, new MessageListener() {

				public void processMessage(Chat chat1, Message message) {

					FriendMessage = "\n[" + name + "] " + message.getBody();
					history.add(FriendMessage);
					messageHistory.setForeground(Color.black);
					messageHistory.append(FriendMessage);
				}

			});

		} catch (Exception e) {
			System.out.println("the chat could not send the message");
		}
		this.mainApp = mainApp;
		this.chat1 = chat1;

		initialize(mainApp);

		try {
			getHistoryFromFile(id2, id3);
		} catch (IOException e1) {
			System.out.println("history was not gotten fromt the file");
		}

		getsMessage e = new getsMessage();
		btnSubmit.addActionListener(e);

		saveAndClose f = new saveAndClose();
		btnSaveMessagesAnd.addActionListener(f);

	}

	private void initialize(MainAppGui mainApp) {

		this.mainApp = mainApp;

		// Chatframe:
		chatWindow = new JFrame();
		chatWindow.getContentPane().setBackground(new Color(0, 128, 128));
		chatWindow.setTitle("Friends Name");
		chatWindow.setBounds(100, 100, 245, 498);
		chatWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		chatWindow.getContentPane().setLayout(null);

		// Message History:
		messageHistory = new JTextArea();
		messageHistory.setToolTipText("all messages will be displayed here");
		messageHistory.setLineWrap(true);
		messageHistory.setWrapStyleWord(true);

		// First Scroll Pane:
		scrollPane1 = new JScrollPane(messageHistory);
		scrollPane1
				.setToolTipText("Program-generated messages will be displayed here");
		scrollPane1.setBounds(5, 25, 220, 300);
		scrollPane1
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chatWindow.getContentPane().add(scrollPane1);

		JLabel lblMessages = new JLabel(name);
		lblMessages.setFont(new Font("Sylfaen", Font.BOLD, 14));
		lblMessages.setForeground(new Color(0, 0, 0));
		lblMessages.setBounds(5, 0, 89, 23);
		chatWindow.getContentPane().add(lblMessages);

		// Message Box:
		messageBox = new JTextArea();
		messageBox.setToolTipText("write your message here");
		messageBox.setLineWrap(true);
		messageBox.setWrapStyleWord(true);

		// Second Scroll Pane:
		scrollPane2 = new JScrollPane(messageBox);
		scrollPane2
				.setToolTipText("Program-generated messages will be displayed here");
		scrollPane2.setBounds(5, 330, 220, 50);
		scrollPane2
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chatWindow.getContentPane().add(scrollPane2);

		// Submit Button:
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(76, 392, 89, 23);
		chatWindow.getContentPane().add(btnSubmit);

		// Save and Close Button:
		btnSaveMessagesAnd = new JButton("Save Messages and Close Window");
		btnSaveMessagesAnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnSaveMessagesAnd.setBounds(10, 426, 209, 23);
		chatWindow.getContentPane().add(btnSaveMessagesAnd);
		this.chatWindow.setVisible(true);
	}

	// Gets text from messageBox and adds it to messageHistory:
	private class getsMessage implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			meMessage = messageBox.getText();

			String meMessage2 = ("[me] " + meMessage);

			addToHistory(meMessage2);

			Message msg;
			try {

				String name = connection.getUser();
				int i = name.indexOf("/");
				name = name.substring(0, i);
				msg = new Message(name, Message.Type.chat);

				msg.setBody(meMessage);

				chat1.sendMessage(msg);

			} catch (XMPPException f) {
				System.out.println("Failed to send message");
			}

			messageHistory.append("\n" + "[me] " + meMessage);
			messageHistory.setForeground(Color.blue);
			messageBox.setText("");

		}

	}

	// Adds history from individual chats to total history:
	private class saveAndClose implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			try {
				addHistorytoFile(history, id2, id3);
			} catch (IOException e1) {
				System.out.println("chat history was not added");
				e1.printStackTrace();
			}
			chatWindow.setVisible(false);

		}
	}

	// Adds the text from the messageBox to history:
	public void addToHistory(String text) {

		try {
			history.add(text);
		} catch (NullPointerException e) {
			System.out
					.println("null pointer was caught when adding to history");
		}
	}

	public void addHistorytoFile(ArrayList<String> history, String id2,
			String id3) throws IOException {

		Writer out = new OutputStreamWriter(new FileOutputStream(
				"SavedHistory1.txt", true));
		try {

			out.write("\n" + id2 + id3 + "StartsHere");
			for (int i = 0; i < history.size(); i++) {
				out.write("\n" + history.get(i));
			}

			out.write("\n" + id2 + id3 + "EndsHere");
		} finally {
			out.close();
		}

	}

	public void getHistoryFromFile(String id2, String id3) throws IOException {
		StringBuilder text = new StringBuilder();
		String NL = System.getProperty("line.separator");
		Scanner scanner = new Scanner(new FileInputStream("SavedHistory1.txt"));
		try {
			ArrayList<String> list = new ArrayList<String>();

			while (scanner.hasNextLine()) {
				String text1 = scanner.nextLine();
				if (!text1.equals(""))
					list.add(text1);
			}

			for (int i = 0; i < list.size(); i++) {

				if (list.get(i).equals(id2 + id3 + "StartsHere")) {
					i++;

					while (!(list.get(i).equals(id2 + id3 + "EndsHere"))) {
						messageHistory.append("\n" + list.get(i));
						i++;
					}
				}
			}

		} catch (Exception e) {
			System.out.println("could not get from file");
		}
	}
}
