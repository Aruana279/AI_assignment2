import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Invalid number of arguments.");
		}

		// Grabs the information from the arguements
		int puzzle = Integer.parseInt(args[0]);
		String fileName = args[1];
		int seconds = Integer.parseInt(args[2]);

		try {
			// MODIFY THIS STRING TO MATCH YOUR PERSONAL FILE STRUCTURE
			File file = new File("/Users/magosheehy/Downloads/" + fileName);
			Scanner scanner = new Scanner(file);
			// Reads the input from the file, passes it to the GA
			if (puzzle == 1) {
				List<Float> numbers = new ArrayList<>();
				while (scanner.hasNextLine()) {
					float number = scanner.nextFloat();
					numbers.add(number);
				}
				Puzzle1 puzzle1 = new Puzzle1();
				puzzle1.ga(numbers, seconds);
			} else if (puzzle == 2) {
				List<Piece> pieces = new ArrayList<>();
				while (scanner.hasNextLine()) {
					String data = scanner.nextLine();
					String[] properties = data.split("\t");
					String type = properties[0];
					int width = Integer.parseInt(properties[1]);
					int strength = Integer.parseInt(properties[2]);
					int cost = Integer.parseInt(properties[3]);
					Piece piece = new Piece(type, width, strength, cost);
					pieces.add(piece);
				}
				Puzzle2 puzzle2 = new Puzzle2();
				puzzle2.ga(pieces, seconds);
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
	}
}