package byog.Core;

import byog.TileEngine.TETile;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Queue;

public class Load {
    public static void saveGame(Game game) {
        try {
            FileOutputStream fos = new FileOutputStream("byog/Core/savedGame.ser");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(game);
        }
        catch (IOException e) {
            System.out.println("WRITE ERROR");
            System.out.println(e.getMessage());
        }
    }

    public static void loadGame() {
        loadGameHelper().playFromLoadedGame();
    }

    public static TETile[][] stringLoadGame(Queue<Character> inputQueue) {
        return loadGameHelper().stringPlayFromLoadedGame(inputQueue);
    }

    // returns a Game object that has been loaded in from a save file
    private static Game loadGameHelper() {
        Game loadedGame = null;

        try {
            FileInputStream fos = new FileInputStream("byog/Core/savedGame.ser");
            ObjectInputStream in = new ObjectInputStream(fos);
            loadedGame = (Game) in.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("READ ERROR");
            System.out.println(e.getMessage());
        }

        return loadedGame;
    }
}
