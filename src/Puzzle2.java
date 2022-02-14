import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Puzzle2 {
    public void ga(List<Piece> pieces, int seconds) {
        Random random = new Random();
        // Grabs the time when execution starts
        long startTime = System.currentTimeMillis() / 1000;
        // Population size
        int size = 100;
        // Top 20% are saved through elitism, bottom 30% are culled
        int NUMSAVED = (int) Math.floor(0.2 * (double) size);
        int NUMREMOVED = (int) Math.floor(0.3 * (double) size);
        TowerPopulation population = new TowerPopulation(size);
        population.initializePopulation(pieces);
        int generationCount = 0;
        int bestGen = 0;
        float bestScore = Float.NEGATIVE_INFINITY;
        System.out.println("Starting Genetic Algoritm...");
        while (System.currentTimeMillis() / 1000 < startTime + seconds) {
            // Get fittest individuals from elitism and save them for the next generation
            List<Tower> topPerformers = elitism(population, NUMSAVED);
            // Cull the worst performers
            culling(population, NUMREMOVED);
            // Perform crossover using all unculled performers as the parent-pool
            population.setTowers(crossover(size - NUMSAVED, population.getTowers(), pieces));
            // 30% of the performers experience mutation
            for (Tower individual : population.getTowers()) {
                if (random.nextInt(10) < 3) {
                    individual.mutation();
                }
            }
            // Elite individuals from previous generation are re-added to the pool of performers
            population.getTowers().addAll(topPerformers);
            //Update the generation of the best tower if necessary
            for (Tower tower : population.getTowers()) {
                if (tower.calculateScore() > bestScore) {
                    bestScore = tower.calculateScore();
                    bestGen = generationCount;
                }
            }
            generationCount++;
        }
        // Execution summary
        System.out.println("Ran for " + generationCount + " generations\n");
        System.out.println("Best Performer:");
        Tower bestPerformer = elitism(population, 1).get(0);
        for (int i = 0; i < bestPerformer.getPieces().size(); i++) {
            Piece piece = bestPerformer.getPieces().get(i);
            System.out.println(piece.getType() + " " + piece.getWidth() + " " + piece.getStrength() + " " + piece.getCost());
        }
        System.out.println("\nScore: " + bestScore );
        System.out.println("Solution found in generation " + bestGen);
    
    }

    public void culling(TowerPopulation population, int numRemoved) {
        List<Integer> scores = new ArrayList<>();
        // Adds the fitness scores of every performer to a list
        for (Tower tower : population.getTowers()) {
            scores.add(tower.calculateScore());
        }
        // Removes the worst performer, iterates numRemoved times
        for (int i = 0; i < numRemoved; i++) {
            int min = Collections.min(scores);
            scores.remove(new Integer(min));
        }
        // Get the individuals whose scores weren't removed
        List<Tower> individuals = new ArrayList<>();
        for (Tower tower : population.getTowers()) {
            if (scores.contains(tower.getScore())) {
                individuals.add(tower);
            }
        }
        // Resets the population
        population.setTowers(individuals);
    }

    public List<Tower> elitism(TowerPopulation population, int numSaved) {
        List<Integer> scores = new ArrayList<>();
        // Grabs the fitness scores of all performers
        for (Tower tower : population.getTowers()) {
            scores.add(tower.calculateScore());
        }
        List<Tower> topIndividuals = new ArrayList<>();
        for (int i = 0; i < numSaved; i++) {
            // Grabs the best score
            int max = Collections.max(scores);
            // Finds the corresponding performer and saves it
            for (Tower tower : population.getTowers()) {
                if (tower.getScore() == max) {
                    topIndividuals.add(tower);
                    break;
                }
            }
            // Removes the performer so that the next best performer can be found on the next iteration
            scores.remove(new Integer(max));
        }
        return topIndividuals;
    }

    public List<Tower> crossover(int size, List<Tower> individuals, List<Piece> pieces) {
        // Stores the children that are created
        List<Tower> result = new ArrayList<>();
        int numChildren = 0;
        // Creates the list with gaps corresponding to performer quality
        List<Float> scores = new ArrayList<>();
        float fitnessSum = 0;
        for (int i = 0; i < individuals.size(); i++) {
            float tempFit = individuals.get(i).calculateScore();
            fitnessSum += tempFit;
            scores.add(fitnessSum);
        }
        // Creates children until no more are required
        while (numChildren < size) {
            Random random = new Random();
            int parent1Index = 0;
            // Generates 2 random numbers >= 0 and < largest value in fitnessScores
            // Selects the parents by finding the first number in fitnessScores greater than
            // the random numbers
            float parent1Prob = random.nextFloat() * scores.get(scores.size() - 1);
            for (int i = 0; i < individuals.size(); i++) {
                if (scores.get(i) >= parent1Prob) {
                    parent1Index = i;
                    break;
                }
            }
            int parent2Index = parent1Index;
            while (parent1Index == parent2Index) {
                float parent2Prob = random.nextFloat() * scores.get(scores.size() - 1);
                for (int i = 0; i < individuals.size(); i++) {
                    if (scores.get(i) > parent2Prob || scores.get(i).equals(parent1Prob)) {
                        parent2Index = i;
                        break;
                    }
                }
                if (parent1Index == parent2Index) {
                    if (parent2Index < scores.size() - 1 && scores.get(parent2Index).equals(scores.get(parent2Index + 1))) {
                        parent2Index++;
                    }
                    else if (parent2Index == scores.size() - 1 && scores.get(parent2Index - 1) == 0.0){
                        parent2Index--;
                    }
                }
            }
            // Grabs the chosen parents
            Tower parent1 = individuals.get(parent1Index);
            Tower parent2 = individuals.get(parent2Index);

            // Randomly chooses cut points within the towers
            int cutPoint1 = random.nextInt(parent1.getPieces().size());
            int cutPoint2 = random.nextInt(parent2.getPieces().size());

            Tower child1 = new Tower();
            Tower child2 = new Tower();

            // Assembles towers for the children using the parents and cutpoints
            for (int i = 0; i < cutPoint2; i++) {
                child1.getPieces().add(parent2.getPieces().get(i));
            }
            for (int i = cutPoint1; i < parent1.getPieces().size(); i++) {
                child1.getPieces().add(parent1.getPieces().get(i));
            }
            for (int i = 0; i < cutPoint1; i++) {
                child2.getPieces().add(parent1.getPieces().get(i));
            }
            for (int i = cutPoint2; i < parent2.getPieces().size(); i++) {
                child2.getPieces().add(parent2.getPieces().get(i));
            }
            // Replaces all duplicates in the children with the missing values
            child1 = removeDuplicates(child1, pieces);
            child2 = removeDuplicates(child2, pieces);

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

    public Tower removeDuplicates(Tower child, List<Piece> pieces) {
        // Grabs all the pieces of the specified tower
        List<Piece> childPieces = child.getPieces();
        List<Piece> duplicates = new ArrayList<>();
        List<Piece> missing = new ArrayList<>();
        // Iterates over the tower, counts the number of times each piece appears
        for (Piece piece : pieces) {
            int count = 0;
            for (Piece childPiece : childPieces) {
                if (piece.equals(childPiece)) {
                    count++;
                }
            }
            // Pieces that do not appear are added to the 'missing' list
            // Pieces that appear more than once are added to the 'duplicates' list
            if (count == 0) {
                missing.add(piece);
            } else if (count > 1) {
                duplicates.add(piece);
            }
        }
        int count = 0;
        boolean dupFlag = false;
        // Iterates over every duplicate
        List<Piece> toRemove = new ArrayList<>();
        for (int i = 0; i < duplicates.size(); i++) {
            // Iterates over every piece in the tower
            for (int j = 0; j < childPieces.size(); j++) {
                if (childPieces.get(j).equals(duplicates.get(i))) {
                    if (!dupFlag) {
                        dupFlag = true;
                    } else {
                        // Replace duplicates with missing values
                        // If there are no missing values left, remove the piece
                        if (count < missing.size()) {
                            childPieces.set(j, missing.get(count));
                        } else {
                            toRemove.add(childPieces.get(j));
                        }
                        count++;
                    }
                }
            }
            dupFlag = false;
        }
        // Executes the removal if necessary
        childPieces.removeAll(toRemove);

        return child;
    }
}
