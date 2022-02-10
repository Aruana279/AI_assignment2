import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {

    int size;
    List<Individual> individuals;

    //how many "parents"?
    //

    public Population(int size) {
        this.size = size;
        this.individuals = new ArrayList<>();
    }

    public void initializePopulation(List<Float> numbers) {
        for (int i = 0; i < size; i++) {
            // Create individuals for the population
            Individual individual = new Individual();

            // Shuffle the 40 numbers
            Collections.shuffle(numbers);

            System.out.println("Bin1:");
            int index = 0;
            // Populate bin 1 with 10 numbers
            for (int j = 0; j < 10; j++) {
                individual.bin1.add(numbers.get(j));
                System.out.print(individual.bin1.get(index) + " ");
                index++;
            }
            System.out.println();

            System.out.println("Bin2:");
            index = 0;
            // Populate bin 2 with 10 numbers
            for (int j = 10; j < 20; j++) {
                individual.bin2.add(numbers.get(j));
                System.out.print(individual.bin2.get(index) + " ");
                index++;
            }
            System.out.println();

            System.out.println("Bin3:");
            index = 0;
            // Populate bin 3 with 10 numbers
            for (int j = 20; j < 30; j++) {
                individual.bin3.add(numbers.get(j));
                System.out.print(individual.bin3.get(index) + " ");
                index++;
            }
            System.out.println();

            System.out.println("Bin4:");
            index = 0;
            // Populate bin 4 with 10 numbers
            for (int j = 30; j < 40; j++) {
                individual.bin4.add(numbers.get(j));
                System.out.print(individual.bin4.get(index) + " ");
                index++;
            }
            System.out.println();
            individuals.add(individual);

            System.out.println();
            System.out.println(individual.calculateFitness());
            System.out.println("-----------------------------------------------------------------------");
        }
    }

    //while (time!=0) iterate through ga
}