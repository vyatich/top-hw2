package clientpack;

import serverpack.GameServer;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;
    public int countsTry = 5;
    public final int PORT = 8080;

    public void startClient() {
        try {
            clientSocket = new Socket("localhost", PORT);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Соединение с сервером установлено в " + GameServer.getTime());
            System.out.println("Я хочу сыграть с тобой в игру...");
            System.out.println("""
                    У тебя есть выбор:\s
                    к - КАМЕНЬ,
                    н - НОЖНИЦЫ,
                    б - БУМАГА,
                    в - выйти из игры""");
            for (int i = 0; i < 5; i++) {
                while (true) {
                    String choice = console.readLine();
                    if (choice.equals("к") || choice.equals("н") || choice.equals("б")) {
                        System.out.println("Осталось попыток: " + countsTry);
                        countsTry--;
                        writer.write(choice);
                        writer.newLine();
                        writer.flush();
                        String computerMove = reader.readLine();
                        System.out.println("Компьтер выбрал: " + computerMove);
                        String result = reader.readLine();
                        System.out.println("Результат: " + result);
                    } else if (choice.equals("в")) {
                        System.out.printf("Вы закончили игру\nкол-во побед = %d\nкол-во поражений = %d%n",
                                GameServer.getWinsCount(), GameServer.getLossesCount());
                        break;
                    } else {
                        System.out.println("""
                                Попробуй еще раз.\s
                                к - КАМЕНЬ,
                                н - НОЖНИЦЫ,
                                б - БУМАГА,
                                в - выйти из игры""");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                clientSocket.close();
                writer.close();
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void startCvsCClient() {
        while (true) {
            try {
                Socket clientSocketCC = new Socket("localhost", 8081);
                BufferedReader readerCC = new BufferedReader(new InputStreamReader(clientSocketCC.getInputStream()));
                String computer1Move = readerCC.readLine();
                System.out.println("Компьтер 1 выбрал: " + computer1Move);
                String computer2Move = readerCC.readLine();
                System.out.println("Компьтер 2 выбрал: " + computer2Move);
                String result = readerCC.readLine();
                System.out.println("Победил: " + result);
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    public int chooseGameType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Выберите тип игры: 1 - игрок против компьютера
                 2 - компьютер против компьютера
                 3 - игрок против игрока""");
        int gameType = scanner.nextInt();
        switch (gameType) {
            case 1:
                startClient();
            case 2:
                startCvsCClient();
        }return gameType;
    }
}
