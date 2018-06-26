package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayDeque;
import java.util.Random;

public class WorldBuilder {
    // default just in case setSeed isn't called
    public static long SEED = 8;
    public static Random r = new Random(SEED);
    public static Position playerStart;

    public static void setSeed(long seed) {
        WorldBuilder.SEED = seed;
        WorldBuilder.r = new Random(seed);
    }

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

    // generate main world with rooms and hallways
    public static void generateWorld(TETile[][] world) {
        // our stack of rooms should we have to backtrace
        ArrayDeque<Room> stack = new ArrayDeque<>();

        // generate a random room that isn't out of bounds
        Room oldRoom = new Room();
        while (oldRoom.hasOverlapOrOutOfBounds(world)) {
            oldRoom = new Room();
        }

        // set position where player will start game
        playerStart = new Position(oldRoom.returnConnectionPos());

        // draw our initial room and push it to the stack
        oldRoom.drawRoom(world);
        stack.push(oldRoom);

        // generate a new room that goes in a random direction from our current room
        // this room only has one walkable tile, so should generate a hallway turn
        Room newRoom = new Room(3, 3);
        stack.push(newRoom);

        while (!stack.isEmpty()) {
            // updates position of newRoom and enters block if there is space to draw
            if (newRoom.branchFrom(world, oldRoom)) {
                newRoom.drawRoom(world);
                newRoom.drawHallwayFrom(world, oldRoom);

                oldRoom = new Room(newRoom); //update our oldRoom
                newRoom = new Room(); //regenerate room of random size

                stack.push(newRoom); //add this newly generated room to our stack
            }
            // wasn't able to draw newRoom, go back in the stack
            else {
                stack.pop();
                newRoom = new Room();
                oldRoom = stack.peek();
            }
        }
    }
}
