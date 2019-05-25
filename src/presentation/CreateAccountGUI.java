package presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bll.UserBLL;
import model.ImagePanel;
import model.User;

public class CreateAccountGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	ImagePanel panel = new ImagePanel(new ImageIcon("background.jpg").getImage());

	Font sdProjectFont = new Font("SansSerif", Font.BOLD, 24);
	Font createAccountFont = new Font("SansSerif", Font.BOLD, 18);

	private JButton createAccountButton = new JButton("Create account");

	private JLabel sdProjectLabel = new JLabel("SDProject");
	private JLabel createAccountLabel = new JLabel("Create a SDProject Account");
	private JLabel accountIndicationsLabel = new JLabel ("You can use Latin letters, numbers and dots.");
	private JLabel passwordIndicationsLabel = new JLabel ("<html>The password must contain at least eight characters, including letters,<br>numbers and special characters.</html>");

	private JTextField nameText = new JTextField("name");
	private JTextField surnameText = new JTextField("surname");	
	private JTextField usernameText = new JTextField("username@gmail.com");
	private JTextField passwordText = new JPasswordField("password");
	private JTextField confirmText = new JPasswordField("confirm");

	private String name, surname, username, password, confirm;

	public CreateAccountGUI() {
		initializeFrame();
		addComponents();
		initializeComponents();
		addListeners();
	}

	public void initializeFrame() {
		setTitle("Word Processor - Create account");
		setSize(new Dimension(530, 530));
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		add(panel);
	}

	public void initializeComponents() {
		sdProjectLabel.setBounds(50, 50, 400, 30);
		sdProjectLabel.setFont(sdProjectFont);
		sdProjectLabel.setForeground(Color.RED);

		createAccountLabel.setBounds(50, 100, 400, 30);
		createAccountLabel.setFont(createAccountFont);

		nameText.setBounds(50, 150, 200, 50);
		nameText.setHorizontalAlignment(SwingConstants.CENTER);

		surnameText.setBounds(275, 150, 200, 50);
		surnameText.setHorizontalAlignment(SwingConstants.CENTER);

		usernameText.setBounds(50, 225, 425, 50);
		usernameText.setHorizontalAlignment(SwingConstants.CENTER);

		accountIndicationsLabel.setBounds(50, 275, 500, 40);

		passwordText.setBounds(50, 315, 200, 50);
		passwordText.setHorizontalAlignment(SwingConstants.CENTER);

		confirmText.setBounds(275, 315, 200, 50);
		confirmText.setHorizontalAlignment(SwingConstants.CENTER);

		passwordIndicationsLabel.setBounds(50, 370, 500, 40);

		createAccountButton.setBounds(275, 415, 200, 50);
	}

	public void addComponents() {
		panel.setLayout(null);
		panel.add(sdProjectLabel);
		panel.add(createAccountLabel);
		panel.add(nameText);
		panel.add(surnameText);
		panel.add(usernameText);
		panel.add(accountIndicationsLabel);
		panel.add(passwordText);
		panel.add(confirmText);
		panel.add(passwordIndicationsLabel);
		panel.add(createAccountButton);
	}

	public void getContent() {
		this.name = nameText.getText();
		this.surname = surnameText.getText();
		this.username = usernameText.getText();  
		this.password = passwordText.getText();
		this.confirm = confirmText.getText();
	}

	public boolean verifyPassword() {
		if((this.password).equals(this.confirm))
			return true;
		else
			return false;
	}

	public void createAccount() {
		User user = new User(this.name, this.surname, this.username, this.password);
		UserBLL userBLL = new UserBLL();
		userBLL.insertUser(user);
	}

	public void addListeners() {
		createAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContent();
				if(verifyPassword() == true) {
					createAccount();
					setVisible(false);
				} else {
					System.out.println("Wrong password!");
				}
			}
		});
	}

}
