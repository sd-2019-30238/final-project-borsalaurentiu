package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Document;
public class DocumentDAO {

	protected static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO document (title, author, text, privacy) VALUES (?,?,?,?)";
	private static final String getDocumentsStatementString = "SELECT title, author, text, privacy FROM document";
	private static final String getDocumentStatementString = "SELECT title, author, text, privacy FROM document WHERE title = ? AND author = ? AND privacy = ?";
	private static final String updateDocumentStatementString = "UPDATE document SET text = ? WHERE title = ? AND author = ?";	
	private static final String deleteDocumentStatementString = "DELETE FROM document WHERE title = ? AND author = ? AND privacy = ?";

	public static void insertDocument(Document document) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement insertStatement = null;

		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, document.getTitle());
			insertStatement.setString(2, document.getAuthor());
			insertStatement.setString(3, document.getText());
			insertStatement.setString(4, document.getPrivacy());
			insertStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "DocumentDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
	}

	public static ArrayList<Document> getDocuments() throws SQLException{
		ArrayList<Document> documents = new ArrayList<Document>();
		Connection dbConnection = ConnectionFactory.getConnection();

		ResultSet rs = null;
		PreparedStatement findStatement = null;
		try {
			findStatement = dbConnection.prepareStatement(getDocumentsStatementString);
			rs = findStatement.executeQuery();

			while(rs.next()) {
				Document document = new Document(rs.getString("title"), rs.getString("author"), rs.getString("text"), rs.getString("privacy"));
				documents.add(document);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "DocumentDAO:getDocuments " + e.getMessage());
		} finally {
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return documents;
	}

	public static Document getDocument(String title, String author, String privacy) throws SQLException{
		Connection dbConnection = ConnectionFactory.getConnection();

		ResultSet rs = null;
		PreparedStatement findStatement = null;
		Document document = null;
		try {
			findStatement = dbConnection.prepareStatement(getDocumentStatementString);
			findStatement.setString(1, title);
			findStatement.setString(2, author);
			findStatement.setString(3, privacy);
			rs = findStatement.executeQuery();
			rs.next();
			document = new Document(rs.getString("title"), rs.getString("author"), rs.getString("text"), rs.getString("privacy"));

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "DocumentDAO:getDocument " + e.getMessage());
		} finally {
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return document;
	}

	public static void updateDocument(Document document) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement updateStatement = null;
		try {
			updateStatement = dbConnection.prepareStatement(updateDocumentStatementString);
			updateStatement.setString(1, document.getText());
			updateStatement.setString(2, document.getTitle());
			updateStatement.setString(3, document.getAuthor());
			updateStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"DocumentDAO:updateDocument " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}	
	}

	public static void deleteDocument(String title, String author, String privacy) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement deleteStatement = null;
		try {
			deleteStatement = dbConnection.prepareStatement(deleteDocumentStatementString);
			deleteStatement.setString(1, title);
			deleteStatement.setString(2, author);
			deleteStatement.setString(3, privacy);
			deleteStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"DocumentDAO:deleteDocument " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}	
	}
}