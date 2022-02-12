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
        int NUMSAVED = (int) Math.floor(0.2 * (double) size);
        int NUMREMOVED = (int) Math.floor(0.3 * (double) size);
        Population population = new Population(size);
        population.initializePopulation(numbers);
        int generationCount = 0;
        while (System.currentTimeMillis() / 1000 < startTime + seconds) {
            // while (generationCount < 10) {
            System.out.println("\nGENERATION " + generationCount);
            // Get fittest individuals from elitism and use those for the next generation
            List<Individual> topPerformers = elitism(population, NUMSAVED);
            culling(population, NUMREMOVED);
            // Do crossover on remaining individuals and fittest individuals
            System.out.println("Parents after culling: " + population.individuals.size());
            population.individuals = crossover(size - NUMSAVED, population.individuals, numbers);
            System.out.println("Children after crossover: " + population.individuals.size());
            population.individuals.addAll(topPerformers);
            System.out.println("Children after elitism: " + population.individuals.size());
            System.out.println("Best Score: " + elitism(population, 1).get(0).calculateFitness());
            generationCount++;
        }
        return generationCount;
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

    public List<Individual> crossover(int size, List<Individual> individuals, List<Float> numbers) {
        DecimalFormat df = new DecimalFormat("#.#");
        List<Individual> result = new ArrayList<>();
        int numChildren = 0;
        List<Float> fitnessScores = new ArrayList<>();
        float fitnessSum = 0;
        for (int i = 0; i < individuals.size(); i++) {
            float tempFit = individuals.get(i).calculateFitness();
            fitnessSum += tempFit;
            fitnessScores.add(fitnessSum);
        }
        while (numChildren < size) {
            Random random = new Random();
            int parent1Index = 0;
            float parent1Prob = random.nextFloat() * fitnessScores.get(fitnessScores.size() - 1);
            for (int i = 0; i < individuals.size(); i++) {
                if (fitnessScores.get(i) >= parent1Prob) {
                    parent1Index = i;
                    break;
                }
            }
            int parent2Index = parent1Index;
            while (parent1Index == parent2Index) {
                float parent2Prob = random.nextFloat() * fitnessScores.get(fitnessScores.size() - 1);
                for (int i = 0; i < individuals.size(); i++) {
                    if (fitnessScores.get(i) >= parent2Prob) {
                        parent2Index = i;
                        break;
                    }
                }
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

            Individual child1 = new Individual();
            Individual child2 = new Individual();
            for (int i = 0; i < 10; i++) {
                child1.bin1.add(parent1.getBin(1).get(i));
                child1.bin2.add(parent1.getBin(2).get(i));
                child1.bin3.add(parent1.getBin(3).get(i));
                child1.bin4.add(parent1.getBin(4).get(i));
                child2.bin1.add(parent1.getBin(1).get(i));
                child2.bin2.add(parent1.getBin(2).get(i));
                child2.bin3.add(parent1.getBin(3).get(i));
                child2.bin4.add(parent1.getBin(4).get(i));
            }
            for (int i = 0; i < 4; i++) {
                int cutPoint = random.nextInt(8) + 1;
                System.out.println("cut point: " + cutPoint);
                for (int j = 0; j < cutPoint; j++) {
                    int parent1Bin = parent1Bins.get(i);
                    int parent2Bin = parent2Bins.get(i);
                    float value1 = parent1.getBin(parent1Bin).get(j);
                    float value2 = parent2.getBin(parent2Bin).get(j);
                    child1.getBin(parent1Bin).set(j, value2);
                    child2.getBin(parent2Bin).set(j, value1);
                }
            }
            child1 = removeDuplicates(child1, numbers);
            child2 = removeDuplicates(child2, numbers);

            result.add(child1);
            result.add(child2);
            numChildren += 2;

            System.out.println("After crossover");
            System.out.println("Child 1");
            System.out.print("Bin 1: ");
            for (float num : child1.getBin(1)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 2: ");
            for (float num : child1.getBin(2)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 3: ");
            for (float num : child1.getBin(3)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 4: ");
            for (float num : child1.getBin(4)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.println();
            System.out.println("Child 2");
            System.out.print("Bin 1: ");
            for (float num : child2.getBin(1)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 2: ");
            for (float num : child2.getBin(2)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 3: ");
            for (float num : child2.getBin(3)) {
                System.out.print(df.format(num) + " ");
            }
            System.out.println();
            System.out.print("Bin 4: ");
            for (float num : child2.getBin(4)) {
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
