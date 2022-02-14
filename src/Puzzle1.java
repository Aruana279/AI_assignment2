import java.util.*;

public class Puzzle1 {

    // elitism
    // culling
    // the solution is the four bins for the highest scoring individual
    // print out the score for the highest scoring individual
    // the highest scoring individual should be found at the last generation as long
    // as elitism is not 0

    public int ga(List<Float> numbers, int seconds) {
        Random random = new Random();
        // Grabs the time when execution starts
        long startTime = System.currentTimeMillis() / 1000;
        // Population
        int size = 100;
        // Top 20% are saved through elitism, bottom 30% are culled
        int NUMSAVED = (int) Math.floor(0.2 * (double) size);
        int NUMREMOVED = (int) Math.floor(0.3 * (double) size);
        Population population = new Population(size);
        population.initializePopulation(numbers);
        int generationCount = 0;
        while (System.currentTimeMillis() / 1000 < startTime + seconds) {
            System.out.println("\nGENERATION " + generationCount);
            // Get fittest individuals from elitism and save them for the next generation
            List<Individual> topPerformers = elitism(population, NUMSAVED);
            // Cull the worst performers
            culling(population, NUMREMOVED);
            // Do crossover using all unculled performers as the parent-pool
            population.setIndividuals(crossover(size - NUMSAVED, population.getIndividuals(), numbers));

            // 30% of the performers experience mutation
            for (Individual individual : population.getIndividuals()) {
                if (random.nextInt(10) < 3) {
                    individual.mutation();
                }
            }
            // Elite individuals from previous generation are re-added to the pool of
            // performers
            population.getIndividuals().addAll(topPerformers);
            System.out.println("Best Score: " + elitism(population, 1).get(0).calculateFitness());
            generationCount++;
        }
        return generationCount;
    }

    public void culling(Population population, int numRemoved) {
        List<Float> fitnessScores = new ArrayList<>();
        // Adds the fitness scores of every performer to a list
        for (Individual individual : population.getIndividuals()) {
            fitnessScores.add(individual.calculateFitness());
        }
        // Removes the worst performer, iterates numRemoved times
        for (int i = 0; i < numRemoved; i++) {
            float min = Collections.min(fitnessScores);
            fitnessScores.remove(min);
        }
        // Get the individuals whose scores weren't removed
        List<Individual> individuals = new ArrayList<>();
        for (Individual individual : population.getIndividuals()) {
            if (fitnessScores.contains(individual.getFitness())) {
                individuals.add(individual);
            }
        }
        // Reset the individuals for the population
        population.setIndividuals(individuals);
        System.out.println();
    }

    public List<Individual> elitism(Population population, int numSaved) {
        List<Float> fitnessScores = new ArrayList<>();
        // Grabs the fitness scores of all performers
        for (Individual individual : population.getIndividuals()) {
            fitnessScores.add(individual.calculateFitness());
        }
        List<Individual> topIndividuals = new ArrayList<>();
        for (int i = 0; i < numSaved; i++) {
            // Grabs the best score
            float max = Collections.max(fitnessScores);
            // Finds the corresponding performer and saves it
            for (Individual individual : population.getIndividuals()) {
                if (individual.getFitness() == max) {
                    topIndividuals.add(individual);
                    break;
                }
            }
            // Removes the performer so that the next best performer can be found on the
            // next iteration
            fitnessScores.remove(new Float(max));
        }
        return topIndividuals;
    }

    public List<Individual> crossover(int size, List<Individual> individuals, List<Float> numbers) {
        // Stores the children that are created
        List<Individual> result = new ArrayList<>();
        int numChildren = 0;
        // Grabs the score of every performer
        List<Float> defaultScores = new ArrayList<>();
        for (int i = 0; i < individuals.size(); i++) {
            defaultScores.add(individuals.get(i).calculateFitness());
        }
        // Grabs the minimum score (or 0 if the minimum is above 0)
        Float minScore = Collections.min(defaultScores);
        if (minScore > 0) {
            minScore = new Float(0);
        }
        // Creates the list with gaps corresponding to performer quality
        List<Float> fitnessScores = new ArrayList<>();
        float fitnessSum = 0;
        for (int i = 0; i < individuals.size(); i++) {
            float tempFit = individuals.get(i).calculateFitness() - minScore;
            fitnessSum += tempFit;
            fitnessScores.add(fitnessSum);
        }
        // Creates children until no more are required
        while (numChildren < size) {
            Random random = new Random();
            int parent1Index = 0;
            // Generates 2 random numbers >= 0 and < largest value in fitnessScores
            // Selects the parents by finding the first number in fitnessScores greater than
            // the random numbers
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
            // Grabs the chosen parents
            Individual parent1 = individuals.get(parent1Index);
            Individual parent2 = individuals.get(parent2Index);

            List<Integer> parent1Bins = new ArrayList<>();
            List<Integer> parent2Bins = new ArrayList<>();

            // Randomly chooses the order in which bins from each parents will be paired for
            // crossover
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

            // Creates children that are copies of the parents
            Individual child1 = new Individual();
            Individual child2 = new Individual();
            for (int i = 0; i < 10; i++) {
                child1.getBin1().add(parent1.getBin(1).get(i));
                child1.getBin2().add(parent1.getBin(2).get(i));
                child1.getBin3().add(parent1.getBin(3).get(i));
                child1.getBin4().add(parent1.getBin(4).get(i));
                child2.getBin1().add(parent2.getBin(1).get(i));
                child2.getBin2().add(parent2.getBin(2).get(i));
                child2.getBin3().add(parent2.getBin(3).get(i));
                child2.getBin4().add(parent2.getBin(4).get(i));
            }
            // Generates a random cut point for each pair of bins
            for (int i = 0; i < 4; i++) {
                int cutPoint = random.nextInt(8) + 1;
                for (int j = 0; j < cutPoint; j++) {
                    int parent1Bin = parent1Bins.get(i);
                    int parent2Bin = parent2Bins.get(i);
                    float value1 = parent1.getBin(parent1Bin).get(j);
                    float value2 = parent2.getBin(parent2Bin).get(j);
                    // Swaps all elements in the bins after the cut point
                    child1.getBin(parent1Bin).set(j, value2);
                    child2.getBin(parent2Bin).set(j, value1);
                }
            }
            child1 = removeDuplicates(child1, numbers);
            child2 = removeDuplicates(child2, numbers);

            // Saves the newly created children
            result.add(child1);
            result.add(child2);
            numChildren += 2;
        }
        // Drops the last child if an extra is created
        // All children are created in pairs, so to create an odd number of children,
        // one must be dropped
        if (numChildren > size) {
            result.remove(result.size() - 1);
        }
        return result;
    }

    public Individual removeDuplicates(Individual child, List<Float> numbers) {
        // Stores all numbers in the bins
        List<Float> binValues = new ArrayList<>();
        binValues.addAll(child.getBin1());
        binValues.addAll(child.getBin2());
        binValues.addAll(child.getBin3());
        binValues.addAll(child.getBin4());
        List<Float> duplicates = new ArrayList<>();
        List<Float> missing = new ArrayList<>();

        // Iterates over every number that should appear, counts the number of times it appears
        for (int i = 0; i < 40; i++) {
            int count = 0;
            for (int j = 0; j < 40; j++) {
                if (numbers.get(i).equals(binValues.get(j))) {
                    count++;
                }
            }
            // Numbers that do not appear are added to the 'missing' list
            // Numbers that appear more than once are added to the 'duplicates' list
            if (count == 0) {
                missing.add(numbers.get(i));
            } else if (count > 1) {
                duplicates.add(numbers.get(i));
            }
        }

        int count = 0;
        boolean dupFlag = false;
        // Iterates over every duplicate
        for (int i = 0; i < duplicates.size(); i++) {
            // Iterates over every number in the bins
            for (int j = 0; j < binValues.size(); j++) {
                if (binValues.get(j).equals(duplicates.get(i))) {
                    // The first time the number is equal to the duplicate, nothing changes but a flag is set
                    // The second time the number is equal to the duplicate, it is replaced with a missing value
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

        // Puts the newly modified bin values into the performer
        child.setBin1(binValues.subList(0, 10));
        child.setBin2(binValues.subList(10, 20));
        child.setBin3(binValues.subList(20, 30));
        child.setBin4(binValues.subList(30, 40));
        return child;
    }
}
