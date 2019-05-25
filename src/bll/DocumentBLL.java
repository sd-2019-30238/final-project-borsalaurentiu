package bll;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.BookDAO;
import dao.DocumentDAO;
import dao.PaymentDAO;
import model.Book;
import model.Document;

public class DocumentBLL {
	public void insertDocument(Document document) {
		DocumentDAO.insertDocument(document);
	}

	public ArrayList<Document> getDocuments() throws SQLException{
		ArrayList<Document> documents = new ArrayList<Document>();
		documents = DocumentDAO.getDocuments();
		return documents;
	}

	public Document getDocument(String title, String author, String privacy) throws SQLException {
		return DocumentDAO.getDocument(title, author, privacy);
	}

	public void updateDocument(Document document) {
		DocumentDAO.updateDocument(document);
	}

	public void deleteDocument(String title, String author, String privacy) throws SQLException {
		DocumentDAO.deleteDocument(title, author, privacy);
	}
}
