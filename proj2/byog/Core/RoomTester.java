package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class RoomTester {
    public static final int WIDTH = 81;
    public static final int HEIGHT = 51;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = WorldBuilder.initializeWorld(WIDTH, HEIGHT);

        Room.drawRandomRooms(world, 10);

        ter.renderFrame(world);
    }
}


