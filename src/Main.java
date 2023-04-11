import controllers.MenuController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {
		String jdbcURL = "jdbc:h2:./movies;AUTO_SERVER=true";
		try (Connection connection = DriverManager.getConnection(jdbcURL, "sa", "")) {
			Class.forName("org.h2.Driver");
			System.out.println("Connected to H2 database.");
			new MenuController(connection);
		} catch (SQLException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
