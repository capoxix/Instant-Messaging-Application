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
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.awt.Color;
import java.awt.Font;

public class Login extends JFrame {
	// Swing components as fields:
	private JFrame frmLogin;
	private JLabel nameLabel;
	private JTextField nameTF;
	private JTextField password;
	private JButton submitBtn;
	private JTextField outputTF;
	private TreeMap<String, String> userPW = new TreeMap<String, String>();
	public XMPPConnection connection;
	private MainAppGui mainApp;
	public Roster roster;
	public String name;
	private JButton button;
	private JButton btnNewButton;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JButton button_5;
	private JLabel label_2;
	private JLabel lblPassword;
	private Chat chat1;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Login frame = new Login(null);
			}
		});
	}

	// Constructor:
	public Login(MainAppGui mainApp) {
		initialize(mainApp);
	}

	private void initialize(MainAppGui mainApp) {
		this.mainApp = mainApp;
		userPW.put("test", "correct");

		// Configure JFrame:
		frmLogin = new JFrame();
		frmLogin.getContentPane().setBackground(new Color(0, 128, 128));
		frmLogin.setSize(282, 529);
		frmLogin.setTitle("Login");
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);

		// JLabel to label the input text-field:
		nameLabel = new JLabel("Username:");
		nameLabel.setFont(new Font("Elephant", Font.ITALIC, 14));
		nameLabel.setBounds(93, 27, 83, 14);
		frmLogin.getContentPane().add(nameLabel);

		// JTextField for input:
		nameTF = new JTextField(10);
		nameTF.setBounds(65, 41, 135, 20);
		frmLogin.getContentPane().add(nameTF);
		password = new JPasswordField(10);
		password.setBounds(65, 86, 138, 20);
		password.addActionListener(new HandleSubmitPressed());
		frmLogin.getContentPane().add(password);

		// Submit Button:
		submitBtn = new JButton("Submit");
		submitBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		submitBtn.setBounds(83, 117, 93, 23);
		frmLogin.getContentPane().add(submitBtn);
		submitBtn.addActionListener(new HandleSubmitPressed());

		// JTextField:
		outputTF = new JTextField(30);
		outputTF.setEditable(false);
		outputTF.setBounds(10, 189, 246, 20);
		frmLogin.getContentPane().add(outputTF);

		// Create new user Button:
		JButton btnCreateANew = new JButton("Create a new user");
		btnCreateANew.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCreateANew.setBounds(38, 155, 190, 23);
		frmLogin.getContentPane().add(btnCreateANew);

		button = new JButton("");
		button.setBackground(new Color(0, 0, 0));
		button.setForeground(new Color(255, 0, 255));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setBounds(74, 272, 118, 23);
		frmLogin.getContentPane().add(button);

		btnNewButton = new JButton("");
		btnNewButton.setBackground(new Color(0, 0, 0));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(73, 298, 20, 62);
		frmLogin.getContentPane().add(btnNewButton);

		button_1 = new JButton("");
		button_1.setBackground(new Color(0, 0, 0));
		button_1.setBounds(170, 298, 20, 62);
		frmLogin.getContentPane().add(button_1);

		button_2 = new JButton("");
		button_2.setBackground(new Color(0, 0, 0));
		button_2.setBounds(108, 298, 20, 62);
		frmLogin.getContentPane().add(button_2);

		button_3 = new JButton("");
		button_3.setBackground(new Color(0, 0, 0));
		button_3.setBounds(141, 298, 19, 62);
		frmLogin.getContentPane().add(button_3);

		button_4 = new JButton("");
		button_4.setForeground(Color.MAGENTA);
		button_4.setBackground(Color.BLACK);
		button_4.setBounds(93, 256, 83, 20);
		frmLogin.getContentPane().add(button_4);

		button_5 = new JButton("");
		button_5.setForeground(Color.MAGENTA);
		button_5.setBackground(Color.BLACK);
		button_5.setBounds(109, 243, 51, 14);
		frmLogin.getContentPane().add(button_5);

		JLabel lblWelcomeToUva = new JLabel("Welcome to UVA Chat");
		lblWelcomeToUva.setForeground(new Color(0, 0, 0));
		lblWelcomeToUva.setBackground(new Color(255, 255, 255));
		lblWelcomeToUva.setFont(new Font("Elephant", Font.ITALIC, 16));
		lblWelcomeToUva.setBounds(38, 361, 190, 25);
		frmLogin.getContentPane().add(lblWelcomeToUva);

		JLabel lblCreatedBy = new JLabel("Created By:");
		lblCreatedBy.setForeground(Color.BLACK);
		lblCreatedBy.setFont(new Font("Elephant", Font.ITALIC, 11));
		lblCreatedBy.setBackground(Color.WHITE);
		lblCreatedBy.setBounds(93, 379, 90, 25);
		frmLogin.getContentPane().add(lblCreatedBy);

		JLabel lblGarbo = new JLabel("Sam Mendis");
		lblGarbo.setForeground(Color.BLACK);
		lblGarbo.setFont(new Font("Elephant", Font.PLAIN, 10));
		lblGarbo.setBackground(Color.WHITE);
		lblGarbo.setBounds(141, 415, 93, 14);
		frmLogin.getContentPane().add(lblGarbo);

		JLabel lblEricJohnston = new JLabel("Eric Johnston");
		lblEricJohnston.setForeground(Color.BLACK);
		lblEricJohnston.setFont(new Font("Elephant", Font.PLAIN, 10));
		lblEricJohnston.setBackground(Color.WHITE);
		lblEricJohnston.setBounds(38, 437, 93, 20);
		frmLogin.getContentPane().add(lblEricJohnston);

		JLabel lblMarggieOrr = new JLabel("Marggie Orr");
		lblMarggieOrr.setForeground(Color.BLACK);
		lblMarggieOrr.setFont(new Font("Elephant", Font.PLAIN, 10));
		lblMarggieOrr.setBackground(Color.WHITE);
		lblMarggieOrr.setBounds(141, 440, 69, 14);
		frmLogin.getContentPane().add(lblMarggieOrr);

		label_2 = new JLabel("Garbo Cheng Ye");
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Elephant", Font.PLAIN, 10));
		label_2.setBackground(Color.WHITE);
		label_2.setBounds(35, 415, 93, 13);
		frmLogin.getContentPane().add(label_2);

		lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Elephant", Font.ITALIC, 14));
		lblPassword.setBounds(93, 73, 83, 14);
		frmLogin.getContentPane().add(lblPassword);
		btnCreateANew.addActionListener(new CreateUser());

		frmLogin.setVisible(true);
	}

	// Called when Submit button is pushed:
	class HandleSubmitPressed implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			tryLogin();
		}
	}

	// Method that user uses to log in:
	private void tryLogin() {
		name = nameTF.getText().trim();
		String pw = password.getText().trim();
		ConnectionConfiguration config = new ConnectionConfiguration(
				"talk.google.com", 5222, "gmail.com");
		connection = new XMPPConnection(config);
		try {
			connection.connect();
			connection.login(name, pw);
			// See if you are authenticated
			if (connection.isAuthenticated()) {
				outputTF.setText("correct password!");

				roster = connection.getRoster();
				Collection<RosterEntry> entries = roster.getEntries();
				FriendList frame = new FriendList(mainApp, connection);
				frame.main(null);
				frmLogin.setVisible(false);

				PopUpChat();

			}

		} catch (XMPPException e1) {
			nameTF.setText("");
			password.setText("");
			outputTF.setText("incorrect username/password");

			e1.printStackTrace();
		}
	}

	// Creates a new user when proper button is pushed:
	class CreateUser implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			try {
				Process p = Runtime
						.getRuntime()
						.exec("cmd /c start https://"
								+ "accounts.google.com/SignUp?servic"
								+ "e=mail&continue=http%3A%2F%2Fmail.google.com"
								+ "%2Fmail%2Fe-11-1c8f6d3fe3b620c914434838484e93-3c"
								+ "48c73f45b96fa2038327cd07875a57cfbc2d38");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void addHistorytoFile() {
		File file1 = new File("historyOfConversations.txt");
		try {
			Scanner reader = new Scanner(file1);
		} catch (FileNotFoundException e) {
			System.out.println("this cant read the file");
			e.printStackTrace();
		}
	}

	public void PopUpChat() {
		connection.getChatManager().addChatListener(new ChatManagerListener() {
			public void chatCreated(Chat chat, boolean local) {
				String speaker;
				speaker = chat.getParticipant();
				chat1 = chat;

				if (speaker.contains("/")) {
					speaker = speaker.substring(0, speaker.indexOf("/"));
				}

				if ((!local)) {

					ChatWindow2 chatWindow = new ChatWindow2(mainApp, speaker,
							speaker, connection);
					try {
						chat1.sendMessage("*Chat has initialized:* Please send your first message");
					} catch (XMPPException e1) {
						e1.printStackTrace();
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					chatWindow.main(null);

				}
			}

		});

	}
}
