package presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bll.UserBLL;
import model.ImagePanel;
import model.User;

public class StartGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	ImagePanel panel = new ImagePanel(new ImageIcon("background.jpg").getImage());

	private JTextField usernameText = new JTextField("username@gmail.com");
	private JTextField passwordText = new JPasswordField("password");	

	private JButton loginButton = new JButton("Login");
	private JButton guestButton = new JButton("Sign-in as guest");
	private JButton createAccountButton = new JButton("Create account");

	private String username, password;
	private User user;
	
	public StartGUI() {
		initializeFrame();
		addComponents();
		initializeComponents();
		addListeners();
	}

	public void initializeFrame() {
		setTitle("Word Processor");
		setSize(new Dimension(305, 405));
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(panel);
	}

	public void initializeComponents() {
		loginButton.setBounds(50, 170, 200, 50);
		guestButton.setBounds(50, 240, 200, 30);
		createAccountButton.setBounds(50, 290, 200, 50);

		usernameText.setBounds(50, 50, 200, 50);
		usernameText.setHorizontalAlignment(SwingConstants.CENTER);

		passwordText.setBounds(50, 100, 200, 50);
		passwordText.setHorizontalAlignment(SwingConstants.CENTER);
	}

	public void addComponents() {
		panel.setLayout(null);
		panel.add(usernameText);
		panel.add(passwordText);
		panel.add(loginButton);
		panel.add(guestButton);
		panel.add(createAccountButton);
	}

	public void getContent() {
		this.username = usernameText.getText();  
		this.password = passwordText.getText();
	}

	public boolean accessAccount() {
		this.user = new User(this.username, this.password);
		UserBLL userBLL = new UserBLL();
		return userBLL.isUser(user);
	}

	public void addListeners() {
		createAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateAccountGUI createAccountGUI;
				createAccountGUI = new CreateAccountGUI();
				createAccountGUI.setVisible(true);
			}
		});

		guestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User guest = new User("guest", "guest", "guest", "guest"); 
				LoginGUI loginGUI;
				try {
					loginGUI = new LoginGUI(guest);
					loginGUI.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContent();
				accessAccount();
				LoginGUI loginGUI;
				if(accessAccount() == true) {
					try {
						loginGUI = new LoginGUI(user);
						loginGUI.setVisible(true);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
	}

	public static void main(String[] args) {
		new StartGUI();
	}
}
