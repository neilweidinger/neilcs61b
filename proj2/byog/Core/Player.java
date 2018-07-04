package byog.Core;

import byog.TileEngine.Tileset;

public class Player extends Being {
    public Player() {
        setPos(WorldBuilder.getPlayerStart());
        setTile(Tileset.PLAYER);
    }
}
