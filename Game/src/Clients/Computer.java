package Clients;

import UDP.ClientUDPComputer;

public class Computer {
    public static void main(String[] args) {
        new ClientUDPComputer("127.0.0.1", 8122, new byte[1024]).clientMove();
    }

}
