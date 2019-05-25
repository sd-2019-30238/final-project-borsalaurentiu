package presentation;

 import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import bll.DocumentBLL;
import model.Document;
import model.ImagePanel;

public class OpenFileGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	ImagePanel panel = new ImagePanel(new ImageIcon("background.jpg").getImage());

	private JTextArea  fileContentText = new JTextArea();  
	private JScrollPane scrollableTextArea = new JScrollPane(fileContentText);  

	private JButton saveFileButton = new JButton("Save file");
	String result = null;
	
	public OpenFileGUI(Document document, String username) throws SQLException {
		initializeFrame(document, username);
		addComponents();
		initializeComponents();
		addListeners(document);
		write(document, username);
	}

	public void write(Document document, String username) {
		if(!(document.getAuthor().equals(username)) && document.getPrivacy().equals("private")) {
			result = "Private account!";
		} else {
			result = document.getText();
		}
		fileContentText.append(String.valueOf(result) + "\n\r");
	}
	
	public void initializeFrame(Document document, String username) {
		setTitle(document.getTitle() + ", " + document.getAuthor());
		setSize(new Dimension(880, 600));
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		add(panel);
	}

	public void initializeComponents() {
		fileContentText.setBounds(25, 25, 825, 400);
		saveFileButton.setBounds(25, 475, 825, 50);
		scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
	}

	public void addComponents() {
		panel.setLayout(null);
		panel.add(saveFileButton);
		panel.add(fileContentText);
		panel.add(scrollableTextArea);
	}

	private void saveFile(Document document) {
		DocumentBLL documentBLL = new DocumentBLL();
		documentBLL.updateDocument(document);
	}
	
	private void modifyText(Document document) {
		result = fileContentText.getText();
		System.out.println(result);
		document.setText(result);	
		
	}

	public void addListeners(final Document document) {
		saveFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyText(document);
				saveFile(document);
			}
		});
	}
}
