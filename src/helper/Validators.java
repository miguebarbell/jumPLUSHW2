package helper;

import java.util.stream.IntStream;


public class Validators {

	static boolean isDigit(Character value) {
		try {
			Integer.parseInt(String.valueOf(value));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static Long validateContactNumber(String contactNumber) {
		StringBuilder validatedNumber = new StringBuilder();
		for (int i = 0; i < contactNumber.length(); i++) {
			try {
				Integer.parseInt(String.valueOf(contactNumber.charAt(i)));
				validatedNumber.append(contactNumber.charAt(i));
			} catch (NumberFormatException e) {
			}
		}
		long returnValue = Long.parseLong(validatedNumber.toString());
		if ((returnValue < 1_000_000_0000L) || (returnValue > 9_999_999_9999L)) {
			return 0L;
		} else {
			return Long.parseLong(validatedNumber.toString());
		}
	}

	public static Double validateAmount(String amountToValidate) {
		final boolean[] isDecimal = {false};
		StringBuilder builder = new StringBuilder();
		IntStream
				.range(0, amountToValidate.length())
				.forEach(index -> {
					if (isDigit(amountToValidate.charAt(index))) {
						builder.append(amountToValidate.charAt(index));
					} else if (amountToValidate.charAt(index) == '-' && index == 0) {
						builder.append(amountToValidate.charAt(index));
					} else if (amountToValidate.charAt(index) == '.' && !isDecimal[0]) {
						isDecimal[0] = true;
						builder.append(amountToValidate.charAt(index));
					}
				});

		return Double.parseDouble(builder.toString());
	}

	public static boolean validatePassword(String password) {
		String pattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=/\\.!\\(\\)])(?=\\S+$).{8,}";
		return password.matches(pattern);
	}

	public static boolean validateEmail(String email) {
		String pattern = "[\\w\\.-]+@[\\w\\.]+\\.[\\w]{2,5}";
		return email.matches(pattern);
	}
}
