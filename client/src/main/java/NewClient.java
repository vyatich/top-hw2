import socket_executors.TcpSocketExecutor;
import socket_executors.UdpSocketExecutor;

import java.io.IOException;
import java.util.Scanner;

public class NewClient {
    private static final int PORT = 8080;
    private static final String ADDRESS = "localhost";

    public static void main(String[] args) throws IOException {
        try (TcpSocketExecutor tcpSocketExecutor = new TcpSocketExecutor(ADDRESS, PORT);
             UdpSocketExecutor udpSocketExecutor = new UdpSocketExecutor(PORT, ADDRESS);
             Scanner scanner = new Scanner(System.in)) {
            while (true) {
                udpSocketExecutor.readMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
