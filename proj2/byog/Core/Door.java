package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Door {
    private Position pos;
    private TETile tile;

    public Door(TETile[][] world) {
        this.pos = WorldBuilder.getGoalDoorPos(world);
        this.tile = Tileset.LOCKED_DOOR;
    }

    public void drawDoor(TETile[][] world) {
        world[this.pos.x][this.pos.y] = this.tile;
    }
}
