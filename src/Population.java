public class Population {

    int popSize;
    int fittest;
    Individual[] indiv;

    //how many "parents"?
    //

    public void initializePopulation(int popSize, float[] numbers) {
        for (int i = 0; i < indiv.length; i++) {
            indiv[i] = new Individual();
        }
    }

    //while (time!=0) iterate through ga
}
