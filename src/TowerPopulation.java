import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TowerPopulation {
    private int size;
    private List<Tower> towers;

    public TowerPopulation(int size) {
        this.size = size;
        this.towers = new ArrayList<>();
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public void setTowers(List<Tower> towers) {
        this.towers = towers;
    }

    public void initializePopulation(List<Piece> pieces) {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            // Randomly chooses a height for the tower
            int height = random.nextInt(pieces.size() - 1) + 1;
            Tower tower = new Tower();
            // Randomly selects pieces and adds them to the tower, avoids duplicates
            List<Piece> towerPieces = new ArrayList<>();
            for (int j = 0; j < height; j++) {
                int index = 0;
                while (towerPieces.contains(pieces.get(index))) {
                    index = random.nextInt(pieces.size());
                }
                towerPieces.add(pieces.get(index));
            }
            // Updates the generated tower and adds it to the population
            tower.setPieces(towerPieces);
            towers.add(tower);
        }
    }
}
