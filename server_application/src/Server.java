
import game_logic.Game;
import socket_executors.TcpSocketExecutor;
import socket_executors.UdpSocketExecutor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private int port;
    private String address;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public Server(int port, String address) {
        this.port = port;
        this.address = address;
    }

    public void start() {
        System.out.println("Server started!!!");
        while (true) {
            try (ServerSocket server = new ServerSocket(port);
                 UdpSocketExecutor udpSocketExecutor = new UdpSocketExecutor(address);
                 TcpSocketExecutor tcpSocketExecutor = new TcpSocketExecutor(server)){
                Game game = new Game();
                while (true) {
                    game.chooseMode(udpSocketExecutor, tcpSocketExecutor, port);
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
