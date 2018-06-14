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
}
