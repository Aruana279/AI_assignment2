import java.text.DecimalFormat;
import java.util.*;

public class GA {

    // elitism
    // culling
    // the solution is the four bins for the highest scoring individual
    // print out the score for the highest scoring individual
    // the highest scoring individual should be found at the last generation as long
    // as elitism is not 0

    public int ga(List<Float> numbers, int puzzle, int seconds) {
        long startTime = System.currentTimeMillis() / 1000;
        int size = 10;
        int NUMSAVED = 2;
        int NUMREMOVED = 3;
        Population population = new Population(size);
        population.initializePopulation(numbers);
        int generationCount = 0;
        while (System.currentTimeMillis() / 1000 < startTime + seconds) {
            System.out.println("\nGENERATION " + generationCount);
            List<Individual> newIndividuals = new ArrayList<>();
            // Get fittest individuals from elitism and use those for the next generation
            List<Individual> topPerformers = elitism(population, NUMSAVED);
            culling(population, NUMREMOVED);
            // Do crossover on remaining individuals and fittest individuals
            System.out.println("Parents after culling: " + population.individuals.size());
            newIndividuals.addAll(crossover(size - NUMSAVED, population.individuals, numbers));
            population.individuals = newIndividuals;
            System.out.println("Children after crossover: " + population.individuals.size());
            population.individuals.addAll(topPerformers);
            System.out.println("Children after elitism: " + population.individuals.size());
            System.out.println("Best Score: " + elitism(population, 1).get(0).calculateFitness());
            generationCount++;
        }
        return generationCount;

        // public void findTheHighestFitness(int popSize){
        // float best=indiv[0].calculateFitness();
        // for (int i = 1; i < popSize-1; i++){
        // float temp=indiv[i].calculateFitness();
        // if (best<temp){
        // best=temp;
        // }
        // }
        // }
    }

    public void culling(Population population, int numRemoved) {
        List<Float> fitnessScores = new ArrayList<>();
        for (Individual individual : population.individuals) {
            fitnessScores.add(individual.calculateFitness());
        }
        for (float num : fitnessScores) {
            System.out.print(num + " ");
        }
        System.out.println();
        for (int i = 0; i < numRemoved; i++) {
            float min = Collections.min(fitnessScores);
            fitnessScores.remove(min);
            System.out.println("Removed: " + min);
        }
        System.out.println();
        for (float num : fitnessScores) {
            System.out.print(num + " ");
        }
        // Get the individuals whose scores weren't removed
        List<Individual> individuals = new ArrayList<>();
        for (Individual individual : population.individuals) {
            if (fitnessScores.contains(individual.fitness)) {
                individuals.add(individual);
            }
        }
        // Reset the individuals for the population
        population.individuals = individuals;
        System.out.println();
        System.out.println(population.individuals.size());
    }

    public List<Individual> crossover(int size, List<Individual> individuals, List<Float> numbers) {
        DecimalFormat df = new DecimalFormat("#.#");
        //// ThreadLocalRandom.current().nextInt(min, max + 1);
        List<Individual> result = new ArrayList<>();
        int numChildren = 0;
        while (numChildren < size) {
            Random random = new Random();
            int parent1Index = random.nextInt(individuals.size());
            int parent2Index = random.nextInt(individuals.size());
            while (parent1Index == parent2Index) {
                parent1Index = random.nextInt(individuals.size());
            }
            Individual parent1 = individuals.get(parent1Index);
            Individual parent2 = individuals.get(parent2Index);

            List<Integer> parent1Bins = new ArrayList<>();
            List<Integer> parent2Bins = new ArrayList<>();

            int parent1RandomBin = random.nextInt(4) + 1;
            parent1Bins.add(parent1RandomBin);
            while (parent1Bins.size() < 4) {
                while (parent1Bins.contains(parent1RandomBin)) {
                    parent1RandomBin = random.nextInt(4) + 1;
                }
                parent1Bins.add(parent1RandomBin);
            }

            int parent2RandomBin = random.nextInt(4) + 1;
            parent2Bins.add(parent2RandomBin);
            while (parent2Bins.size() < 4) {
                while (parent2Bins.contains(parent2RandomBin)) {
                    parent2RandomBin = random.nextInt(4) + 1;
                }
                parent2Bins.add(parent2RandomBin);
            }

            System.out.println("Before crossover");
            System.out.println("Parent 1");
            System.out.print("Bin 1: ");
            for (float num : parent1.getBin(1)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 2: ");
            for (float num : parent1.getBin(2)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 3: ");
            for (float num : parent1.getBin(3)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 4: ");
            for (float num : parent1.getBin(4)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.println();
            System.out.println("Parent 2");
            System.out.print("Bin 1: ");
            for (float num : parent2.getBin(1)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 2: ");
            for (float num : parent2.getBin(2)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 3: ");
            for (float num : parent2.getBin(3)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 4: ");
            for (float num : parent2.getBin(4)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.println();

            for (int i = 0; i < 4; i++) {
                int cutPoint = random.nextInt(8) + 1;
                System.out.println("cut point: " + cutPoint);
                for (int j = 0; j < cutPoint; j++) {
                    int parent1Bin = parent1Bins.get(i);
                    int parent2Bin = parent2Bins.get(i);
                    float value1 = parent1.getBin(parent1Bin).get(j);
                    float value2 = parent2.getBin(parent2Bin).get(j);
                    parent1.getBin(parent1Bin).set(j, value2);
                    parent2.getBin(parent2Bin).set(j, value1);
                }
            }
            parent1 = removeDuplicates(parent1, numbers);
            parent2 = removeDuplicates(parent2, numbers);

            result.add(parent1);
            result.add(parent2);
            numChildren += 2;

            System.out.println("After crossover");
            System.out.println("Parent 1");
            System.out.print("Bin 1: ");
            for (float num : parent1.getBin(1)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 2: ");
            for (float num : parent1.getBin(2)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 3: ");
            for (float num : parent1.getBin(3)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 4: ");
            for (float num : parent1.getBin(4)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.println();
            System.out.println("Parent 2");
            System.out.print("Bin 1: ");
            for (float num : parent2.getBin(1)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 2: ");
            for (float num : parent2.getBin(2)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 3: ");
            for (float num : parent2.getBin(3)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 4: ");
            for (float num : parent2.getBin(4)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.println();
        }
        if (numChildren > size) {
            result.remove(result.size() - 1);
        }
        return result;
    }

    private List<Individual> elitism(Population population, int numSaved) {
        List<Float> fitnessScores = new ArrayList<>();

        for (Individual individual : population.individuals) {
            fitnessScores.add(individual.calculateFitness());
        }
        List<Individual> topIndividuals = new ArrayList<>();
        for (int i = 0; i < numSaved; i++) {
            float max = Collections.max(fitnessScores);
            for (Individual individual : population.individuals) {
                if (individual.fitness == max) {
                    topIndividuals.add(individual);
                    break;
                }
            }
            fitnessScores.remove(new Float(max));
        }
        return topIndividuals;
    }

    public Individual removeDuplicates(Individual child, List<Float> numbers) {
        // HashMap<Float, Integer> counts = new HashMap<>();
        List<Float> binValues = new ArrayList<>();
        List<Float> duplicates = new ArrayList<>();
        List<Float> missing = new ArrayList<>();
        binValues.addAll(child.bin1);
        binValues.addAll(child.bin2);
        binValues.addAll(child.bin3);
        binValues.addAll(child.bin4);

        for (int i = 0; i < 40; i++) {
            int count = 0;
            // counts.put(binValues.get(i), counts.getOrDefault(binValues.get(i), 0) + 1);
            for (int j = 0; j < 40; j++) {
                if (numbers.get(i).equals(binValues.get(j))) {
                    count++;
                }
            }
            if (count == 0) {
                missing.add(numbers.get(i));
            } else if (count > 1) {
                duplicates.add(numbers.get(i));
            }
        }

        System.out.println("Duplicates: " + duplicates.size());
        System.out.println("Missing: " + missing.size());

        int count = 0;
        boolean dupFlag = false;
        for (int i = 0; i < duplicates.size(); i++) {
            for (int j = 0; j < binValues.size(); j++) {
                if (binValues.get(j).equals(duplicates.get(i))) {
                    if (!dupFlag) {
                        dupFlag = true;
                    } else {
                        binValues.set(j, missing.get(count));
                        count++;
                    }
                }
            }
            dupFlag = false;
        }

        child.bin1 = binValues.subList(0, 10);
        child.bin2 = binValues.subList(10, 20);
        child.bin3 = binValues.subList(20, 30);
        child.bin4 = binValues.subList(30, 40);
        return child;
    }
}
