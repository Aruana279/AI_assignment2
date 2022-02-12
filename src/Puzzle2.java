import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Puzzle2 {
    public void ga(List<Piece> pieces, int seconds) {
        long startTime = System.currentTimeMillis() / 1000;
        int size = 10;
        int NUMSAVED = (int) Math.floor(0.2 * (double) size);
        int NUMREMOVED = (int) Math.floor(0.3 * (double) size);
        TowerPopulation population = new TowerPopulation(size);
        population.initializePopulation(pieces);
        int generationCount = 0;
        while (System.currentTimeMillis() / 1000 < startTime + seconds) {
            System.out.println("\nGENERATION " + generationCount);
            // Get fittest individuals from elitism and use those for the next generation
            List<Tower> topPerformers = elitism(population, NUMSAVED);
            culling(population, NUMREMOVED);
            population.setTowers(crossover(size - NUMSAVED, population.getTowers(), pieces));
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
            scores.remove(min);
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
        return result;
    }
}
