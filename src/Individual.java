public class Individual {
    int fitness = 0;
    int indLength = 10;
    int[] bin1 = new int[10];
    int[] bin2 = new int[10];
    int[] bin3 = new int[10];
    int[] bin4 = new int[10];

    public Individual() {
    }

    public void calculateFitness() {
        int bin1Score = 1;
        for (int num : bin1) {
            bin1Score *= num;
        }

        int bin2Score = 0;
        for (int num : bin2) {
            bin2Score += num;
        }

        int max = -10;
        int min = 10;
        for (int num : bin3) {
            if (num > max) {
                max = num;
            }
            if (num < min) {
                min = num;
            }
        }
        int bin3Score = max - min;

        fitness = bin1Score + bin2Score + bin3Score;
    }
}
