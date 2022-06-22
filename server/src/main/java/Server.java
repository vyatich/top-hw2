
import rock_paper_scissors.game_logic.Game;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private static final int PORT = 8080;
    private static final String ADDRESS = "localhost";

    public static void main(String[] args) {
        System.out.println("Server started!!!");
        while(true) {
            try (Game game = new Game(PORT, ADDRESS)) {
                game.chooseMode();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
