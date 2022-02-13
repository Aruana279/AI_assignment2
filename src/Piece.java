import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return width == piece.width && strength == piece.strength && cost == piece.cost && Objects.equals(type, piece.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, width, strength, cost);
    }
}