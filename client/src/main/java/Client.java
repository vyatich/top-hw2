import game_logic.Game;
import socket_executors.TcpSocketExecutor;
import socket_executors.UdpSocketExecutor;

import java.io.IOException;

public class Client implements Runnable {

    private final int PORT = 8080;
    private final String ADDRESS = "localhost";
//    private final TcpSocketExecutor tcpSocketExecutor = new TcpSocketExecutor(ADDRESS, PORT);
//    private final UdpSocketExecutor udpSocketExecutor = new UdpSocketExecutor(PORT, ADDRESS);

    public void run() {
        try(Game game = new Game(PORT, ADDRESS)) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
