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

		int puzzle = Integer.parseInt(args[0]);
		String fileName = args[1];
		int seconds = Integer.parseInt(args[2]);

//		Random random = new Random();
//		int lowerBound = -10;
//		int upperBound = 10;
//		float[] numbers = new float[40];
//		for (int i = 0; i < 40; i++) {
//			float number = lowerBound + random.nextFloat() * (upperBound - lowerBound);
//			numbers[i] = number;
//			System.out.println(number);
//		}

		try {
			//File file = new File("/Users/mollysunray/OneDrive - Worcester Polytechnic Institute (wpi.edu)/Courses/Junior/CS 4341/Assignments/Assignment2/src/" + fileName);
			File file = new File("/Users/magosheehy/Downloads/" + fileName);
			Scanner scanner = new Scanner(file);
			if (puzzle == 1) {
				int i = 0;
				List<Float> numbers = new ArrayList<>();
				while (scanner.hasNextLine()) {
					float number = scanner.nextFloat();
					numbers.add(number);
					i++;
				}
				Puzzle1 puzzle1 = new Puzzle1();
				puzzle1.ga(numbers, seconds);
			} else if (puzzle == 2) {
				int i = 0;
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