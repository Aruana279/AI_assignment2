import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {
    private int size;
    private List<Individual> individuals;

    public Population(int size) {
        this.size = size;
        this.individuals = new ArrayList<>();
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<Individual> individuals) {
        this.individuals = individuals;
    }

    public void initializePopulation(List<Float> numbers) {
        for (int i = 0; i < size; i++) {
            // Create individuals for the population
            Individual individual = new Individual();

            // Shuffle the 40 numbers
            Collections.shuffle(numbers);

            // Populate bin 1 with 10 numbers
            for (int j = 0; j < 10; j++) {
                individual.getBin1().add(numbers.get(j));
            }
            // Populate bin 2 with 10 numbers
            for (int j = 10; j < 20; j++) {
                individual.getBin2().add(numbers.get(j));
            }
            // Populate bin 3 with 10 numbers
            for (int j = 20; j < 30; j++) {
                individual.getBin3().add(numbers.get(j));
            }
            // Populate bin 4 with 10 numbers
            for (int j = 30; j < 40; j++) {
                individual.getBin4().add(numbers.get(j));
            }
            // Adds the generated individual to the population
            individuals.add(individual);
        }
    }
}