public class GA{

    public int ga(){

        List<Piece> visited=new ArrayList<Piece>(); //visited pieces for puzzle 2 and puzzle 1?

        boolean worthy=false; //for second puzzle

    }

    private int calculateFinalScore(int userInput){
        int score=0;
        switch(userInput){
            case 1:
                score=GA.puzzle1(int[] nums); //series of 40 nums one per line
                break;
            case 2:
                //score=GA.puzzle2()
                //break;

        }
        return score;
    }
}