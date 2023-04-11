package helper;

import java.util.stream.Collectors;

public class Converter {
	public static String replaceTo(String string, char replacement) {
		return string
				.chars()
				.mapToObj(charToString -> String.valueOf(replacement))
				.collect(Collectors.joining(""));
	}
}
