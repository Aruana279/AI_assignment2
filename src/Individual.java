import java.util.ArrayList;
import java.util.List;

public class Individual {
    float fitness = 0;
    int indLength = 10;
    List<Float> bin1 = new ArrayList<>();
    List<Float> bin2 = new ArrayList<>();
    List<Float> bin3 = new ArrayList<>();
    List<Float> bin4 = new ArrayList<>();

    public Individual() {
    }

//    public void calculateFitness() {
//        int bin1Score = 1;
//        for (int num : bin1) {
//            bin1Score *= num;
//        }
//
//        int bin2Score = 0;
//        for (int num : bin2) {
//            bin2Score += num;
//        }
//
//        int max = -10;
//        int min = 10;
//        for (int num : bin3) {
//            if (num > max) {
//                max = num;
//            }
//            if (num < min) {
//                min = num;
//            }
//        }
//        int bin3Score = max - min;
//
//        fitness = bin1Score + bin2Score + bin3Score;
//    }


    public float calculateFitness() {
        float bin1Score = 1;
        for (float num : bin1) {
            bin1Score *= num;
        }

        float bin2Score = 0;
        for (float num : bin2) {
            bin2Score += num;
        }

        float max = -10;
        float min = 10;
        for (float num : bin3) {
            if (num > max) {
                max = num;
            }
            if (num < min) {
                min = num;
            }
        }
        float bin3Score = max - min;

        fitness = bin1Score + bin2Score + bin3Score;
        return fitness;
    }
}
