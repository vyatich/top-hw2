package game_logic;

import socket_executors.TcpSocketExecutor;
import socket_executors.UdpSocketExecutor;

import java.util.Scanner;
public class Game {

    private static final int NUMBER_OF_GAMES = 5;
    private static final RockPaperScissors rockPaperScissors = new RockPaperScissors();
    private static int hostCounter = 0;
    private static int playerCounter = 0;

    public void chooseMode(UdpSocketExecutor udpSocketExecutor, TcpSocketExecutor tcpSocketExecutor, int PORT) {
        tcpSocketExecutor.write("Choose mode plz");
        String mode = tcpSocketExecutor.read();
        switch (mode) {
            case "/1" -> gameBotVsBot(udpSocketExecutor, PORT);
            case "/2" -> gamePlayerVsBot(udpSocketExecutor, tcpSocketExecutor, PORT);
//            case "/3" -> gamePlayerVsPlayer();
        }
    }

    public void clientChooseMode(TcpSocketExecutor tcpSocketExecutor, UdpSocketExecutor udpSocketExecutor, Scanner scanner) {
        while (true) {
            System.out.println(tcpSocketExecutor.read());
            String read = scanner.nextLine();
            tcpSocketExecutor.write(read);
            switch (read) {
                case "/1" -> clientGameBotVsBot(udpSocketExecutor);
                case "/2" -> clientGamePlayerVsBot(tcpSocketExecutor, udpSocketExecutor, scanner);
                case "/3" -> clientGamePlayerVsPlayer();
            }
        }
    }

    private void gameBotVsBot(UdpSocketExecutor udpSocketExecutor, int port) {
        while (NUMBER_OF_GAMES > hostCounter) {
            String bot1 = rockPaperScissors.botChoose();
            String bot2 = rockPaperScissors.botChoose();
            rockPaperScissors.comparison(bot1, bot2);
            String title = "    <---------BOT vs BOT---------->\n" + rockPaperScissors + "\n";
            udpSocketExecutor.sendMessage("Bot 1 choose " + bot1 + "    <------>    "
                    + "Bot 2 choose " + bot2, port);
            udpSocketExecutor.sendMessage(title, port);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hostCounter++;
        }
        udpSocketExecutor.sendMessage(rockPaperScissors.whoWin(), port);
        rockPaperScissors.setZero();
        hostCounter = 0;
        System.out.println(hostCounter);
    }

    private void clientGameBotVsBot(UdpSocketExecutor udpSocketExecutor) {
        while (playerCounter < NUMBER_OF_GAMES) {
            for (int i = 0; i < 2; i++) {
                System.out.println(udpSocketExecutor.readMessage());
            }
            playerCounter++;
        }
        System.out.println(udpSocketExecutor.readMessage());
        playerCounter = 0;
    }

    private void gamePlayerVsBot(UdpSocketExecutor udpSocketExecutor, TcpSocketExecutor tcpSocketExecutor, int port) {
        String title = "<---------USER vs COMPUTER---------->" + "Please choose" +
                "  /r - ROCK" +
                " /p - PAPER" +
                " /s - SCISSORS";
        while (hostCounter < NUMBER_OF_GAMES) {
            tcpSocketExecutor.write(title);
            String playerChoose = tcpSocketExecutor.read();
            rockPaperScissors.comparison(playerChoose, rockPaperScissors.botChoose());
            udpSocketExecutor.sendMessage(rockPaperScissors.toString(), port);
            hostCounter++;
        }
        udpSocketExecutor.sendMessage(rockPaperScissors.whoWin(), port);
        rockPaperScissors.setZero();
        hostCounter = 0;
    }

    private void clientGamePlayerVsBot(TcpSocketExecutor tcpSocketExecutor, UdpSocketExecutor udpSocketExecutor,
                                       Scanner scanner) {
        while (playerCounter < NUMBER_OF_GAMES) {
            System.out.println(tcpSocketExecutor.read());
            tcpSocketExecutor.write(scanner.nextLine());
            System.out.println(udpSocketExecutor.readMessage());
            playerCounter++;
        }
        System.out.println(udpSocketExecutor.readMessage());
        playerCounter = 0;
    }

    private void gamePlayerVsPlayer(TcpSocketExecutor tcpSocketExecutor, UdpSocketExecutor udpSocketExecutor) {
//        String title = "<---------USER vs USER---------->" + "Please choose" +
//                "  /r - ROCK" +
//                " /p - PAPER" +
//                " /s - SCISSORS";
//        tcpSocketExecutor.write(title);
//        while(hostCounter < NUMBER_OF_GAMES) {
//            String choicePlayer = tcpSocketExecutor.read();
//
//
//            hostCounter++;
//        }
//        hostCounter = 0;
        //TODO тут будет реализация сервера
    }

    private void clientGamePlayerVsPlayer() {
        //TODO тут будет реализация клиента
    }
}
