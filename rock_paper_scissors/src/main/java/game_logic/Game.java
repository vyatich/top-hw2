package game_logic;

import socket_executors.TcpSocketExecutor;
import socket_executors.UdpSocketExecutor;

import java.io.Closeable;
import java.io.IOException;

public class Game implements Closeable {

    private static final int NUMBER_OF_GAMES = 5;
    private static final RockPaperScissors rockPaperScissors = new RockPaperScissors();
    private final int PORT;
    private final UdpSocketExecutor udpSocketExecutor;
    private final TcpSocketExecutor tcpSocketExecutor;

    public Game(int port, String address) {
        this.PORT = port;
        this.udpSocketExecutor = new UdpSocketExecutor(port, address);
        this.tcpSocketExecutor = new TcpSocketExecutor(address, port);
    }

    public void chooseMode() {
        udpSocketExecutor.sendMessage("Choose mode plz", PORT);
        String mode = tcpSocketExecutor.read();
        if (mode.equals("/1")) {
            gameBotVsBot(udpSocketExecutor);
        }
    }

    private void gameBotVsBot(UdpSocketExecutor udpSocketExecutor) {
        int counter = 0;
        while (NUMBER_OF_GAMES > counter) {
            String title = "\"<---------BOT vs BOT---------->" +
                    "   ******************   " + rockPaperScissors;
            udpSocketExecutor.sendMessage(title, PORT);
            rockPaperScissors.comparison(rockPaperScissors.botChoose(), rockPaperScissors.botChoose());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
        }
        udpSocketExecutor.sendMessage(rockPaperScissors.whoWin(), PORT);
        rockPaperScissors.setZero();
    }

    public void gamePlayerVsBot() {
        String title = "<---------USER vs COMPUTER---------->" + "Please choose" +
                "  /r - ROCK" +
                " /p - PAPER" +
                " /s - SCISSORS";
        int counter = 0;
        while (counter < NUMBER_OF_GAMES) {
            udpSocketExecutor.sendMessage(title, PORT);
            String playerChoose = tcpSocketExecutor.read();
            rockPaperScissors.comparison(playerChoose, rockPaperScissors.botChoose());
            udpSocketExecutor.sendMessage(rockPaperScissors.toString(), PORT);
            counter++;
        }
        udpSocketExecutor.sendMessage(rockPaperScissors.whoWin(), PORT);
        rockPaperScissors.setZero();
    }

    @Override
    public void close() throws IOException {
        tcpSocketExecutor.close();
        udpSocketExecutor.close();
    }
}
