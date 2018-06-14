package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Room {
    public static void drawRandomRooms(TETile[][] world, int numRooms) {
        Random r = new Random(543245);
        int [] randX = RandomUtils.permutation(r, world.length);
        int [] randY = RandomUtils.permutation(r, world[0].length);

        for (int i = 0; i < numRooms; i++) {
            drawRoom(world, new Position(randX[i], randY[i]), RandomUtils.uniform(r, 8), RandomUtils.uniform(r, 8), Tileset.FLOWER);
        }
    }

    public static void drawRoom(TETile[][] world, Position pos, int width, int len, TETile tile) {
        for (int x = pos.x; x < pos.x + width; x++) {
            for (int y = pos.y; y < pos.y + len; y++) {
                if (x < world.length && x >= 0 && y < world[0].length && y >= 0) {
                    world[x][y] = tile;
                }
            }
        }
    }
}
