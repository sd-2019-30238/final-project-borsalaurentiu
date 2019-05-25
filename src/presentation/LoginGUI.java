package presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import bll.DocumentBLL;
import bll.UserBLL;
import model.Document;
import model.ImagePanel;
import model.User;

public class LoginGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	ImagePanel panel = new ImagePanel(new ImageIcon("background.jpg").getImage());

	private JTable table = new JTable();
	private JScrollPane tablePane = new JScrollPane(table);

	private JButton newFileButton = new JButton("New file");	
	private JButton openFileButton = new JButton("Open file");	
	private JButton deleteFileButton = new JButton("Delete file");	

	public LoginGUI(User user) throws SQLException {
		initializeFrame(user);
		addComponents(user);
		initializeComponents();
		addListeners(user);
		showTable();
	}

	public void initializeFrame(User user) {
		setTitle("Word Processor - Logged as " + user.getUsername());
		setSize(new Dimension(480, 400));
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		add(panel);
	}

	public void initializeComponents() {
		tablePane.setBounds(25, 25, 425, 200);
		table.setBounds(25, 25, 425, 200);
		newFileButton.setBounds(25, 275, 125, 50);
		openFileButton.setBounds(175, 275, 125, 50);
		deleteFileButton.setBounds(325, 275, 125, 50);
	}

	public void addComponents(User user) {
		panel.setLayout(null);
		panel.add(table);
		panel.add(tablePane);
		panel.add(deleteFileButton);
		panel.add(openFileButton);
		panel.add(newFileButton);
	}

	static int row;
	static Point point;
	
	private Document openFile(String title, String username, String privacy) throws SQLException {
		DocumentBLL documentBLL = new DocumentBLL();
		return documentBLL.getDocument(title, username, privacy);
	}

	Document document;

	private void deleteDocument(Document document) throws SQLException {
		String title = document.getTitle();
		String author = document.getAuthor();
		String privacy = document.getPrivacy();
		System.out.print(title+author+privacy);
		DocumentBLL documentBLL = new DocumentBLL();
		documentBLL.deleteDocument(title, author, privacy);
	}
	
	public void addListeners(User user) {
//		final Document document;
		
		final String username = user.getUsername();
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable table =(JTable) mouseEvent.getSource();
				point = mouseEvent.getPoint();
				row = table.rowAtPoint(point);
				if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
					if((table.getModel().getValueAt(row, 1)).equals(username)) {
						String title = (String) table.getModel().getValueAt(row, 0);
						String privacy = (String) table.getModel().getValueAt(row, 2);
				
						try {
							document = openFile(title, username, privacy);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});

		if(user.getUsername().equals("guest")) {
			newFileButton.setBackground(Color.RED);
		} else {
			newFileButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					NewFileGUI newFileGUI;
					try {
						newFileGUI = new NewFileGUI(username);
						newFileGUI.setVisible(true);
						showTable();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}		
				}
			});
		}

		openFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenFileGUI openFileGUI;
				try {
					openFileGUI = new OpenFileGUI(document, username);
					openFileGUI.setVisible(true);
					showTable();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		if(user.getUsername().equals("guest")) {
			deleteFileButton.setBackground(Color.RED);
		} else {
		deleteFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					deleteDocument(document);
					showTable();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		}

	}

	public void showTable() throws SQLException  {
		DocumentBLL documentBll = new DocumentBLL();
		ArrayList<Document> documents = new ArrayList<Document>();
		documents = documentBll.getDocuments();
		String[] column = {"<html><b>Title</html>", "<html><b>Author", "<html><b>Privacy"};		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("title");
		model.addColumn("author");
		model.addColumn("privacy");
		model.addRow(column);	
		for(Document document: documents){
			Object[] row = new Object[3];
			row[0] = document.getTitle();
			row[1] = document.getAuthor();
			row[2] = document.getPrivacy();
			model.addRow(row);
		}
		table.setModel(model);
	}
}