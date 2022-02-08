import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Population {

    int size;
    int fittest;
    Individual[] individuals;

    //how many "parents"?
    //

    public Population(int size) {
        this.size = size;
        this.individuals = new Individual[size];
    }

    public void initializePopulation(List<Float> numbers) {
        for (int i = 0; i < size; i++) {
            // Create individuals for the population
            individuals[i] = new Individual();

            // Shuffle the 40 numbers
            Collections.shuffle(numbers);

            System.out.println("Bin1:");
            int index = 0;
            // Populate bin 1 with 10 numbers
            for (int j = 0; j < 10; j++) {
                individuals[i].bin1.add(numbers.get(j));
                System.out.print(individuals[i].bin1.get(index) + " ");
                index++;
            }
            System.out.println();

            System.out.println("Bin2:");
            index = 0;
            // Populate bin 2 with 10 numbers
            for (int j = 10; j < 20; j++) {
                individuals[i].bin2.add(numbers.get(j));
                System.out.print(individuals[i].bin2.get(index) + " ");
                index++;
            }
            System.out.println();

            System.out.println("Bin3:");
            index = 0;
            // Populate bin 3 with 10 numbers
            for (int j = 20; j < 30; j++) {
                individuals[i].bin3.add(numbers.get(j));
                System.out.print(individuals[i].bin3.get(index) + " ");
                index++;
            }
            System.out.println();

            System.out.println("Bin4:");
            index = 0;
            // Populate bin 4 with 10 numbers
            for (int j = 30; j < 40; j++) {
                individuals[i].bin4.add(numbers.get(j));
                System.out.print(individuals[i].bin4.get(index) + " ");
                index++;
            }
            System.out.println();

            System.out.println();
            System.out.println(individuals[i].calculateFitness());
            System.out.println("-----------------------------------------------------------------------");
        }
    }

    //while (time!=0) iterate through ga
}
