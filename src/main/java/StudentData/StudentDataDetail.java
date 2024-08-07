package StudentData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentDataDetail {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "system";
	private static final String PASSWORD = "atul";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Select an operation:");
			System.out.println("1. Add Student");
			System.out.println("2. View Students");
			System.out.println("3. Update Student");
			System.out.println("4. Delete Student");
			System.out.println("0. Exit");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				addStudent(scanner);
				break;
			case 2:
				viewStudents();
				break;
			case 3:
				updateStudent(scanner);
				break;
			case 4:
				deleteStudent(scanner);
				break;
			case 0:
				System.out.println("Exiting...");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private static void addStudent(Scanner scanner) {
		System.out.println("Enter student id:");
		int id = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Enter student name:");
		String name = scanner.nextLine();
		System.out.println("Enter student age:");
		int age = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Enter student grade:");
		String grade = scanner.nextLine();

		String sql = "INSERT INTO students (id, name, age, grade) VALUES (?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, id);
			statement.setString(2, name);
			statement.setInt(3, age);
			statement.setString(4, grade);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("A new student was inserted successfully!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void viewStudents() {
		String sql = "SELECT * FROM students";

		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String grade = resultSet.getString("grade");

				System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age + ", Grade: " + grade);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void updateStudent(Scanner scanner) {
		System.out.println("Enter student ID to update:");
		int id = scanner.nextInt();
		scanner.nextLine();

		System.out.println("Enter new name:");
		String name = scanner.nextLine();
		System.out.println("Enter new age:");
		int age = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Enter new grade:");
		String grade = scanner.nextLine();

		String sql = "UPDATE students SET name = ?, age = ?, grade = ? WHERE id = ?";

		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, name);
			statement.setInt(2, age);
			statement.setString(3, grade);
			statement.setInt(4, id);

			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("An existing student was updated successfully!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void deleteStudent(Scanner scanner) {
		System.out.println("Enter student ID to delete:");
		int id = scanner.nextInt();
		scanner.nextLine();

		String sql = "DELETE FROM students WHERE id = ?";

		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, id);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("A student was deleted successfully!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
