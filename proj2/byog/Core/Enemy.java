package byog.Core;

import byog.TileEngine.Tileset;
import byog.TileEngine.TETile;

import java.io.Serializable;
import java.util.ArrayList;

public class Enemy extends Being implements Serializable {
    // static variable so we can easily check Enemy tile type without calling an enemy instance
    private static TETile tileStatic;

    public Enemy(Position pos) {
        setPos(pos);
        setTile(Tileset.SAND);
    }

    public static TETile returnEnemyTile() {
        return tileStatic;
    }

    public static ArrayList<Enemy> initializeEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        ArrayList<Room> roomsListCopy = WorldBuilder.getRoomsList();

        // we substract 1 from total num of rooms bc we don't want to add an enemy to the first room
        // which is where our player spawns
        // but we use i + 1 to index since when i = 0 we want to get 1, so we don't access the first room
        for (int i = 0; i < WorldBuilder.getRoomsList().size() - 1; i++) {
            enemies.add(new Enemy(roomsListCopy.get(i + 1)
                                               .getConnectionPos()));
        }

        return enemies;
    }

    public static void drawEnemies(TETile[][] world, ArrayList<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            enemy.drawBeing(world);
        }
    }

    public static void moveEnemies(TETile[][] world, ArrayList<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            enemy.moveEnemiesHelper(world);
        }
    }

    public static void deleteEnemyWithSamePositionAs(Position dartPos, ArrayList<Enemy> enemies) {
        for (int i = 0; i < enemies.size(); i++) {
            if (dartPos.equals(enemies.get(i).getPos())) {
                enemies.remove(i);
            }
        }
    }

    private void moveEnemiesHelper(TETile[][] world) {
        int[] randDirs = Room.generateRandDirs(WorldBuilder.r);
        boolean success = false;

        for (int dir : randDirs) {
            switch (dir) {
                case 1:
                    if (this.moveUp(world)) {
                        success = true;
                    }
                    break;
                case 2:
                    if (this.moveRight(world)) {
                        success = true;
                    }
                    break;
                case 3:
                    if (this.moveDown(world)) {
                        success = true;
                    }
                    break;
                case 4:
                    if (this.moveLeft(world)) {
                        success = true;
                    }
                    break;
            }

            if (success) break;
        }
    }

    @Override
    protected void setTile(TETile tile) {
        super.setTile(tile);
        tileStatic = tile; 
    }
}
