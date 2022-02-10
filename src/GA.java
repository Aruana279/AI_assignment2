import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GA {

    //elitism
    //culling
    // the solution is the four bins for the highest scoring individual
    // print out the score for the highest scoring individual
    // the highest scoring individual should be found at the last generation as long as elitism is not 0

    public int ga(List<Float> numbers, int puzzle) {
        int size = 10;
        Population population = new Population(size);
        population.initializePopulation(numbers);
        List<Individual> newIndividuals = new ArrayList<>();
        // Get fittest individuals from elitism and use those for the next generation
//        elitism(population);
        int numRemoved = culling(population);
        // Do crossover on remaining individuals and fittest individuals
        System.out.println("newIndividuals size: " + population.individuals.size());
        newIndividuals.addAll(crossover(size - numRemoved, population.individuals));
        System.out.println("newIndividuals size: " + newIndividuals.size());
        Individual fittest;
        Individual secondFittest;
        int generationCount = 0;
        return generationCount;

//        public void findTheHighestFitness(int popSize){
//            float best=indiv[0].calculateFitness();
//            for (int i = 1; i < popSize-1; i++){
//                float temp=indiv[i].calculateFitness();
//                if (best<temp){
//                    best=temp;
//                }
//            }
//        }
    }

//    public List<Individual> elitism(Population population) {
//        int numToKeep = (int) Math.floor(population.size * 0.3);
//        List<Individual> eliteIndividuals = new ArrayList<>();
//        float max = Float.MIN_VALUE;
//
//        List<Float> fitnessScores = new ArrayList<>();
//        for (Individual individual : population.individuals) {
//            fitnessScores.add(individual.calculateFitness());
//        }
//    }

    public int culling(Population population) {
        int numToRemove = (int) Math.floor(population.size * 0.3);
        List<Float> fitnessScores = new ArrayList<>();
        for (Individual individual : population.individuals) {
            fitnessScores.add(individual.calculateFitness());
        }
        for (float num : fitnessScores) {
            System.out.print(num + " ");
        }
        System.out.println();
        for (int i = 0; i < numToRemove; i++) {
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
        return numToRemove;
    }

    public List<Individual> crossover(int size, List<Individual> individuals) {
        ////        ThreadLocalRandom.current().nextInt(min, max + 1);
        List<Individual> result = new ArrayList<>();
        int numChildren = 0;
        while (numChildren < size) {
            Random random = new Random();
            int parent1Index = random.nextInt(size);
            int parent2Index = random.nextInt(size);
            while (parent1Index == parent2Index) {
                parent1Index = random.nextInt(size);
            }
            Individual parent1 = individuals.get(parent1Index);
            Individual parent2 = individuals.get(parent2Index);

            int parent1RandomBin = random.nextInt(4) + 1;
            int parent2RandomBin = random.nextInt(4) + 1;
            System.out.println("Before crossover");
            for (float num : parent1.getBin(parent1RandomBin)) {
                System.out.print(num + " ");
            }
            System.out.println();
            for (float num : parent2.getBin(parent2RandomBin)) {
                System.out.print(num + " ");
            }
            System.out.println();

            int cutPoint = random.nextInt(8) + 1;
            for (int j = 0; j < cutPoint; j++) {
                float value1 = parent1.getBin(parent1RandomBin).get(j);
                float value2 = parent2.getBin(parent2RandomBin).get(j);
                parent1.getBin(parent1RandomBin).set(j, value2);
                parent2.getBin(parent2RandomBin).set(j, value1);
            }
            result.add(parent1);
            result.add(parent2);
            numChildren += 2;

            System.out.println("After crossover");
            for (float num : parent1.getBin(parent1RandomBin)) {
                System.out.print(num + " ");
            }
            System.out.println();
            for (float num : parent2.getBin(parent2RandomBin)) {
                System.out.print(num + " ");
            }
            System.out.println();
            System.out.println();
            System.out.println();
        }
//        if (numChildren > size) {
//            result.remove(result.size() - 1);
//        }
        return result;
    }
//    private void elitism(Population pop){
//        Population top=new Population(topSize, 0, listOfIndiv){
//            for (int i=0; i<topSize; i++){
//                float random=(float)(Math.random()*pop.individuals.size());
//                top.listOfIndiv
//            }
//
//        }
//
//    }
}


