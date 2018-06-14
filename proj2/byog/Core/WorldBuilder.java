package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class WorldBuilder {

    // fill in world with a single type of tile
    public static TETile[][] initializeWorld(int width, int height) {
        TETile[][] world = new TETile[width][height];

        for (int x = 0; x < world.length; x++) {
            for (int y = 0; y < world[0].length; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        return world;
    }
}
