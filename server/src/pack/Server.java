package pack;

import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 8081;

    public void startServer() {
        String resClient_1 = "";
        String resClient_2 = "";
        String inputClient_1;
        String inputClient_2;

        try (ServerSocket serverSocket = new ServerSocket(Server.PORT)) {
            System.out.println("\nСервер запущен на порту " + serverSocket.getLocalPort() + " ...");
            while (!serverSocket.isClosed()) {
                Socket client_1 = serverSocket.accept();
                if (client_1.isConnected()) {
                    System.out.println("\nPlayer 1 (" + client_1.getRemoteSocketAddress() + ") присоединился ... ждем Player 2 ...");
                }
                DataOutputStream outClient_1 = new DataOutputStream(client_1.getOutputStream());
                BufferedReader inClient_1 = new BufferedReader(new InputStreamReader(client_1.getInputStream()));

                Socket client_2 = serverSocket.accept();
                if (client_2.isConnected()) {
                    System.out.println("Player 2 (" + client_2.getRemoteSocketAddress() + ") присоединился ... игра ...");
                }
                DataOutputStream outClient_2 = new DataOutputStream(client_2.getOutputStream());
                BufferedReader inClient_2 = new BufferedReader(new InputStreamReader(client_2.getInputStream()));

                inputClient_1 = inClient_1.readLine();
                inputClient_2 = inClient_2.readLine();

                if (inputClient_1.equals(inputClient_2)) {
                    resClient_1 = "Draw";
                    resClient_2 = "Draw";
                    System.out.println("Это ничья.");
                } else if (inputClient_1.equals("R") && inputClient_2.equals("S")) {
                    resClient_1 = "You WIN";
                    resClient_2 = "You LOSE";
                    System.out.println("Player 1 победил.");

                } else if (inputClient_1.equals("S") && inputClient_2.equals("R")) {
                    resClient_1 = "You LOSE";
                    resClient_2 = "You WIN";
                    System.out.println("Player 2 победил.");
                } else if (inputClient_1.equals("R") && inputClient_2.equals("P")) {
                    resClient_1 = "You LOSE";
                    resClient_2 = "You WIN";
                    System.out.println("Player 2 победил.");
                } else if (inputClient_1.equals("P") && inputClient_2.equals("R")) {
                    resClient_1 = "You WIN";
                    resClient_2 = "You LOSE";
                    System.out.println("Player 1 победил.");
                } else if (inputClient_1.equals("S") && inputClient_2.equals("P")) {
                    resClient_1 = "You WIN";
                    resClient_2 = "You LOSE";
                    System.out.println("Player 1 победил.");
                } else if (inputClient_1.equals("P") && inputClient_2.equals("S")) {
                    resClient_1 = "You LOSE";
                    resClient_2 = "You WIN";
                    System.out.println("Player 2 победил.");
                }

                outClient_1.writeBytes(resClient_1.toUpperCase());
                outClient_2.writeBytes(resClient_2.toUpperCase());
                client_1.close();
                client_2.close();

                System.out.println("\nЖдем новых игроков ...\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}