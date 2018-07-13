package byog.Core;

import edu.princeton.cs.introcs.StdDraw;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Dart {
    private static Position dartPos;
    private static TETile tile;

    public static void shootUp(TETile[][] world, Position origin) {
        shootHelper(world, origin, 1);
    }

    public static void shootRight(TETile[][] world, Position origin) {
        shootHelper(world, origin, 2);
    }

    public static void shootDown(TETile[][] world, Position origin) {
        shootHelper(world, origin, 3);
    }

    public static void shootLeft(TETile[][] world, Position origin) {
        shootHelper(world, origin, 4);
    }

    private static void shootHelper(TETile[][] world, Position origin, int dir) {
        dartPos = new Position(origin);
        updateDartPos(dir, dartPos);
        setDartTile(dir);

        while (world[dartPos.x][dartPos.y].equals(Room.innerTile)) {
            world[dartPos.x][dartPos.y] = tile; //draw dart
            Game.ter.renderFrame(world);
            StdDraw.pause(50); //animation interval

            world[dartPos.x][dartPos.y] = Room.innerTile; //redraw standard walkable tile back
            updateDartPos(dir, dartPos); //update dart in path of trajectory
        }

        Game.ter.renderFrame(world);
    }

    private static void updateDartPos(int dir, Position dart) {
        switch (dir) {
            case 1: // up
                dart.y += 1;
                break;
            case 2: // right
                dart.x += 1;
                break;
            case 3: // down
                dart.y -= 1;
                break;
            case 4: // left
                dart.x -= 1;
                break;
        }
    }

    private static void setDartTile(int dir) {
        switch (dir) {
            case 1:
            case 3:
                tile = Tileset.VERTICAL_DART;
                break;
            case 2:
            case 4:
                tile = Tileset.HORIZONTAL_DART;
                break;
        }
    }
}
