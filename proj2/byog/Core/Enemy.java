package byog.Core;

import byog.TileEngine.Tileset;
import byog.TileEngine.TETile;

public class Enemy extends Being {
    // static variable so we can easily check Enemy tile type without calling an enemy instance
    private static TETile tileStatic;

    public Enemy(Position pos) {
        setPos(pos);
        setTile(Tileset.SAND);
    }

    public static TETile returnEnemyTile() {
        return tileStatic;
    }

    public static Enemy[] initializeEnemies() {
        // we substract 1 from total num of rooms bc we don't want to add an enemy to the first room
        // which is where our player spawns
        Enemy[] enemies = new Enemy[WorldBuilder.roomsList.size() - 1];

        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new Enemy(WorldBuilder.roomsList.get(i + 1).returnConnectionPos());
        }

        return enemies;
    }

    public static void drawEnemies(TETile[][] world, Enemy[] enemies) {
        for (Enemy enemy : enemies) {
            enemy.drawBeing(world);
        }
    }

    public static void moveEnemies(TETile[][] world, Enemy[] enemies) {
        for (Enemy enemy : enemies) {
            enemy.moveEnemiesHelper(world);
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
