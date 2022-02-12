import java.util.ArrayList;
import java.util.List;

public class Tower {
    private List<Piece> pieces = new ArrayList<>();

    public int calculateScore() {
        // Check rules 1 and 2
        if (!pieces.get(0).getType().equals("door")) {
            return 0;
        } else if (!pieces.get(pieces.size() - 1).getType().equals("lookout")) {
            return 0;
        }
        // Check rule 3
        // Exclude the first and last pieces since they shouldn't be walls
        for (int i = 1; i < pieces.size() - 1; i++) {
            if (!pieces.get(i).getType().equals("wall")) {
                return 0;
            }
        }
        // Check rule 4
        for (int i = 0; i < pieces.size() - 1; i++) {
            if (pieces.get(i).getWidth() < pieces.get(i + 1).getWidth()) {
                return 0;
            }
        }
        // Check rule 5
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).getStrength() < pieces.size() - 1 - i) {
                return 0;
            }
        }
        return 10 + ((int) Math.pow(pieces.size(), 2)) - calculatePieceCost();
    }

    public int calculatePieceCost() {
        int cost = 0;
        for (Piece piece : pieces) {
            cost += piece.getCost();
        }
        return cost;
    }
}
