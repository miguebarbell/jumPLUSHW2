package controllers;

import helper.MenuParser;
import helper.Validators;
import models.Movie;
import models.User;
import repositories.MoviesRepository;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static controllers.CreateMovieController.createAMovieMenu;
import static controllers.ReviewController.reviewMenu;
import static helper.Prompt.*;

public class MoviesController {
	public static void moviesMenu(User user, Scanner scanner) {
		AtomicInteger counter = new AtomicInteger(1);
		promptHeader("Movies");
		promptFeedback("Welcome %s".formatted(null == user ? "Guest" : user.isAdmin() ?
				user.username() + " you are an Administrator" :	user.username()));
		List<Movie> movieList = MoviesRepository.getMovieList();
		if (movieList.isEmpty() && (null == user || !user.isAdmin())) {
			promptFeedback("No movies in the database");
		} else {
			promptHeader("""
					+===============================================+
					| Movie                         Rating Opinions |""");
			movieList.forEach(movie -> promptHeader(
					MenuParser.fourParameter(
							counter.getAndIncrement(),
							movie.title(),
							movie.count() > 0 ? String.format("%.1f", movie.rating()) : "N/A",
							movie.count())));
			promptHeader("+===============================================+\n  %s. Exit".formatted(counter.get()));
			if (null != user && user.isAdmin()) {
				promptOptions("  0. Add a movie");
			}
			String option;
			do {
				option = scanner.nextLine();
				if (Validators.validateNumber(option)) {
					int optionAsInt = Integer.parseInt(option);
					if (optionAsInt > 0 && optionAsInt < counter.get()) {
						reviewMenu(user, movieList.get(optionAsInt - 1), scanner);
					} else if (optionAsInt == 0 && user.isAdmin()) {
						createAMovieMenu(user, scanner);
					} else if (optionAsInt == counter.get()) {
						return;
					} else {
						promptError("Invalid option: %s".formatted(option));
					}
				}
			} while (!Validators.validateNumber(option));
		}
	}
}
