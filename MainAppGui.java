import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import org.jivesoftware.smack.XMPPConnection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainAppGui {

    public JFrame frmMainWindow; // change to public if you want other windows to access it
	private Add1 add1;
	private Remove1 remove1;
	private Login login;
	private FriendList friendlist;
	private JTextField window2TextField;
//	private JTextField window1TextField;
	private JTextField window1TextField;
	private JScrollPane scrollPane ;
	private JTextField textFieldLoginName;
	private JTextArea logMsgTextArea;
	private String user = "";
	private String password = "";
	public XMPPConnection connection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainAppGui window = new MainAppGui();
					// try this: comment out following line and uncomment lines in constructor
//					window.frmMainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainAppGui() {
		initialize();
// try this: comment out call to setVisible() in main and uncomment these
		if ( login == null)
			login = new Login(this); // pass ref to MainAppGui object
//		login.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMainWindow = new JFrame();
		frmMainWindow.setTitle("Main Window");
		frmMainWindow.setBounds(100, 100, 371, 453);
		frmMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainWindow.getContentPane().setLayout(null);
		
		logMsgTextArea = new JTextArea();
		logMsgTextArea.setToolTipText("Program-generated messages will be displayed here");
        logMsgTextArea.setLineWrap(true);
        logMsgTextArea.setWrapStyleWord(true);
        
		scrollPane = new JScrollPane(logMsgTextArea);
		scrollPane.setToolTipText("Program-generated messages will be displayed here");
		scrollPane.setBounds(20, 230, 320, 135);
		scrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frmMainWindow.getContentPane().add(scrollPane);
		
		JLabel lblMessages = new JLabel("Log Messages:");
		lblMessages.setBounds(23, 202, 114, 16);
		frmMainWindow.getContentPane().add(lblMessages);
		
		JButton btnOpenWindow2 = new JButton("Open Window2");
		btnOpenWindow2.setToolTipText("Click here to open data-input window");
		btnOpenWindow2.addActionListener(new OpenWindow2ActionListener());
		btnOpenWindow2.setBounds(20, 149, 145, 29);
		frmMainWindow.getContentPane().add(btnOpenWindow2);
		
		JLabel lblWindow2Data = new JLabel("Window2 data:");
		lblWindow2Data.setBounds(177, 154, 104, 16);
		frmMainWindow.getContentPane().add(lblWindow2Data);
		
		window2TextField = new JTextField();
		window2TextField.setEditable(false);
		window2TextField.setBounds(177, 173, 163, 28);
		frmMainWindow.getContentPane().add(window2TextField);
		window2TextField.setColumns(10);
		
		JButton buttonOpenWindow1 = new JButton("Open Window1");
		buttonOpenWindow1.setToolTipText("Click here to open data-input window");
		buttonOpenWindow1.addActionListener(new OpenWindow1ActionListener());
		buttonOpenWindow1.setBounds(20, 97, 145, 29);
		frmMainWindow.getContentPane().add(buttonOpenWindow1);
		
		window1TextField = new JTextField();
		window1TextField.setEditable(false);
		window1TextField.setBounds(177, 121, 163, 28);
		frmMainWindow.getContentPane().add(window1TextField);
		window1TextField.setColumns(10);
		
		JLabel lblWindow1Data = new JLabel("Window1 data:");
		lblWindow1Data.setBounds(177, 102, 104, 16);
		frmMainWindow.getContentPane().add(lblWindow1Data);
		
		JButton btnCloseAll = new JButton("Close All");
		btnCloseAll.setToolTipText("Close any open windows");
		btnCloseAll.addActionListener(new CloseAllActionListener());
		btnCloseAll.setBounds(20, 377, 117, 29);
		frmMainWindow.getContentPane().add(btnCloseAll);
		
		textFieldLoginName = new JTextField();
		textFieldLoginName.setEditable(false);
		textFieldLoginName.setBounds(177, 62, 134, 28);
		frmMainWindow.getContentPane().add(textFieldLoginName);
		textFieldLoginName.setColumns(10);
		
		JLabel lblLoggedInAs = new JLabel("Logged In As:");
		lblLoggedInAs.setBounds(23, 69, 132, 16);
		frmMainWindow.getContentPane().add(lblLoggedInAs);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setToolTipText("Stop the program");
		exitButton.addActionListener(new ExitActionListener());
		exitButton.setBounds(165, 377, 117, 29);
		frmMainWindow.getContentPane().add(exitButton);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setToolTipText("First click here to login");
		btnLogin.addActionListener(new LoginActionListener());
		btnLogin.setBounds(20, 18, 117, 29);
		frmMainWindow.getContentPane().add(btnLogin);
	}

	public void setWindow1Data(String input) {
		this.window1TextField.setText(input);
		this.frmMainWindow.repaint();
		logMessage("MainAppGui: window1 data updated.");
	}
	
	public void setWindow2Data(String input) {
		this.window2TextField.setText(input);
		this.frmMainWindow.repaint();
		logMessage("MainAppGui: window1 data updated.");
	}

	public void logMessage(String msg) {
		if ( logMsgTextArea != null ) {
			this.logMsgTextArea.append(msg + "\n");
			this.frmMainWindow.repaint();
		}
	}

	public void setLoginInfo(String user, String passStr) {
		this.user = user;
		this.password = passStr;
		this.textFieldLoginName.setText(user);
		this.frmMainWindow.repaint();
		logMessage("MainAppGui: login info stored.");
	}


    private final class LoginActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if ( login == null)
                login = new Login(MainAppGui.this); 
            login.setVisible(true);
        }
    }

    private final class ExitActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private final class CloseAllActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if ( add1 != null )
                add1.setVisible(false);
            if ( remove1 != null )
                remove1.setVisible(false);
            if ( login != null )
                login.setVisible(false);
            logMessage("MainAppGui: closing any open windows.");
        }
    }

    private final class OpenWindow1ActionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            if ( user.length() == 0 ) {
                logMessage("MainAppGui: ERROR! Must login first!");
                return;
            }
            if ( add1 == null)
                add1 = new Add1(MainAppGui.this, connection); 
            add1.setVisible(true);
        }
    }

    private final class OpenWindow2ActionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            if ( user.length() == 0 ) {
                logMessage("MainAppGui: ERROR! Must login first!");
                return;
            }
            if ( remove1 == null)
                remove1 = new Remove1(MainAppGui.this, null, null); 
            remove1.setVisible(true);
        }
    }


}
