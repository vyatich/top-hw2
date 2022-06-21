import game_logic.Game;
import socket_executors.TcpSocketExecutor;
import socket_executors.UdpSocketExecutor;

import java.io.IOException;
import java.util.Scanner;

public class Client implements Runnable {
    private int port;
    private String address;

    public Client(int port, String address) {
        this.port = port;
        this.address = address;
    }

    @Override
    public void run() {
        try (UdpSocketExecutor udpSocketExecutor = new UdpSocketExecutor(port, address);
             TcpSocketExecutor tcpSocketExecutor = new TcpSocketExecutor(address, port);
             Scanner scanner = new Scanner(System.in)) {
            Game game = new Game();
            game.clientChooseMode(tcpSocketExecutor, udpSocketExecutor, scanner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
