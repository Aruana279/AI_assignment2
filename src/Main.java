import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	Individual fittest;

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
			File file = new File("/Users/magosheehy/Downloads/" + fileName);
			Scanner scanner = new Scanner(file);
			int i = 0;
			List<Float> numbers = new ArrayList<>();
//			float[] numbers = new float[40];
			while (scanner.hasNextLine()) {
				float number = scanner.nextFloat();
				numbers.add(number);
//				numbers[i] = number;
				i++;
			}
			GA ga = new GA();
			ga.ga(numbers, puzzle, seconds);
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}

		GA geneticAlg= new GA();

		//System.out.println(geneticAlg.ga(nmbs, puzzlePassed));
	}

}