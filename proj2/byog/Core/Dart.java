package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import edu.princeton.cs.introcs.StdDraw;

import java.io.Serializable;
import java.util.ArrayList;

public class Dart implements Serializable {
    private static Position dartPos;
    private static TETile tile;

    public static void shootUp(TETile[][] world, ArrayList<Enemy> enemies, Position origin) {
        shootHelper(world, enemies, origin, 1);
    }

    public static void shootRight(TETile[][] world, ArrayList<Enemy> enemies, Position origin) {
        shootHelper(world, enemies, origin, 2);
    }

    public static void shootDown(TETile[][] world, ArrayList<Enemy> enemies, Position origin) {
        shootHelper(world, enemies, origin, 3);
    }

    public static void shootLeft(TETile[][] world, ArrayList<Enemy> enemies, Position origin) {
        shootHelper(world, enemies, origin, 4);
    }

    private static void shootHelper(TETile[][] world, ArrayList<Enemy> enemies, Position origin, int dir) {
        dartPos = new Position(origin);
        updateDartPos(dir, dartPos);
        setDartTile(dir);

        while (world[dartPos.x][dartPos.y].equals(Room.innerTile)) {
            world[dartPos.x][dartPos.y] = tile; //draw dart
            Game.ter.renderFrame(world);
            StdDraw.pause(50); //animation interval

            world[dartPos.x][dartPos.y] = Room.innerTile; //redraws Room innerTile aka erases dart
            updateDartPos(dir, dartPos); //update dart in path of trajectory
        }

        if (Dart.hitEnemy(world, dartPos)) {
            world[dartPos.x][dartPos.y] = Room.innerTile; //redraws Room innerTile
            Enemy.deleteEnemyWithSamePositionAs(dartPos, enemies); //delete enemy
        }

        Game.ter.renderFrame(world);
    }

    private static boolean hitEnemy(TETile[][] world, Position dartPos) {
        return world[dartPos.x][dartPos.y].equals(Enemy.returnEnemyTile());
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
