package helper;

import org.junit.jupiter.api.Test;

class MenuParserTest {

	@Test
	void fourParameter() {
		String rambo = MenuParser.fourParameter(1, "Rambo", "3.5", 2);
		System.out.println(rambo);
	}
}
