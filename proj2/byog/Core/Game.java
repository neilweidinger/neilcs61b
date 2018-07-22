package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Queue;
import java.io.Serializable;

public class Game implements Serializable {
    public static TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    public static final int GUI_HEIGHT = 2;

    // booleans to break out of loops, so that we don't keep listening to keyboard inputs when we don't want
    private boolean mainMenu;
    private boolean playingGame;
    private boolean playerWon;
    private int nourishment = 100;
    private int lives = 3;
    private int darts = 50;
    private Player player;
    private ArrayList<Enemy> enemies;
    private Door goalDoor;
    private TETile[][] world; //static so we can render using static method (called from outside of game class)

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT + GUI_HEIGHT); // initialize renderer
        world = WorldBuilder.initializeWorld(WIDTH, HEIGHT);

        // draw main menu - THIS ONLY DISPLAYS THE MENU, DOESN'T REALLY DO ANYTHING
        drawMainMenu();

        // set booleans to control loops
        mainMenu = true;
        playingGame = false;

        mainMenuLoop();
        playingGameLoop();
    }

    private void mainMenuLoop() {
        while (mainMenu) {
            mainMenuListener();
        }
    }

    private void playingGameLoop() {
        while (playingGame) {
            drawGUI();
            playingGameListener();

            if (gameOver()) {
                break;
            }

            if (gameWon()) {
                break;
            }
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        ter.initialize(WIDTH, HEIGHT + GUI_HEIGHT); // initialize renderer
        world = WorldBuilder.initializeWorld(WIDTH, HEIGHT);
        Queue<Character> inputQueue = stringInitializeQueue(input);

        stringMainMenu(inputQueue);
        stringPlayingGame(inputQueue);

        return world;
    }

    public void playFromLoadedGame() {
        // clear and reset everything, including user inputted keys if any
        StdDraw.clear(Color.RED);
        clearUserInput();
        resetFont();

        ter.renderFrame(world);

        playingGameLoop();
    }

    public TETile[][] stringPlayFromLoadedGame(Queue<Character> inputQueue) {
        // just in case user only enetered 'l' (render would never be called again)
        ter.renderFrame(world);
        stringPlayingGame(inputQueue);
        return world;
    }

    private TETile[][] stringMainMenu(Queue<Character> inputQueue) {
        Character action = inputQueue.poll();
        if (action == null) action = '0'; // make sure null action doesn't do anything

        switch (action) {
            case 'l':
            case 'L':
                world = Load.stringLoadGame(inputQueue);
                mainMenu = false;
                break;
            case 'n':
            case 'N':
                WorldBuilder.setSeed(stringAskForSeed(inputQueue));
                WorldBuilder.generateWorld(world);
                spawnBeingsAndDoor();

                // reset font so that tiles are correct size before rendering world
                resetFont();

                // render world
                ter.renderFrame(world);

                break;
            default:
                mainMenuListenerHelper(action); // the same regardless if playByString or not
                break;
        }

        return world;
    }

    // listen for user input while on main menu
    private void mainMenuListener() {
        if (StdDraw.hasNextKeyTyped()) {
            mainMenuListenerHelper(StdDraw.nextKeyTyped());
        }
    }

    private void mainMenuListenerHelper(char action) {
        switch (action) {
            case 'q': // switch case fall through - 'q' has same effect as 'Q'
            case 'Q':
                mainMenu = false;
                StdDraw.clear(Color.BLACK);
                StdDraw.show();
                break;
            case 'l':
            case 'L':
                Load.loadGame();
                mainMenu = false;
                break;
            case 'n':
            case 'N':
                mainMenu = false;
                playingGame = true;

                WorldBuilder.setSeed(askForSeed());
                WorldBuilder.generateWorld(world);
                spawnBeingsAndDoor();

                // reset font so that tiles are correct size before rendering world
                resetFont();

                // render world
                ter.renderFrame(world);

                break;
            default:
                break;
        }
    }

    // this method is needed because if we just used playingGameListenerHelper, if the user entered ':' then it would
    // be weird and annoying to poll the secondAction (bc user called option menu) within playingGameListenerHelper
    // which is also used in playByKeyboard
    private void stringPlayingGame(Queue<Character> inputQueue) {
        while (!inputQueue.isEmpty()) {
            Character action = inputQueue.poll();
            if (action == null) return;

            switch (action) {
                case ':':
                    optionsListenerHelper(inputQueue.poll());
                    break;
                default:
                    playingGameListenerHelper(action);
                    break;
            }
        }
    }

    // listen for user input while playing game
    private void playingGameListener() {
        // if user typed in something, execute appropriate action otherwise just continue on
        if (StdDraw.hasNextKeyTyped()) {
            playingGameListenerHelper(StdDraw.nextKeyTyped());
        }
    }

    private void playingGameListenerHelper(char action) {
        switch (action) {
            case ':':
                optionsListener();
                break;
            case 'e':
            case 'E':
                if (player.isTouchingDoor(goalDoor.getPos())) {
                    playerWon = true;
                }
                break;
            case 'w':
            case 'W':
                if (player.moveUp(world)) {
                    checkForEnemyCollision();
                }
                break;
            case 'd':
            case 'D':
                if (player.moveRight(world)) {
                    checkForEnemyCollision();
                }
                break;
            case 's':
            case 'S':
                if (player.moveDown(world)) {
                    checkForEnemyCollision();
                }
                break;
            case 'a':
            case 'A':
                if (player.moveLeft(world)) {
                    checkForEnemyCollision();
                }
                break;
            case 'i':
            case 'I':
                if (darts <= 0) break;

                Dart.shootUp(world, enemies, player.getPos());
                clearUserInput();
                darts--;

                break;
            case 'l':
            case 'L':
                if (darts <= 0) break;

                Dart.shootRight(world, enemies, player.getPos());
                clearUserInput();
                darts--;

                break;
            case 'k':
            case 'K':
                if (darts <= 0) break;

                Dart.shootDown(world, enemies, player.getPos());
                clearUserInput();
                darts--;

                break;
            case 'j':
            case 'J':
                if (darts <= 0) break;

                Dart.shootLeft(world, enemies, player.getPos());
                clearUserInput();
                darts--;

                break;
        }
    }

    private void optionsListener() {
        drawGUI("OPTIONS: [q] to quit and save-Press any other key to cancel");

        while (!StdDraw.hasNextKeyTyped()) {
            // wait for second action
        }

        optionsListenerHelper(StdDraw.nextKeyTyped());
    }

    private void optionsListenerHelper(char secondAction) {
        switch (secondAction) {
            case 'q':
            case 'Q':
                Load.saveGame(this);
                playingGame = false;
                StdDraw.clear(Color.BLACK);
                StdDraw.show();
                break;
            default:
                break;
        }
    }

    private long stringAskForSeed(Queue<Character> inputQueue) {
        ArrayList<Character> seed = new ArrayList<>();

        Character digit = inputQueue.poll();

        while (digit != null && digit != 's' && digit != 'S') {
            seed.add(digit);
            digit = inputQueue.poll();
        }

        return Long.valueOf(arrayListToString(seed));
    }

    // returns user inputted seed as a long, sanitized so that seed is valid
    private long askForSeed() {
        StdDraw.clear(Color.BLACK);

        long userSeed;
        boolean error = false;

        // loop to avoid invalid numbers
        while (true) {
            try {
                userSeed = Long.parseLong(askForSeedHelper(error));
                break;
            }
            catch (NumberFormatException e) {
                error = true;
            }
        }

        return userSeed;
    }

    // returns user inputted seed as a String, does not check if seed is valid or not
    private String askForSeedHelper(boolean error) {
        drawBigText("ENTER SEED");
        drawSmallText("Press S to finish", 2, Color.WHITE);

        if (error) {
            drawSmallText("ENTER VALID NUMBER", 0, Color.RED);
        }

        StringBuilder builder = new StringBuilder();

        askForSeedListener(builder);

        return builder.toString();
    }

    // listens for user inputted seed
    private void askForSeedListener(StringBuilder builder) {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();

                if (c == 'S' || c == 's') {
                    break;
                }

                if (c <= '9' && c >= '0') {
                    builder.append(c);

                    // really hacky way of making sure we clear the seed area but not the whole screen
                    StdDraw.setPenColor(Color.BLACK);
                    StdDraw.filledRectangle(WIDTH / 2, (HEIGHT / 2) + 5, WIDTH / 2, 2);
                    StdDraw.setPenColor(Color.WHITE);

                    drawSmallText(builder.toString(), -3, Color.WHITE);
                }
            }
        }
    }

    // draws gui while playing game
    private void drawGUI() {
        clearGUI();

        // display gui
        if (StdDraw.mouseY() >= HEIGHT) {
            StdDraw.textLeft(0, HEIGHT + (GUI_HEIGHT / 2), "GUI");
        }
        else {
            int x = (int) StdDraw.mouseX();
            int y = (int) StdDraw.mouseY();

            StdDraw.textLeft(0, HEIGHT + (GUI_HEIGHT / 2), world[x][y].description());
        }
        
        String nourishmentAndLivesStats = "Darts: " + String.valueOf(darts) +
                                          " --- Lives: " + String.valueOf(lives) +
                                          " --- Nourishment: " + String.valueOf(nourishment);
        StdDraw.textRight(WIDTH, HEIGHT + (GUI_HEIGHT / 2), nourishmentAndLivesStats);

        StdDraw.show();
    }

    // overloaded method to display custom message on GUI
    private void drawGUI(String message) {
        clearGUI();

        StdDraw.setPenColor(Color.GREEN);
        StdDraw.textLeft(0, HEIGHT + (GUI_HEIGHT / 2), message);
        StdDraw.show();
    }

    // really hacky way of making sure we clear the gui area but not the whole screen
    // by default sets pen color to white
    private void clearGUI() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(WIDTH / 2, HEIGHT + (GUI_HEIGHT / 2), WIDTH / 2, (GUI_HEIGHT / 2));
        StdDraw.setPenColor(Color.WHITE);
    }

    private void drawMainMenu() {
        drawBigText("CS61B: THE GAME");
        drawSmallText("New Game (N)", 1, Color.CYAN);
        drawSmallText("Load Game (L)", 2, Color.CYAN);
        drawSmallText("Quit (Q)", 3, Color.CYAN);
    }

    // draws big text in center of screen
    private void drawBigText(String output) {
        int midWidth = WIDTH / 2;

        StdDraw.clear(Color.BLACK); 

        StdDraw.setPenColor(Color.WHITE); //set pen color to white
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 40)); //set big font
        StdDraw.text(midWidth, (HEIGHT / 100.0) * 80, output);

        StdDraw.show();
    }

    // DOES NOT CLEAR SCREEN, intended to be called after drawBigText()
    // line means how many lines down from center of screen, so use negative number to go up
    private void drawSmallText(String output, int line, Color color) {
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;

        StdDraw.setPenColor(color);
        StdDraw.setFont(new Font("Monaco", Font.PLAIN, 20)); //set small font
        StdDraw.text(midWidth, midHeight - (1.5 * line), output);

        StdDraw.show();
    }

    // spawns and draws Player and enemies
    private void spawnBeingsAndDoor() {
        player = new Player();
        player.drawBeing(world);

        enemies = Enemy.initializeEnemies();
        Enemy.drawEnemies(world, enemies);

        goalDoor = new Door(world);
        goalDoor.drawDoor(world);

}

    private void checkForEnemyCollision() {
        // after player moves, before enemies move
        if (Being.isTouchingEnemy(world, player)) {
            adjustNourishmentAndLives();
            return;
        }

        Enemy.moveEnemies(world, enemies);

        // after player moves and after enemies move
        if (Being.isTouchingEnemy(world, player)) {
            adjustNourishmentAndLives();
        }

        ter.renderFrame(world);
    }

    private void adjustNourishmentAndLives() {
        nourishment -= 20;

        if (nourishment == 0) {
            lives--;
            nourishment = 100;
        }
    }

    private void clearUserInput() {
        while (StdDraw.hasNextKeyTyped()) {
            // make sure keys entered while shooting dart are not registered
            StdDraw.nextKeyTyped();
        }
    }

    private boolean gameOver() {
        if (lives == 0) {
            drawGUI("GAME OVER");
            StdDraw.pause(1000);
            playingGame = false;
            drawBigText("GAME OVER");
            StdDraw.show();
            return true;
        }

        return false;
    }

    private boolean gameWon() {
        if (playerWon) {
            drawGUI("YOU WON!!!");
            StdDraw.pause(1000);
            playingGame = false;
            drawBigText("YOU WON!!!");
            StdDraw.show();
            return true;
        }

        return false;
    }

    private String arrayListToString(ArrayList<Character> list) {
        StringBuilder builder = new StringBuilder();

        for (Character item : list) {
            builder.append(String.valueOf(item));
        }

        return builder.toString();
    }

    private Queue<Character> stringInitializeQueue(String input) {
        Queue<Character> inputQueue = new ArrayDeque<>();

        // fill in queue
        for (char c : input.toCharArray()) {
            inputQueue.offer(c);
        }

        return inputQueue;
    }

    private void resetFont() {
        StdDraw.setFont(new Font("Monaco", Font.PLAIN, 16));
    }
}
