/*
 * Names: Margaret Orr, Sam Mendis, Garbo Cheng Ye, Eric Johnston
 * Computing Ids: mmo7kd, slm7kd, gc2bt, etj5ne
 * Lab Section: 102
 * Group Number: 38
 */

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

import java.util.*;
import java.awt.SystemColor;
import java.awt.Color;

public class FriendList extends JFrame {

	private JFrame frmFriendlist;
	private MainAppGui mainApp;
	public static XMPPConnection connection;
	public Presence presence;
	private JTextField nameField;
	private JButton btnRemoveFriend;
	private JButton btnAddFriend;
	private JButton btnRefresh;
	private JButton Chat;
	private JTextArea textArea;
	public String name;
	public HashMap<String, String> friendlist = new HashMap<String, String>();

	// Launch the application:
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FriendList window = new FriendList(null, connection);
					window.frmFriendlist.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Create the application:
	public FriendList(MainAppGui mainApp, XMPPConnection connection) {
		this.connection = connection;
		initialize(mainApp);
	}

	// Initialize frame contents:
	private void initialize(final MainAppGui mainApp) {

		// Create the JFrame:
		this.mainApp = mainApp;
		frmFriendlist = new JFrame();
		frmFriendlist.getContentPane().setBackground(new Color(0, 128, 128));
		frmFriendlist.setTitle("FriendList");
		frmFriendlist.setBounds(100, 100, 349, 360);
		frmFriendlist.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFriendlist.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 11, 288, 227);
		frmFriendlist.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		nameField = new JTextField();
		nameField.setBounds(20, 250, 197, 20);
		frmFriendlist.getContentPane().add(nameField);
		nameField.setColumns(10);

		Chat = new JButton("Chat");
		Chat.setBounds(235, 249, 73, 23);
		frmFriendlist.getContentPane().add(Chat);
		Chat.addActionListener(new chatWith());

		btnAddFriend = new JButton("Add Friend");
		btnAddFriend.setBounds(10, 281, 89, 25);
		frmFriendlist.getContentPane().add(btnAddFriend);
		btnAddFriend.addActionListener(new add());

		btnRemoveFriend = new JButton("Remove Friend");
		btnRemoveFriend.setBounds(109, 281, 116, 25);
		frmFriendlist.getContentPane().add(btnRemoveFriend);
		btnRemoveFriend.addActionListener(new remove());

		btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(235, 283, 89, 24);
		frmFriendlist.getContentPane().add(btnRefresh);
		btnRefresh.addActionListener(new refresh());

		printRoster();

	}

	// Called when refresh button is pressed:
	class refresh implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			textArea.enable(true);
			textArea.setText("");
			printRoster();
		}
	}

	// Called when add button is pressed:
	class add implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			// pop up add frame
			Add1 frame = new Add1(mainApp, connection);
			frame.main(null);

		}
	}

	// Called when add button is pressed:
	class remove implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			// pop up remove frame
			Remove1 frame = new Remove1(mainApp, connection, friendlist);
			frame.main(null);

		}
	}

	// Called when chat button has been pressed
	class chatWith implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String name = nameField.getText();
			String id = friendlist.get(name);
			if (name.contains("@")) {
				id = name;
				name = id.split("@")[0];
			}
			ChatWindow2 frame = new ChatWindow2(mainApp, id, name, connection);
			frame.chatWindow.setVisible(true);
			nameField.setText("");

		}

	}

	// Prints roster of friends, including presence:
	public void printRoster() {
		Roster roster = connection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (RosterEntry entry : entries) {
			String user = entry.getUser();
			String name2 = entry.getName();
			presence = roster.getPresence(entry.getUser());
			String availability;
			if (presence.getStatus() == null)
				availability = "OFFLINE";
			else
				availability = " ONLINE ";

			textArea.append("[" + availability + "] " + entry.getName() + "\n");
			friendlist.put(entry.getName(), entry.getUser());
		}
	}
}
