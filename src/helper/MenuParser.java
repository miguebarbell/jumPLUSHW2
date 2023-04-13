package helper;

public class MenuParser {
	public static String fourParameter(int index, String title, String rating, Integer count) {
		int indexAndTitleLength = 32;
		StringBuilder result = new StringBuilder();
		result.append("|")
		      .append(" ".repeat(index < 10 ? 1 : 0)) //no more than 99 movies
		      .append(index).append(". ")
		      .append(title);

		int spacesToAppend = indexAndTitleLength - result.length();
		if (spacesToAppend > 0) result.append(" ".repeat(spacesToAppend));
		else result.setLength(indexAndTitleLength);
		result.append(" ")
		      .append(rating)
		      .append(" ".repeat(count > 9 ? 5 : 6))
		      .append(count)
		      .append(" ".repeat(5))
		      .append('|');
		return String.valueOf(result);
	}
}
