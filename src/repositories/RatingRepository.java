package repositories;

import models.Movie;
import models.Rating;
import models.User;

import java.sql.*;

public class RatingRepository {
	private static Connection connection;

	private RatingRepository() {
	}

	public static void setConnection(Connection connection) {
		RatingRepository.connection = connection;
		String createTableStatement =
				"CREATE TABLE IF NOT EXISTS rating (ID INTEGER PRIMARY KEY AUTO_INCREMENT, user_id INTEGER NOT NULL, movie_id " +
				"INTEGER NOT NULL, rating INTEGER NOT NULL, foreign key (user_id) references users, foreign key (movie_id) references movies)";
		try {
			connection.createStatement().execute(createTableStatement);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static Rating getRating(User user, Movie movie) {
		String getRatingStatement = "SELECT * from rating where user_id = ? and movie_id = ?";
		if (null == user) return null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(getRatingStatement)) {
			preparedStatement.setInt(1, user.userId());
			preparedStatement.setInt(2, movie.movieId());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return new Rating(
						resultSet.getInt(1),
						resultSet.getInt(2),
						resultSet.getInt(3),
						resultSet.getInt(4)
				);
			} else return null;
		} catch (SQLException e) {
			return null;
		}
	}

	public static boolean updateRating(Integer ratingId, Integer rating) {
		String updateRatingStatement = "UPDATE rating SET rating = ? WHERE ID = ?";
		try (PreparedStatement statement = connection.prepareStatement(updateRatingStatement)) {
			statement.setInt(1, rating);
			statement.setInt(2, ratingId);
			return statement.execute();
		} catch (SQLException e) {
			return false;
		}
	}

	public static void createRating(User user, Movie movie, Integer rating) {
		String createRatingStatement = "insert into rating (user_id, movie_id, rating) values (%s, %s, %s)"
				.formatted(user.userId(), movie.movieId(), rating);
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate(createRatingStatement);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public static void deleteRatingForUserInMovie(User user, Movie movie){}
	public static void deleteRatingForMovie(Movie movie) {

	}
}
