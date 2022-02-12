public class Piece {
    private String type;
    private int width;
    private int strength;
    private int cost;

    public Piece(String type, int width, int strength, int cost) {
        this.type = type;
        this.width = width;
        this.strength = strength;
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getStrength() {
        return strength;
    }

    public int getCost() {
        return cost;
    }
}