public class Population {

    int popSize;
    int fittest;
    Individual[] indiv = new Individual[40];

    public void initPopulation(int popSize){
        for (int i = 0; i < indiv.length; i++) {
            indiv[i] = new Individual();
        }


    }

    public void calcFitness(int[] nums){
        return ;
    }

    public void totalScoreForPuzzleOne(int[] ns){
        return;
    }


}
