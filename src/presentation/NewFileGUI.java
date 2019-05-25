package presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import bll.DocumentBLL;
import bll.UserBLL;
import model.Document;
import model.ImagePanel;
import model.User;

public class NewFileGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	ImagePanel panel = new ImagePanel(new ImageIcon("background.jpg").getImage());

	private JTextField fileNameText = new JTextField("new_file_name");
	private JTextArea  fileContentText = new JTextArea();  
	private JScrollPane scrollableTextArea = new JScrollPane(fileContentText);  


	private JRadioButton publicRadioButton = new JRadioButton("public");
	private JRadioButton privateRadioButton = new JRadioButton("private");
	private ButtonGroup group = new ButtonGroup();

	private JButton saveFileButton = new JButton("Save file");

	public NewFileGUI(String username) throws SQLException {
		initializeFrame(username);
		addComponents();
		initializeComponents();
		addListeners(username);
	}

	public void initializeFrame(String username) {
		setTitle("Word Processor - New File - Logged as " + username);
		setSize(new Dimension(880, 600));
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		add(panel);
	}

	public void initializeComponents() {
		fileContentText.setBounds(25, 25, 825, 400);
		saveFileButton.setBounds(525, 475, 125, 50);

		fileNameText.setBounds(225, 475, 200, 50);
		fileNameText.setHorizontalAlignment(SwingConstants.CENTER);
		scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  

		publicRadioButton.setBounds(425, 475, 100, 25);
		privateRadioButton.setBounds(425, 500, 100, 25);

		publicRadioButton.setActionCommand("public");
		privateRadioButton.setActionCommand("private");

		group.add(publicRadioButton);
		group.add(privateRadioButton);
	}

	public void addComponents() {
		panel.setLayout(null);
		panel.add(saveFileButton);
		panel.add(publicRadioButton);
		panel.add(privateRadioButton);
		panel.add(fileNameText);
		panel.add(fileContentText);
		panel.add(scrollableTextArea);
	}

	private String fileName, fileText, filePrivacy;

	public void getContent() {
		this.fileName = fileNameText.getText();
		this.fileText = fileContentText.getText();
		if(privateRadioButton.isSelected())
			this.filePrivacy = "private";
		else if(publicRadioButton.isSelected())
			this.filePrivacy = "public";
		else
			this.filePrivacy = "none";
	}
	
	private void saveFile(String author) {
		Document document = new Document(this.fileName, author, this.fileText, this.filePrivacy);
		DocumentBLL documentBLL = new DocumentBLL();
		documentBLL.insertDocument(document);
	}


	public void addListeners(final String username) {

		saveFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContent();
				saveFile(username);
				setVisible(false);
			}
		});

	}
}
