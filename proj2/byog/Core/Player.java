package byog.Core;

import byog.TileEngine.Tileset;

public class Player extends Being {
    public Player() {
        setPos(WorldBuilder.playerStart);
        setTile(Tileset.PLAYER);
    }
}
