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
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;




public class Remove1 extends JFrame{

	private JFrame frmRemove;
	private JTextField nameField;
	private JFrame remove2;
	private static MainAppGui mainApp;
	public static XMPPConnection connection;
	public Roster roster;
	public  Collection<RosterEntry> entries;
	public static HashMap<String,String> friendlist = new HashMap<String,String>(); 

	//Launch application:
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Remove1 window = new Remove1(mainApp, connection, friendlist);
					window.frmRemove.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Create application:
	public Remove1(MainAppGui mainApp, XMPPConnection connection, HashMap<String,String> friendlist) {
		this.connection = connection;
		this.friendlist = friendlist;
		initialize(mainApp);
	}

	// Initialize JFrame:
	private void initialize(MainAppGui mainApp) {
		this.mainApp = mainApp;
		frmRemove = new JFrame();
		frmRemove.setTitle("Remove1");
		frmRemove.setBounds(100, 100, 205, 146);
		frmRemove.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frmRemove.getContentPane().setLayout(null);

		JLabel lblWhatIsYour = new JLabel("What is your friends User id?");
		lblWhatIsYour.setBounds(10, 21, 169, 14);
		frmRemove.getContentPane().add(lblWhatIsYour);

		nameField = new JTextField();
		nameField.setBounds(21, 46, 142, 20);
		frmRemove.getContentPane().add(nameField);
		nameField.setColumns(10);
		nameField.addActionListener(new HandleSubmitPressed());

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(48, 77, 89, 23);
		frmRemove.getContentPane().add(btnSubmit);
		btnSubmit.addActionListener(new HandleSubmitPressed());
		
		 roster = connection.getRoster();
	     Collection<RosterEntry> entries = roster.getEntries();
	}
	
	// Called when Submit button is pressed:
	class HandleSubmitPressed implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			checkUserMatch();
		    try {
				roster.removeEntry(roster.getEntry(friendlist.get(nameField.getText())));
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
		
		private void checkUserMatch(){
			 //display the name (not id) of removed friend
            if(!friendlist.containsKey(nameField.getText())){
            	frmRemove.setVisible(false);
			JOptionPane.showMessageDialog(remove2,
					nameField.getText() + 
					" is not in your friendlist", "remove2",
					JOptionPane.PLAIN_MESSAGE);
		}
			else{
				frmRemove.setVisible(false);
				JOptionPane.showMessageDialog(remove2,
						nameField.getText() + " has " +
						"been removed from your friendlist", "remove2",
						JOptionPane.PLAIN_MESSAGE);
			}

		}
	}
}