import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Puzzle2 {
    public void ga(List<Piece> pieces, int seconds) {
        Random random = new Random();
        long startTime = System.currentTimeMillis() / 1000;
        int size = 10;
        int NUMSAVED = (int) Math.floor(0.2 * (double) size);
        int NUMREMOVED = (int) Math.floor(0.3 * (double) size);
        TowerPopulation population = new TowerPopulation(size);
        population.initializePopulation(pieces);
        int generationCount = 0;
        while (System.currentTimeMillis() / 1000 < startTime + seconds) {
        //while (generationCount < 2) {
            System.out.println("\nGENERATION " + generationCount);
            // Get fittest individuals from elitism and use those for the next generation
            List<Tower> topPerformers = elitism(population, NUMSAVED);
            culling(population, NUMREMOVED);
            population.setTowers(crossover(size - NUMSAVED, population.getTowers(), pieces));


            for (Tower individual : population.getTowers()) {
                if (random.nextInt(10) < 3) {
                    individual.mutation();
                    System.out.println("MUTATION OCCURRED");
                }
            }

            population.getTowers().addAll(topPerformers);

            for (Tower individual : population.getTowers()) {
                for (int i = 0; i < individual.getPieces().size(); i++) {
                    Piece piece = individual.getPieces().get(i);
                    System.out.println(piece.getType() + " " + piece.getWidth() + " " + piece.getStrength() + " " + piece.getCost());
                }
                System.out.println("Score: " + individual.calculateScore());
                System.out.println();
            }

            System.out.println("Best Score: " + elitism(population, 1).get(0).calculateScore());
            generationCount++;
        }
    }

    public void culling(TowerPopulation population, int numRemoved) {
        List<Integer> scores = new ArrayList<>();
        for (Tower tower : population.getTowers()) {
            scores.add(tower.calculateScore());
        }
//        for (float num : fitnessScores) {
//            System.out.print(num + " ");
//        }
//        System.out.println();
        for (int i = 0; i < numRemoved; i++) {
            int min = Collections.min(scores);
            scores.remove(new Integer(min));
//            System.out.println("Removed: " + min);
        }
        // Get the individuals whose scores weren't removed
        List<Tower> individuals = new ArrayList<>();
        for (Tower tower : population.getTowers()) {
            if (scores.contains(tower.getScore())) {
                individuals.add(tower);
            }
        }
        // Reset the individuals for the population
        population.setTowers(individuals);
//        System.out.println();
//        System.out.println(population.getTowers().size());
    }

    public List<Tower> elitism(TowerPopulation population, int numSaved) {
        List<Integer> scores = new ArrayList<>();

        for (Tower tower : population.getTowers()) {
            scores.add(tower.calculateScore());
        }
        List<Tower> topIndividuals = new ArrayList<>();
        for (int i = 0; i < numSaved; i++) {
            int max = Collections.max(scores);
            for (Tower tower : population.getTowers()) {
                if (tower.getScore() == max) {
                    topIndividuals.add(tower);
                    break;
                }
            }
            scores.remove(new Integer(max));
        }
        return topIndividuals;
    }

    public List<Tower> crossover(int size, List<Tower> individuals, List<Piece> pieces) {
        List<Tower> result = new ArrayList<>();
        int numChildren = 0;
        List<Float> scores = new ArrayList<>();
        float fitnessSum = 0;
        for (int i = 0; i < individuals.size(); i++) {
            float tempFit = individuals.get(i).calculateScore();
            fitnessSum += tempFit;
            scores.add(fitnessSum);
        }
        while (numChildren < size) {
            Random random = new Random();
            int parent1Index = 0;
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
            // Randomly selected parents
            Tower parent1 = individuals.get(parent1Index);
            Tower parent2 = individuals.get(parent2Index);

//            System.out.println("Parent 1:");
//            for (int i = 0; i < parent1.getPieces().size(); i++) {
//                Piece piece = parent1.getPieces().get(i);
//                System.out.println(piece.getType() + " " + piece.getWidth() + " " + piece.getStrength() + " " + piece.getCost());
//            }
//            System.out.println();
//            System.out.println("Parent 2:");
//            for (int i = 0; i < parent2.getPieces().size(); i++) {
//                Piece piece = parent2.getPieces().get(i);
//                System.out.println(piece.getType() + " " + piece.getWidth() + " " + piece.getStrength() + " " + piece.getCost());
//            }

            // Randomly selected cut points
            int cutPoint1 = random.nextInt(parent1.getPieces().size());
            int cutPoint2 = random.nextInt(parent2.getPieces().size());

            Tower child1 = new Tower();
            Tower child2 = new Tower();

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
            child1 = removeDuplicates(child1, pieces);
            child2 = removeDuplicates(child2, pieces);

//            System.out.println();
//            System.out.println("Child 1:");
//            for (int i = 0; i < child1.getPieces().size(); i++) {
//                Piece piece = child1.getPieces().get(i);
//                System.out.println(piece.getType() + " " + piece.getWidth() + " " + piece.getStrength() + " " + piece.getCost());
//            }
//            System.out.println();
//            System.out.println("Child 2:");
//            for (int i = 0; i < child2.getPieces().size(); i++) {
//                Piece piece = child2.getPieces().get(i);
//                System.out.println(piece.getType() + " " + piece.getWidth() + " " + piece.getStrength() + " " + piece.getCost());
//            }
//            System.out.println();

            result.add(child1);
            result.add(child2);
            numChildren += 2;
        }
        if (numChildren > size) {
            result.remove(result.size() - 1);
        }
        return result;
    }

    public Tower removeDuplicates(Tower child, List<Piece> pieces) {
        List<Piece> duplicates = new ArrayList<>();
        List<Piece> missing = new ArrayList<>();
        List<Piece> childPieces = child.getPieces();
        for (Piece piece : pieces) {
            int count = 0;
            for (Piece childPiece : childPieces) {
                if (piece.equals(childPiece)) {
                    count++;
                }
            }
            if (count == 0) {
                missing.add(piece);
            } else if (count > 1) {
                duplicates.add(piece);
            }
        }
//        System.out.println("DUPLICATES: " + duplicates.size());
//        System.out.println("MISSING: " + missing.size());

        int count = 0;
        boolean dupFlag = false;
        List<Piece> toRemove = new ArrayList<>();
        for (int i = 0; i < duplicates.size(); i++) {
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
        childPieces.removeAll(toRemove);

        return child;
    }
}
