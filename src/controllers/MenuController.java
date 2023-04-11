package controllers;

import helper.Hasher;
import helper.Validators;
import models.User;
import repositories.UserRepository;

import java.sql.Connection;
import java.util.Scanner;

import static helper.Prompt.*;

public class MenuController {
	Scanner scanner = new Scanner(System.in);

	public MenuController(Connection connection) {
		UserRepository.setConnection(connection);
		mainMenu();
	}

	void mainMenu() {
		String option;
		do {
			promptHeader("""
									
					+---------------------+
					| 1. REGISTER         |
					| 2. LOGIN            |
					| 3. VIEW MOVIES      |
					| 4. EXIT             |
					+---------------------+
					""");
			prompt("Select your choice");
			option = scanner.nextLine();
			switch (option) {
				case "1" -> registerMenu();
				case "2" -> loginMenu();
				case "3" -> moviesMenu();
				case "4" -> promptFeedback("Bye!");
				default -> promptError("Unknown option: " + option);
			}
		} while (!option.equals("4"));
	}

	void registerMenu() {
		promptHeader("\nNew User");
		String email;
		do {
			prompt("Enter your email:");
			email = scanner.nextLine();
			if (!Validators.validateEmail(email)) promptError("Invalid email");
		} while (!Validators.validateEmail(email));

		String password;
		do {
			prompt("Enter your password:");
			password = scanner.nextLine();
			if (!Validators.validatePassword(password)) promptError("Invalid password");
		} while (!Validators.validatePassword(password));

		String rePassword;
		int times = 0;
		do {
			prompt("Enter your password, again:");
			rePassword = scanner.nextLine();
			if (!rePassword.equals(password)) {
				promptError("not the same password");
				if (++times >= 3) {
					promptError("Too many times trying to match password, are you a robot?");
					System.exit(1);
				}
			}
		} while (!rePassword.equals(password));
		boolean isSaved = UserRepository.save(new User(email, Hasher.hasher(password)));

		System.out.println("isSaved = " + isSaved);

	}

	void loginMenu() {
		promptHeader("Login");
		String email;
		String password;
		User userByEmail = null;
		do {
			prompt("email:");
			email = scanner.nextLine();
			prompt("password:");
			password = scanner.nextLine();
			// check if the email exist and retrive the user
			try {
				userByEmail = UserRepository.getUserByEmail(email);
				if (!Hasher.compare(password, userByEmail.password())) throw new RuntimeException();
			} catch (RuntimeException e) {
				promptError("Bad Credentials");
				promptFeedback("try again");
			}

			// check the password
		} while (null == userByEmail || !Hasher.compare(userByEmail.password(), password));
		promptFeedback("successfully logged as %s".formatted(userByEmail.username()));
		moviesMenu();

	}

	void moviesMenu() {
		promptHeader("Movies");
	}


}
