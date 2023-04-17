package repositories;

import models.Movie;
import models.Rating;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoviesRepository {
	private static Connection connection;
	private MoviesRepository() {}
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
			preparedMovieStatement.setString(1, title);
			int rows = preparedMovieStatement.executeUpdate();
			System.out.println("rows = " + rows);
			return true;
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

	static public boolean leaveRatingFor(User user, Movie movie, Integer rating) {
		Rating previousRating = RatingRepository.getRating(user, movie);
		if (null != previousRating) {
			RatingRepository.updateRating(previousRating.ratingId(), rating);
		} else if (null != user) {
			RatingRepository.createRating(user, movie, rating);
		}
		String getMovieStatement = "UPDATE movies SET rating = ?, count = ? WHERE ID = ?";
		try (PreparedStatement statement = connection.prepareStatement(getMovieStatement)) {
			float newRating;
			if (null != previousRating) {
				newRating = (movie.rating() * movie.count()) - previousRating.rating() + rating;
				newRating /= movie.count();
				statement.setInt(2, movie.count());
			} else {
				newRating = (movie.rating() * movie.count() + rating) / (movie.count() + 1);
				statement.setInt(2, movie.count() + 1);
			}
			statement.setFloat(1, newRating);
			statement.setInt(3, movie.movieId());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	static public boolean deleteMovie(Movie movie) {

		return false;
	}


}
