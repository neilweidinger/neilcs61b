package byog.Core;

import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Player extends Being implements Serializable {
    public Player() {
        setPos(WorldBuilder.getPlayerStart());
        setTile(Tileset.PLAYER);
    }
}
