package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;
import java.util.ArrayDeque; //use this for our stack

public class Maze {
    public static void generateMaze(TETile[][] world, int seed) {
        Random r = new Random(seed);
        ArrayDeque<Position> stack = new ArrayDeque<>();

        int x = RandomUtils.uniform(r, world.length);
        int y = RandomUtils.uniform(r, world[0].length);

        Position cellPos = new Position(x, y);
        Position oldCellPos = new Position(cellPos);

        drawCell(world, cellPos);
        stack.push(new Position(cellPos));

        while (!stack.isEmpty()) {
            if (availableNeighbor(world, cellPos, r)) {
                drawCell(world, oldCellPos, cellPos);
                oldCellPos = new Position(cellPos);
                stack.push(new Position(cellPos));
            }
            else {
                cellPos = stack.pop();
                oldCellPos = new Position(cellPos);
            }
        }
    }

    public static boolean availableNeighbor(TETile[][] world, Position pos, Random rand) {
        int [] randDirs = generateRandDirs(rand);

        for (int i = 0; i < randDirs.length; i++) {
            switch(randDirs[i]) {
                case 1: //up
                    // out of bounds of world 2d array
                    if (pos.y + 2 >= world[0].length) {
                        break;
                    }

                    // if tile two tiles above is equal to nothing aka available
                    if (world[pos.x][pos.y + 2].equals(Tileset.NOTHING)) {
                        pos.y += 2;
                        return true;
                    }

                    break;
                case 2: //right
                    // out of bounds of world 2d array
                    if (pos.x + 2 >= world.length) {
                        break;
                    }

                    // if tile two tiles to the right is equal to nothing aka available
                    if (world[pos.x + 2][pos.y].equals(Tileset.NOTHING)) {
                        pos.x += 2;
                        return true;
                    }

                    break;
                case 3: //down
                    // out of bounds of world 2d array
                    if (pos.y - 2 < 0) {
                        break;
                    }

                    // if tile two tiles below is equal to nothing aka available
                    if (world[pos.x][pos.y - 2].equals(Tileset.NOTHING)) {
                        pos.y -= 2;
                        return true;
                    }

                    break;
                case 4: //left
                    // out of bounds of world 2d array
                    if (pos.x - 2 < 0) {
                        break;
                    }

                    // if tile two tiles to the left is equal to nothing aka available
                    if (world[pos.x - 2][pos.y].equals(Tileset.NOTHING)) {
                        pos.x -= 2;
                        return true;
                    }

                    break;
                default:
                    return false;
            }
        }

        return false;
    }

    public static int[] generateRandDirs(Random rand) {
        int[] res = new int[4];

        for (int i = 0; i < res.length; i++) {
            res[i] = i + 1;
        }

        RandomUtils.shuffle(rand, res);
        return res;
    }

    public static void drawCell(TETile[][] world, Position pos) {
        if (pos.x < world.length && pos.x >= 0 && pos.y < world[0].length && pos.y >= 0) {
            world[pos.x][pos.y] = Tileset.FLOWER;
        }
    }

    public static void drawCell(TETile[][] world, Position oldPos, Position newPos) {
        if (newPos.x < world.length && newPos.x >= 0 && newPos.y < world[0].length && newPos.y >= 0) {
            world[avgX(oldPos, newPos)][avgY(oldPos, newPos)] = Tileset.FLOWER;
            world[newPos.x][newPos.y] = Tileset.FLOWER;
        }
    }

    public static int avgX(Position oldPos, Position newPos) {
        return (oldPos.x + newPos.x) / 2;
    }

    public static int avgY(Position oldPos, Position newPos) {
        return (oldPos.y + newPos.y) / 2;
    }
}
