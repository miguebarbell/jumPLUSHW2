package controllers;

import models.Movie;
import models.User;
import repositories.MoviesRepository;

import java.util.Arrays;
import java.util.Scanner;

import static helper.Prompt.*;

public class ReviewController {

	public static void reviewMenu(User user, Movie movie, Scanner scanner) {
		boolean undone = true;
		do {
			promptHeader("%s Leave a review for %s".formatted(null == user ? "Guest" : user.username(), movie.title()));
			promptOptions("""
					Rating:
					0. Really Bad
					1. Bad
					2. Not Good
					3. Okay
					4. Good
					5. Great
									
					6. EXIT
									""");
			prompt("Your review?");
			String option = scanner.nextLine();
			if (Arrays.asList("0", "1", "2", "3", "4").contains(option)) {
				undone = !MoviesRepository.leaveRatingFor(movie, Integer.parseInt(option));
				promptFeedback("Thanks for reviewing %s".formatted(movie.title()));
			} else if (option.equals("6")) {
				break;
			} else {
				promptError("Invalid rating option");
			}
		} while (undone);
	}
}
