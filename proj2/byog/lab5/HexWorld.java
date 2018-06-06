package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 768879;
    private static final Random RANDOM = new Random(SEED);

    public static class Position {
        public int x;
        public int y;

        public Position() {
            this.x = 0;
            this.y = 0;
        }

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // fill in world with a single type of tile
    public static void initializeWorld(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    // draw a line in world array
    private static void drawHexLine(TETile[][] tiles, TETile tile, int length, Position linePos) {
        for (int xPos = linePos.x; xPos < linePos.x + length; xPos++) {
            if (xPos < tiles.length && xPos >= 0 && linePos.y < tiles[0].length && linePos.y >= 0) {
                tiles[xPos][linePos.y] = tile;
            }
        }
    }

    // draw the two middle rows of a hexagon
    private static void drawHexMiddle(TETile[][] tiles, TETile tile, int sides, Position hexPos) {
        int len = sides + (2 * (sides - 1));
        Position hexMiddlePos = new Position(hexPos.x, hexPos.y + (sides - 1)); //specifies lower left corner

        for (int i = 0; i < 2; i++) {
            drawHexLine(tiles, tile, len, hexMiddlePos); //draw one row of our two middle hexagon rows
            hexMiddlePos.y += 1; //bc every position is specified by lower left corner, we must increment upwards
        }
    }

    // recursive helper method for drawHexLower
    private static int drawHexLowerHelper(TETile[][] tiles, TETile tile, int sides, int len, Position linePos) {
        if (len < sides) {
            return len;
        }

        drawHexLine(tiles, tile, len, linePos);
        Position newLinePos = new Position(linePos.x + 1, linePos.y - 1); //increment new position down and to the right
        return drawHexLowerHelper(tiles, tile, sides, len - 2, newLinePos);
    }

    // draw the lower part of a hexagon
    private static void drawHexLower(TETile[][] tiles, TETile tile, int sides, Position hexPos) {
        //position of the uppermost row of the lower part of the hexagon (we increment this down and to the right)
        Position linePos = new Position(hexPos.x + 1, hexPos.y + (sides - 2));
        //length of uppermost row of the lower part of the hexagon (this gets decremented by two)
        int len = sides + (2 * (sides - 2));
        //call recursive helper method
        drawHexLowerHelper(tiles, tile, sides, len, linePos);
    }

    // recursive helper method for drawHexUpper
    private static int drawHexUpperHelper(TETile[][] tiles, TETile tile, int sides, int len, Position linePos) {
        if (len < sides) {
            return len;
        }

        drawHexLine(tiles, tile, len, linePos);
        Position newLinePos = new Position(linePos.x + 1, linePos.y + 1); //increment new position up and to the right
        return drawHexUpperHelper(tiles, tile, sides, len - 2, newLinePos);
    }

    // draw the upper part of a hexagon
    private static void drawHexUpper(TETile[][] tiles, TETile tile, int sides, Position hexPos) {
        //position of the uppermost row of the upper part of the hexagon (we increment this down and to the right)
        Position linePos = new Position(hexPos.x + 1, hexPos.y + (sides + 1));
        //length of uppermost row of the upper part of the hexagon (this gets decremented by two)
        int len = sides + (2 * (sides - 2));
        //call recursive helper method
        drawHexUpperHelper(tiles, tile, sides, len, linePos);
    }

    // draws a hexagon
    public static void addHexagon(TETile[][] tiles, TETile tile, int sides, Position hexPos) {
        drawHexMiddle(tiles, tile, sides, hexPos);
        drawHexLower(tiles, tile, sides, hexPos);
        drawHexUpper(tiles, tile, sides, hexPos);
    }

    // draws a random hexagon
    public static void addRandomHexagon(TETile[][] tiles, int sides, Position hexPos) {
        TETile tile = getRandomBiome();
        drawHexMiddle(tiles, tile, sides, hexPos);
        drawHexLower(tiles, tile, sides, hexPos);
        drawHexUpper(tiles, tile, sides, hexPos);
    }

    // get x offset when creating a tessellation using number of hexagon columns and hexagon side length
    private static int getTessXoffset(int numOfHexColumns, int hexSides) {
        return numOfHexColumns * (hexSides + (hexSides - 1));
    }

    // get y offset when creating a tessellation using number of hexagons and hexagon side length
    private static int getTessYoffset(int numOfHexagons, int hexSides) {
        return numOfHexagons * (hexSides * 2);
    }

    // draw a column of hexagons NOTE: colPos parameter means bottom left of column that method will draw
    private static void drawHexColumn(TETile[][] tiles, TETile tile, int colHeight, int hexSides, Position colPos) {
        Position currHexPos = new Position(colPos.x, colPos.y);

        //draw hexagons starting from the bottom
        for (int i = 0; i < colHeight; i++) {
            // addHexagon(tiles, tile, hexSides, currHexPos);
            addRandomHexagon(tiles, hexSides, currHexPos);
            currHexPos.y += getTessYoffset(1, hexSides); //draw our next hexagon exactly one hexagon height above
        }
    }

    // draws the middle column of a hexagon tessellation
    private static void drawTessMiddle(TETile[][] tiles, TETile tile, int tessSides, int hexSides, Position tessPos) {
        int colHeight = tessSides + (tessSides - 1);
        int firstHexPosX = tessPos.x + getTessXoffset(tessSides - 1, hexSides); //find the appropriate x pos for our first hex
        int firstHexPosY = tessPos.y; //we start drawing hexagons from the bottom and work our way up
        Position currHexPos = new Position(firstHexPosX, firstHexPosY);

        drawHexColumn(tiles, tile, colHeight, hexSides, currHexPos); //draw column of hexagons starting from bottom
    }

    // recursive helper method to draw left side of a hexagon tessellation
    private static int drawTessLeftHelper(TETile[][] tiles, TETile tile, int tessSides,
                                          int hexSides, Position colPos, int colHeight) {

        // if column we are drawing is greater than middle column return from recursive method
        if (colHeight >= tessSides + (tessSides - 1)) {
            return colHeight;
        }

        drawHexColumn(tiles, tile, colHeight, hexSides, colPos);
        Position newColPos = new Position(colPos.x + getTessXoffset(1, hexSides),
                                          colPos.y - (getTessYoffset(1, hexSides) / 2));

        return drawTessLeftHelper(tiles, tile, tessSides, hexSides, newColPos, colHeight + 1);
    }

    // draws the left side of a hexagon tessellation
    private static void drawTessLeft(TETile[][] tiles, TETile tile, int tessSides, int hexSides, Position tessPos) {
        int firstXpos = tessPos.x;
        int firstYpos = tessPos.y + getTessYoffset(tessSides - 1, hexSides) / 2;
        Position firstColPos = new Position(firstXpos, firstYpos);

        drawTessLeftHelper(tiles, tile, tessSides, hexSides, firstColPos, tessSides);
    }

    // recursive helper method to draw right side of a hexagon tessellation
    private static int drawTessRightHelper(TETile[][] tiles, TETile tile, int tessSides,
                                          int hexSides, Position colPos, int colHeight) {

        // if column we are drawing is greater than middle column return from recursive method
        if (colHeight >= tessSides + (tessSides - 1)) {
            return colHeight;
        }

        drawHexColumn(tiles, tile, colHeight, hexSides, colPos);
        Position newColPos = new Position(colPos.x - getTessXoffset(1, hexSides),
                                          colPos.y - (getTessYoffset(1, hexSides) / 2));

        return drawTessRightHelper(tiles, tile, tessSides, hexSides, newColPos, colHeight + 1);
    }

    // draws the left side of a hexagon tessellation
    private static void drawTessRight(TETile[][] tiles, TETile tile, int tessSides, int hexSides, Position tessPos) {
        int firstXpos = tessPos.x + getTessXoffset(tessSides * 2 - 2, hexSides);
        int firstYpos = tessPos.y + getTessYoffset(tessSides - 1, hexSides) / 2;
        Position firstColPos = new Position(firstXpos, firstYpos);

        drawTessRightHelper(tiles, tile, tessSides, hexSides, firstColPos, tessSides);
    }

    // draws a hexagon tessellation
    public static void tesselateHexagon(TETile[][] tiles, TETile tile, int tessSides, int hexSides, Position tessPos) {
        drawTessMiddle(tiles, tile, tessSides, hexSides, tessPos);
        drawTessLeft(tiles, tile, tessSides, hexSides, tessPos);
        drawTessRight(tiles, tile, tessSides, hexSides, tessPos);
    }

    // returns a random TETile (except for water, water doesn't show up well on NOTHING/black background)
    public static TETile getRandomBiome() {
        int tileNum = RANDOM.nextInt(7);
        switch(tileNum) {
            case 0: return Tileset.TREE;
            case 1: return Tileset.WALL;
            case 2: return Tileset.FLOOR;
            case 3: return Tileset.GRASS;
            case 4: return Tileset.FLOWER;
            case 5: return Tileset.SAND;
            case 6: return Tileset.MOUNTAIN;
            default: return Tileset.TREE;
        }
    }

    public static void main(String [] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);

        // addHexagon(world, Tileset.FLOWER, 3, new Position(0, 0)); //position specifies lower left corner
        // addHexagon(world, Tileset.FLOWER, 4, new Position(9, 0));
        // addHexagon(world, Tileset.FLOWER, 5, new Position(21, 0));
        tesselateHexagon(world, Tileset.TREE, 3, 3, new Position(10, 10));

        ter.renderFrame(world);
    }
}
