import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GA{

    //elitism
    //culling

    public int ga(List<Float> numbers, int puzzle) {
        int size = 10;
        Population population = new Population(size);
        population.initializePopulation(numbers);
        culling(population);
        Individual fittest;
        Individual secondFittest;
        int generationCount = 0;
//        Population population=new Population();
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

    public void culling(Population population) {
        int numToRemove = (int) Math.floor(population.size * 0.2);
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
    }
    private void elitism(Population pop){
        Population top=new Population(topSize, 0, listOfIndiv){
            for (int i=0; i<topSize; i++){
                float random=(float)(Math.random()*pop.individuals.size());
                top.listOfIndiv
            }

        }

    }
}


