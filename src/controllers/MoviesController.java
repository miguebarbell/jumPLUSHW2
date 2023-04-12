package controllers;

import models.Movie;
import models.User;
import repositories.MoviesRepository;

import java.util.List;
import java.util.Scanner;

import static helper.Prompt.promptHeader;

public class MoviesController {

	public static void moviesMenu(User user, Scanner scanner) {
		promptHeader("Movies");
		List<Movie> movieList = MoviesRepository.getMovieList();
		System.out.println("Movie\t\tRating\tOpinions");
		movieList.forEach(movie -> System.out.printf("%s. %s\t%s\t%s%n", movie.movieId(), movie.title(), movie.rating(),
				movie.count()));
		System.out.printf("%s. Exit%n", movieList.size() + 1);
		if (null != user && user.username().equals("admin")) {
			System.out.println("0. Add a movie");
		}
		String option = scanner.nextLine();
		//TODO: make the menu option here


	}
}
