package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Room {
    private Position pos;
    private Position connectionPos;
    private int width;
    private int len;

    public static TETile innerTile = Tileset.FLOWER;
    public static TETile outerTile = Tileset.WALL;

    public Room() {
        this.pos = new Position(RandomUtils.uniform(WorldBuilder.r, Game.WIDTH),
                                RandomUtils.uniform(WorldBuilder.r, Game.HEIGHT));
        this.width = RandomUtils.uniform(WorldBuilder.r, 3, 15);
        this.len = RandomUtils.uniform(WorldBuilder.r, 3, 15);
        this.connectionPos = getRandomConnectionPos();
    }

    public Room(int width, int len) {
        this.pos = new Position(RandomUtils.uniform(WorldBuilder.r, Game.WIDTH),
                                RandomUtils.uniform(WorldBuilder.r, Game.HEIGHT));
        this.width = width;
        this.len = len;
        this.connectionPos = getRandomConnectionPos();
    }

    public Room(Room room) {
        this.pos = room.pos;
        this.width = room.width;
        this.len = room.len;
        this.connectionPos = room.connectionPos;
    }

    public void drawRoom(TETile[][] world) {
        for (int x = this.pos.x; x < this.pos.x + this.width; x++) {
            for (int y = this.pos.y; y < this.pos.y + this.len; y++) {
                //just checks for indexOutOfBounds error
                if (x < world.length && x >= 0 && y < world[0].length && y >= 0) {
                    //if drawing outline of room
                    if (x == this.pos.x || x == (this.pos.x + this.width) - 1 || y == this.pos.y || y == (this.pos.y + this.len) - 1) {
                        world[x][y] = outerTile;
                    }
                    //if drawing inner part of room
                    else {
                        world[x][y] = innerTile;
                    }
                }
            }
        }
    }

    // draws a hallway between two rooms
    public void drawHallwayFrom(TETile[][] world, Room oldRoom) {
        //we only need to draw one hallway since connection points are aligned
        if (this.connectionPos.x == oldRoom.connectionPos.x || this.connectionPos.y == oldRoom.connectionPos.y) {
            drawHallwayHelper(world, this.connectionPos, oldRoom.connectionPos);
        }
        //connection points are not aligned, we need to draw two hallways
        else {
            Position hallwayBend = new Position(this.connectionPos.x, oldRoom.connectionPos.y);
            drawHallwayHelper(world, this.connectionPos, hallwayBend);
            drawHallwayHelper(world, hallwayBend, oldRoom.connectionPos);
            drawHallwayCorner(world, hallwayBend);
        }
    }

    // decides whether to draw a horizontal or a vertical hallway between two points
    private void drawHallwayHelper(TETile[][] world, Position a, Position b) {
        if (a.x == b.x) {
            drawVerticalHallway(world, a, b);
        }
        else {
            drawHorizontalHallway(world, a, b);
        }
    }

    private void drawHorizontalHallway(TETile[][] world, Position a, Position b) {
        int xStart = Math.min(a.x, b.x);
        int xEnd = Math.max(a.x, b.x);

        for (; xStart <= xEnd; xStart++) {
            //just checks for indexOutOfBounds error
            if (xStart < world.length && xStart >= 0 && a.y < world[0].length && a.y >= 0) {
                //doesn't draw hallway walls where there are walkable areas (prevent cutting rooms off)
                if (world[xStart][a.y] != Room.innerTile)
                    world[xStart][a.y] = Room.innerTile;
                if (world[xStart][a.y - 1] != Room.innerTile) 
                    world[xStart][a.y - 1] = Room.outerTile;
                if (world[xStart][a.y + 1] != Room.innerTile) 
                    world[xStart][a.y + 1] = Room.outerTile;
            }
        }
    }

    private void drawVerticalHallway(TETile[][] world, Position a, Position b) {
        int yStart = Math.min(a.y, b.y);
        int yEnd = Math.max(a.y, b.y);

        for (; yStart <= yEnd; yStart++) {
            //just checks for indexOutOfBounds error
            if (a.x < world.length && a.x >= 0 && yStart < world[0].length && yStart >= 0) {
                //doesn't draw hallway walls where there are walkable areas (prevent cutting rooms off)
                if (world[a.x][yStart] != Room.innerTile)
                    world[a.x][yStart] = Room.innerTile;
                if (world[a.x - 1][yStart] != Room.innerTile)
                    world[a.x - 1][yStart] = Room.outerTile;
                if (world[a.x + 1][yStart] != Room.innerTile)
                    world[a.x + 1][yStart] = Room.outerTile;
            }
        }
    }

    // because at hallway intersections it is difficult to draw a perfect corner,
    // this method just "paints" over NOTHING tiles with a WALL tile in the 3x3 corner
    private void drawHallwayCorner(TETile[][] world, Position corner) {
        for (int x = corner.x - 1; x <= corner.x + 1; x++) {
            for (int y = corner.y - 1; y <= corner.y + 1; y++) {
                if (world[x][y] == Tileset.NOTHING) {
                    world[x][y] = Room.outerTile;
                }
            }
        }
    }

    // if a room would be drawn on a tile that is not Tileset.NOTHING, it has an overlap
    // this method also makes sure the room within the visible world
    public boolean hasOverlapOrOutOfBounds(TETile[][] world) {
        for (int x = this.pos.x; x < this.pos.x + this.width; x++) {
            for (int y = this.pos.y; y < this.pos.y + this.len; y++) {
                //just checks for indexOutOfBounds error
                if (x < world.length && x >= 0 && y < world[0].length && y >= 0) {
                    if (!world[x][y].equals(Tileset.NOTHING)) {
                        return true;
                    }
                }
                else {
                    return true;
                }
            }
        }

        return false;
    }

    // tries to branch (update position) of a room in every direction, returns true if successful
    public boolean branchFrom(TETile[][] world, Room oldRoom) {
        int [] randDirs = generateRandDirs(WorldBuilder.r);

        for (int direction : randDirs) {
            if (!this.updatePosition(world, oldRoom, direction)) continue;
            if (!this.hasOverlapOrOutOfBounds(world)) return true;
        }

        return false;
    }

    // method that decides which update method to run depending on direction
    // returns true if position update is successful
    private boolean updatePosition(TETile[][] world, Room oldRoom, int direction) {
        switch(direction) {
            case 1:
                return this.updatePositionUp(world, oldRoom);
            case 2:
                return this.updatePositionRight(world, oldRoom);
            case 3:
                return this.updatePositionDown(oldRoom);
            case 4:
                return this.updatePositionLeft(oldRoom);
        }

        return false;
    }

    private boolean updatePositionUp(TETile[][] world, Room oldRoom) {
        // just in case we run into any weird ranges like [21, 21)
        try {
            this.pos.x = oldRoom.pos.x + randomRange(-2, 2);
            this.pos.y = randomRange(oldRoom.pos.y + oldRoom.len, world[0].length - 1);

            this.connectionPos.x = getRandomConnectionPosX();
            this.connectionPos.y = getRandomConnectionPosY();
        }

        catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    private boolean updatePositionRight(TETile[][] world, Room oldRoom) {
        // just in case we run into any weird ranges like [21, 21)
        try {
            this.pos.x = randomRange(oldRoom.pos.x + oldRoom.width, world.length - 1);
            this.pos.y = oldRoom.pos.y + randomRange(-2, 2);

            this.connectionPos.x = getRandomConnectionPosX();
            this.connectionPos.y = getRandomConnectionPosY();
        }
        catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    private boolean updatePositionDown(Room oldRoom) {
        // just so that we don't feed a zero or a negative value to our random number generator below
        try {
            this.pos.x = oldRoom.pos.x + randomRange(-2, 2);
            this.pos.y = randomRange(0, oldRoom.pos.y - this.len - 1);

            this.connectionPos.x = getRandomConnectionPosX();
            this.connectionPos.y = getRandomConnectionPosY();
        }
        catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    private boolean updatePositionLeft(Room oldRoom) {
        // just so that we don't feed a zero or a negative value to our random number generator below
        try {
            this.pos.x = randomRange(0, oldRoom.pos.x - this.width - 1);
            this.pos.y = oldRoom.pos.y + randomRange(-2, 2);

            this.connectionPos.x = getRandomConnectionPosX();
            this.connectionPos.y = getRandomConnectionPosY();
        }
        catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    // return an array in random order of ints 1 - 4 that correspond to directions
    public static int[] generateRandDirs(Random rand) {
        int[] dirs = new int[4];

        for (int i = 0; i < dirs.length; i++) {
            dirs[i] = i + 1;
        }

        RandomUtils.shuffle(rand, dirs);
        return dirs;
    }

    // returns a random integer in [lower, upper]
    private static int randomRange(int lower, int upper) throws IllegalArgumentException {
        return RandomUtils.uniform(WorldBuilder.r, lower, upper + 1);
    }

    // returns a randomly placed connection position for a given room
    private Position getRandomConnectionPos() {
        return new Position(randomRange(this.pos.x + 1, this.pos.x + width - 2),
                            randomRange(this.pos.y + 1, this.pos.y + len - 2));
    }

    // instead of returning a new Position object like the method above, this just edits the x value
    // this method is necessary because when we have a room in the stack and our newRoom is being branched,
    // since the room in the stack is representative of our newRoom, when the connection point of newRoom is being
    // updated the connection point of our room in the stack also needs to be updated
    private int getRandomConnectionPosX() {
        return randomRange(this.pos.x + 1, this.pos.x + width - 2);
    }

    // see above
    private int getRandomConnectionPosY() {
        return randomRange(this.pos.y + 1, this.pos.y + len - 2);
    }

    public Position getConnectionPos() {
        return this.connectionPos;
    }

    public Position getGoalDoorPos(TETile[][] world) throws IndexOutOfBoundsException {
        // check bottom and top row of room
        for (int x = this.pos.x + 1; x < (this.pos.x + this.width) - 1; x++) {
            //check bottom row
            if (world[x][this.pos.y].equals(Room.outerTile)) {
                return new Position(x, this.pos.y);
            }

            //check top row
            if (world[x][(this.pos.y + this.len) - 1].equals(Room.outerTile)) {
                return new Position(x, (this.pos.y + this.len) - 1);
            }
        }

        // check left and right column of room
        for (int y = this.pos.y + 1; y < (this.pos.y + this.len) - 1; y++) {
            //check left column
            if (world[this.pos.x][y].equals(Room.outerTile)) {
                return new Position(this.pos.x, y);
            }

            //check right column
            if (world[(this.pos.x + this.width) - 1][y].equals(Room.outerTile)) {
                return new Position((this.pos.x + this.width) - 1, y);
            }
        }

        throw new IndexOutOfBoundsException();
    }

    @Override
    public String toString() {
        String res = "";
        res += "Position x: " + this.pos.x + " y: " + this.pos.y;
        res += "\nConnection point x: " + this.connectionPos.x + " y: " + this.connectionPos.y;
        res += "\nWidth: " + this.width;
        res += "\nLength: " + this.len + "\n";
        return res;
    }
}
