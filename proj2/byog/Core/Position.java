package byog.Core;

public class Position {
    public int x;
    public int y;

    public Position() {
        this.x = 0;
        this.y = 0;
    }

    public Position(Position p) {
        this.x = p.x;
        this.y = p.y;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Position)) return false;
        if (this == other) return true;

        Position p = (Position) other;
        return (p.x == this.x && p.y == this.y);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.x;
        result = 31 * result + this.y;
        return result;
    }
}
