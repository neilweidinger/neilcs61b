package byog.Core;

import byog.TileEngine.TETile;

public abstract class Being {
    private Position pos;
    private TETile tile;

    // updates tile 2D array to display being
    public void drawBeing(TETile[][] world) {
        //just checks for indexOutOfBounds error
        if (!isOutOfBoundsOrInvalidTile(world, this.pos)) {
            world[this.pos.x][this.pos.y] = this.tile;
        }
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public void setTile(TETile tile) {
        this.tile = tile;
    }

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

    // reset tile where being is at current position, then update being, draw being, and render world
    private boolean moveHelper(TETile[][] world, Position newPos) {
        if (!isOutOfBoundsOrInvalidTile(world, newPos)) {
            world[this.pos.x][this.pos.y] = Room.innerTile;
            this.pos = newPos;
            this.drawBeing(world);
            Game.ter.renderFrame(world);
            return true;
        }

        return false;
    }

    // static method that takes in a position to see if it can be allowed to be drawn or not
    private static boolean isOutOfBoundsOrInvalidTile(TETile[][] world, Position pos) {
        return (pos.x < world.length && pos.x >= 0 &&
                pos.y < world[0].length && pos.y >= 0 &&
                world[pos.x][pos.y] != Room.innerTile);
    }

    @Override
    public String toString() {
        return "Position x: " + this.pos.x + " y: " + this.pos.y;
    }
}
