package serverpack;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameServer {
    private static Logger logger = Logger.getLogger(GameServer.class.getSimpleName());
    private ServerSocket server;
    private BufferedWriter writer;
    private BufferedReader reader;
    public final int PORT = 8080;
    private static int winsCount = 0;
    private static int lossesCount = 0;

    public GameServer() {
    }

    public static int getWinsCount() {
        return winsCount;
    }

    public static int getLossesCount() {
        return lossesCount;
    }

    public void setWinsCount(int winsCount) {
        GameServer.winsCount = winsCount;
    }

    public void setLossesCount(int lossesCount) {
        GameServer.lossesCount = lossesCount;
    }

    public void startServer() {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен");
            Socket clientSocket = server.accept();
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            System.out.println("клиент " + clientSocket.getRemoteSocketAddress() + " подключен в" + getTime());
            logger.log(Level.INFO, GameServer::getTime);
            while (true) {
                String choice = reader.readLine();
                switch (choice) {
                    case "к" -> {
                        choice = "Камень";
                        System.out.println("Ход игрока: КАМЕНЬ");
                    }
                    case "н" -> {
                        choice = "Ножницы";
                        System.out.println("Ход игрока: НОЖНИЦЫ");
                    }
                    case "б" -> {
                        choice = "Бумага";
                        System.out.println("Ход игрока: БУМАГА");
                    }
                }
                String computerMove = generateComputerMove();
                System.out.println("Ход компьютера " + computerMove);
                writer.write(computerMove);
                writer.newLine();
                writer.flush();
                if (choice.equals(computerMove)) {
                    System.out.println("Ничья!");
                    writer.write("Ничья!");
                    writer.newLine();
                    writer.flush();
                } else if (ifPlayerWin(choice, computerMove)) {
                    setWinsCount(winsCount++);
                    System.out.println("Победил игрок! Побед игрока: " + getWinsCount());
                    writer.write("Победил игрок!");
                    writer.newLine();
                    writer.flush();
                } else if (!ifPlayerWin(choice, computerMove)) {
                    setLossesCount(++lossesCount);
                    System.out.println("Победил компьютер! Поражений игрока: " + getLossesCount());
                    writer.write("Победил компьютер!");
                    writer.newLine();
                    writer.flush();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            try {
                writer.close();
                reader.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getTime() {
        return new SimpleDateFormat("HH:mm:ss dd.MM.yy").format(Calendar.getInstance().getTime());
    }

    public static String generateComputerMove() {
        Random random = new Random();
        int randomNumber = random.nextInt(4);
        String computerMove = null;
        if (randomNumber == 1) {
            computerMove = "Камень";
        } else if (randomNumber == 2) {
            computerMove = "Ножницы";
        } else if (randomNumber == 3) {
            computerMove = "Бумага";
        }
        return computerMove;
    }

    private boolean ifPlayerWin(String computerMove, String choice) {
        return choice.equals("б") && computerMove.equals("Камень") ||
                (choice.equals("к") && computerMove.equals("Ножницы")) ||
                (choice.equals("н") && computerMove.equals("Бумага"));
    }
}

