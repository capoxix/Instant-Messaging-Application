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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Add1 extends JFrame {

	private JFrame frmAdd;
	private JTextField userIdField;
	private JFrame add2;
	private MainAppGui mainApp;
	public static XMPPConnection connection;
	public Roster roster;
	private JTextField nickname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add1 window = new Add1(null, connection);
					window.frmAdd.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Add1(MainAppGui mainApp, XMPPConnection connection) {
		this.connection = connection;
		initialize(mainApp);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(MainAppGui mainApp) {
		this.mainApp = mainApp;
		frmAdd = new JFrame();
		frmAdd.setTitle("add1");
		frmAdd.setBounds(100, 100, 282, 208);
		frmAdd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frmAdd.getContentPane().setLayout(null);

		JLabel lblWhatIsYour = new JLabel("What is your friends User id?");
		lblWhatIsYour.setBounds(10, 21, 246, 14);
		frmAdd.getContentPane().add(lblWhatIsYour);

		userIdField = new JTextField();
		userIdField.setBounds(10, 46, 246, 20);
		frmAdd.getContentPane().add(userIdField);
		userIdField.setColumns(10);
		userIdField.addActionListener(new HandleSubmitPressed());

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(73, 136, 89, 23);
		frmAdd.getContentPane().add(btnSubmit);

		nickname = new JTextField();
		nickname.setColumns(10);
		nickname.setBounds(10, 105, 246, 20);
		frmAdd.getContentPane().add(nickname);

		JLabel lblWhatWouldYou = new JLabel(
				"What would you like their name to appear as?");
		lblWhatWouldYou.setBounds(10, 77, 246, 14);
		frmAdd.getContentPane().add(lblWhatWouldYou);
		btnSubmit.addActionListener(new HandleSubmitPressed());

		roster = connection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();

	}

	class HandleSubmitPressed implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String[] a = new String[10];
			a[0] = "recently added";
			try {
				roster.createEntry(userIdField.getText(), nickname.getText(), a);
			} catch (XMPPException e) {
				e.printStackTrace();
			}
			checkUserMatch();
		}

		private void checkUserMatch() {
			frmAdd.setVisible(false);
			// display the name (not id) of added friend
			JOptionPane.showMessageDialog(add2, userIdField.getText() + " has "
					+ "been added to your friendlist", "add2",
					JOptionPane.PLAIN_MESSAGE);
		}
	}
}
