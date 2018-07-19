package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Door implements Serializable {
    private Position pos;
    private TETile tile;

    public Door(TETile[][] world) {
        this.pos = WorldBuilder.getGoalDoorPos(world);
        this.tile = Tileset.LOCKED_DOOR;
    }

    public Position getPos() {
        return this.pos;
    }

    public void drawDoor(TETile[][] world) {
        world[this.pos.x][this.pos.y] = this.tile;
    }
}
