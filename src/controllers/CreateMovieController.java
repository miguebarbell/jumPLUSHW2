package controllers;

import models.User;
import repositories.MoviesRepository;

import java.util.Scanner;

public class CreateMovieController {
	public static void createAMovieMenu(User user, Scanner scanner) {
		System.out.println("Create a new movie as admin");
		String option;
		do {
			System.out.println("0. to go back\nTitle of the movie: ");
			option = scanner.nextLine();
		} while (option.isBlank() && !option.equals("0"));
		if (!option.equals("0")) MoviesRepository.save(option);
		MoviesController.moviesMenu(user, scanner);

	}
}
