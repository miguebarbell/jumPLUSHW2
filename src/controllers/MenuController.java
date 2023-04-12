package controllers;

import models.User;
import repositories.MoviesRepository;
import repositories.UserRepository;

import java.sql.Connection;
import java.util.Scanner;

import static controllers.LoginController.loginMenu;
import static controllers.MoviesController.moviesMenu;
import static controllers.RegisterController.registerMenu;
import static helper.Prompt.*;

public class MenuController {
	Scanner scanner = new Scanner(System.in);

	public MenuController(Connection connection) {
		UserRepository.setConnection(connection);
		MoviesRepository.setConnection(connection);
		mainMenu();
	}

	void mainMenu() {
		User user = null;
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
				case "1" -> registerMenu(scanner);
				case "2" -> loginMenu(user, scanner);
				case "3" -> moviesMenu(user, scanner);
				case "4" -> promptFeedback("Bye!");
				default -> promptError("Unknown option: " + option);
			}
		} while (!option.equals("4"));
	}
}
