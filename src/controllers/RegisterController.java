package controllers;

import helper.Hasher;
import helper.Validators;
import models.UserDTO;
import repositories.UserRepository;

import java.util.Scanner;

import static helper.Prompt.*;

public class RegisterController {

	public static void registerMenu(Scanner scanner) {
		promptHeader("\nNew User");
		String email;
		boolean emailInDB = true;
		do {
			prompt("Enter your email:");
			email = scanner.nextLine();
			if (!Validators.validateEmail(email)) promptError("Invalid email");
			try {
				if (null != UserRepository.getUserByEmail(email).username()){
					emailInDB = true;
					promptError("Email already in use");
					promptFeedback("Try again with a different email");
				}
			} catch (RuntimeException e) {
				emailInDB = false;
			}
		} while (!Validators.validateEmail(email) || emailInDB);

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
		boolean isSaved = UserRepository.save(new UserDTO(email, Hasher.hasher(password)));

//		System.out.println("isSaved = " + isSaved);

	}
}
