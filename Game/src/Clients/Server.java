package Clients;

import UDP.ServerUDP;

public class Server {
    public static void main(String[] args) {
        new ServerUDP(8122, new byte[1024]).start();
    }
}
