package repositories;

import models.User;
import models.UserDTO;

import java.sql.*;

public class UserRepository {
	private static Connection connection;

	private UserRepository() {
	}

	public static void setConnection(Connection connection) {
		UserRepository.connection = connection;
	}

	static public boolean save(UserDTO user) {
		String createTable =
				"Create table if not exists users (ID int primary key auto_increment, email varchar(250) unique , password " +
				"varchar(250), is_admin boolean default false)";
		try (Statement statement = connection.createStatement()) {
			statement.execute(createTable);
			PreparedStatement preparedUserStatement =
					connection.prepareStatement("insert into users (email, password) values (?, ?)");

			preparedUserStatement.setString(1, user.username());
			preparedUserStatement.setString(2, user.password());
			int rows = preparedUserStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	static public User getUserByEmail(String email) {
		String getUserByEmailStatement = "SELECT * FROM users WHERE email = '%s'".formatted(email);
		try (PreparedStatement statement = connection.prepareStatement(getUserByEmailStatement)) {
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return new User(resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getBoolean(4));
			} else {
				throw new RuntimeException("Could not find user");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
