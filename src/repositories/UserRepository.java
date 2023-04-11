package repositories;

import models.User;

import java.sql.*;

public class UserRepository {
	private static final UserRepository instance = null;
	private static int currentId = 1;
	private static Connection connection;

	private UserRepository() {
	}

	public static void setConnection(Connection connection) {
		UserRepository.connection = connection;
	}

	static public UserRepository getInstance() {
		return instance;
	}

	static public boolean save(User user) {
		String createTable = "Create table users (ID int primary key, email varchar(250), password varchar(250))";
		try (Statement statement = connection.createStatement()){
			statement.execute(createTable);
			PreparedStatement preparedUserStatement =
					connection
							.prepareStatement("insert into users (ID, email, password) values (?, ?, ?)");

			preparedUserStatement.setInt(1,currentId);
			preparedUserStatement.setString(2,user.username());
			preparedUserStatement.setString(3, user.password());
			int rows = preparedUserStatement.executeUpdate();
			System.out.println("rows = " + rows);
			return true;
//			return preparedUserStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	static public User getUserByEmail(String email) {
		String getUserByEmailStatement = "SELECT * FROM users WHERE email = '%s'".formatted(email);
		try (PreparedStatement statement = connection.prepareStatement(getUserByEmailStatement)) {

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return new User(resultSet.getString(2), resultSet.getString(3));
			} else {
				throw new RuntimeException("Could not find user");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
