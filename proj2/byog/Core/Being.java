package byog.Core;

import byog.TileEngine.TETile;

public abstract class Being {
    private Position pos;
    private TETile tile;

    // updates tile 2D array to display being
    public void drawBeing(TETile[][] world) {
        if (isInBoundsAndValidTile(world, this.pos)) {
            world[this.pos.x][this.pos.y] = this.tile;
        }
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public void setTile(TETile tile) {
        this.tile = tile;
    }

    // each move method moves being AND draws into our world 2D array
    // STILL NEED TO RENDER AFTER CALLING THIS
    public boolean moveUp(TETile[][] world) {
        Position newPos = new Position(this.pos.x, this.pos.y + 1);
        return moveHelper(world, newPos);
    }

    public boolean moveRight(TETile[][] world) {
        Position newPos = new Position(this.pos.x + 1, this.pos.y);
        return moveHelper(world, newPos);
    }

    public boolean moveDown(TETile[][] world) {
        Position newPos = new Position(this.pos.x, this.pos.y - 1);
        return moveHelper(world, newPos);
    }

    public boolean moveLeft(TETile[][] world) {
        Position newPos = new Position(this.pos.x - 1, this.pos.y);
        return moveHelper(world, newPos);
    }

    // reset tile where being is at current position, then update being, and draw being
    // DOES NOT RENDER WORLD
    private boolean moveHelper(TETile[][] world, Position newPos) {
        if (isInBoundsAndValidTile(world, newPos)) {
            world[this.pos.x][this.pos.y] = Room.innerTile;
            this.pos = newPos;
            this.drawBeing(world);
            return true;
        }

        return false;
    }

    // static method that takes in a position to see if it is allowed to be drawn or not
    private static boolean isInBoundsAndValidTile(TETile[][] world, Position pos) {
        return (pos.x < world.length && pos.x >= 0 &&
                pos.y < world[0].length && pos.y >= 0 &&
                world[pos.x][pos.y] == Room.innerTile);
    }

    @Override
    public String toString() {
        return "Position x: " + this.pos.x + " y: " + this.pos.y;
    }
}
