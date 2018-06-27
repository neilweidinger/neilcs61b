package byog.Core;

import byog.TileEngine.Tileset;
import byog.TileEngine.TETile;

public class Enemy extends Being {
    public Enemy(Position pos) {
        setPos(pos);
        setTile(Tileset.SAND);
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
}
