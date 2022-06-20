package Clients;

import UDP.ClientUDPMan;

public class Man {
    public static void main(String[] args) {
        new ClientUDPMan("127.0.0.1", 8122, new byte[1024]).clientMove();
    }
}