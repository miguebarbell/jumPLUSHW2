package controllers;

import helper.Hasher;
import models.User;
import repositories.UserRepository;

import java.util.Scanner;

import static controllers.MoviesController.moviesMenu;
import static helper.Prompt.*;

public class LoginController {

	public static void loginMenu(User user, Scanner scanner) {
		promptHeader("Login");
		String email;
		String password;
		User userByEmail = null;
		do {
			prompt("email:");
			email = scanner.nextLine();
			prompt("password:");
			password = scanner.nextLine();
			// check if the email exists and retrive the user
			try {
				userByEmail = UserRepository.getUserByEmail(email);
				if (!Hasher.compare(password, userByEmail.password())) throw new RuntimeException();
			} catch (RuntimeException e) {
				promptError("Bad Credentials");
				promptFeedback("try again");
				// give them 3 times to try again, then return to the mail menu
			}

			// check the password
		} while (null == userByEmail || !Hasher.compare(userByEmail.password(), password));
		user = userByEmail;
		promptFeedback("successfully logged as %s".formatted(userByEmail.username()));
		moviesMenu(user, scanner);
	}
}
