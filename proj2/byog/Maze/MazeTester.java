package byog.Maze;

import byog.Core.WorldBuilder;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class MazeTester {
    public static final int WIDTH = 81;
    public static final int HEIGHT = 51;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = WorldBuilder.initializeWorld(WIDTH, HEIGHT);

        Maze.generateMaze(world, 1);

        ter.renderFrame(world);
    }
}
