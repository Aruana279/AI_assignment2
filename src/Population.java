public class Population {

    int popSize;
    int fittest;
    Individual[] indiv;

    public void initializePopulation(int popSize, float[] numbers) {
        for (int i = 0; i < indiv.length; i++) {
            indiv[i] = new Individual();
        }
    }
    public void findTheBestPopulation(int popSize, float fitnessScore){
        float best=indiv[0].calculateFitness();
        for (int i = 1; i < popSize-1; i++){
            float temp=indiv[i].calculateFitness();
            if (best<temp){
                best=temp;
            }
        }
    }


}
