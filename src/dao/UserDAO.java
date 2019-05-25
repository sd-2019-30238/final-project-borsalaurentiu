package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.User;
public class UserDAO {

	protected static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO user (name, surname, username, password) VALUES (?,?,?,?)";
	private static final String findPasswordStatementString = "SELECT password FROM user where username = ?";

	public static String findPassword(User user) {
		String toReturn = null;
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findPasswordStatementString);
			findStatement.setString(1, user.getUsername());
			rs = findStatement.executeQuery();
			rs.next();

			String password = rs.getString("password");
			toReturn = password;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"UserDAO:login " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	
	public static void insertUser(User user) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement insertStatement = null;
		
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, user.getName());
			insertStatement.setString(2, user.getSurname());
			insertStatement.setString(3, user.getUsername());
			insertStatement.setString(4, user.getPassword());
			insertStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "UserDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
	}
}