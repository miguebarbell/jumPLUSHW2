package repositories;

import models.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoviesRepository {

	private static final UserRepository instance = null;
	private static int currentId = 1;
	private static Connection connection;

	private MoviesRepository() {
	}

	public static void setConnection(Connection connection) {
		MoviesRepository.connection = connection;
	}

	static public boolean save(String title) {
		String createTable = "Create table IF NOT EXISTS movies (ID int primary key AUTO_INCREMENT, title varchar(255) not null " +
		                     "unique, rating FLOAT default null, count INT default 0)";
		try (Statement statement = connection.createStatement()) {
			statement.execute(createTable);
			PreparedStatement preparedMovieStatement =
					connection
							.prepareStatement("insert into movies (title) values (?)");

//			preparedMovieStatement.setInt(1, currentId);
			preparedMovieStatement.setString(1, title);
//			preparedMovieStatement.setInt(2, null);
//			preparedMovieStatement.setInt(3, 0);
			int rows = preparedMovieStatement.executeUpdate();
			System.out.println("rows = " + rows);
			return true;
//			return preparedUserStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	static public List<Movie> getMovieList() {

		String createTable = "Create table IF NOT EXISTS movies (ID int primary key AUTO_INCREMENT, title varchar(255) not null " +
		                     "unique, rating FLOAT default null, count INT default 0)";
		String getAllMoviesStatement = "select * from movies";
		List<Movie> result = new ArrayList<>();
		try (Statement statement = connection.createStatement()) {
			statement.execute(createTable);
			ResultSet resultSet = statement.executeQuery(getAllMoviesStatement);
			while (resultSet.next()) {
				result.add(new Movie(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getFloat(3),
						resultSet.getInt(4)));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	static public boolean leaveRatingFor(Movie movie, Integer rating) {
		// not needed to look and retrieve the movie because we already know the values
		float newRating = (movie.rating() + rating) / (movie.count() + 1);
		String getMovieStatement = "UPDATE movies SET rating = ?, count = ? WHERE ID = ?";
		try (PreparedStatement statement = connection.prepareStatement(getMovieStatement)) {
			statement.setFloat(1, newRating);
			statement.setInt(2, movie.count() + 1);
			statement.setInt(3, movie.movieId());
			int rows = statement.executeUpdate();
			System.out.println(rows + " rows affected");
			return true;
		} catch (SQLException e) {
//			throw new RuntimeException(e);
			return false;
		}
	}


}
