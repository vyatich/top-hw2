package server_pack;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

import static server_pack.Choice.*;
import static server_pack.Choice.values;

public class Server {

    private final Player player;

    static Random random = new Random();

    public Server(Player player) {
        this.player = player;
    }

    public void start(){
        try (DatagramSocket datagramSocket = new DatagramSocket(8081)) {
            byte[] buffer = new byte[10240];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(packet);
                player.setPort(packet.getPort());
                byte[] inputData = packet.getData();
                String inputString = new String(inputData, 0, packet.getLength());
                System.out.println(inputString);
                switch (inputString) {
                    case "/r":
                        player.setChoice(Rock);
                        break;
                    case "/p":
                        player.setChoice(Paper);
                        break;
                    case "/s":
                        player.setChoice(Scissors);
                        break;
                    default:
                        sendMessage(player.getPort(), "Try again. Wrong command", datagramSocket);
                        continue;
                }
                Choice pcChoice = generateAnswer();
                sendMessage(player.getPort(), compareChoices(pcChoice, player.getChoice()), datagramSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String compareChoices(Choice pcChoice, Choice secondPlayerChoice) {
        String resultMessage = "Your choice - " + secondPlayerChoice + "\n" + "PC choice - " + pcChoice + "\n";
        if (pcChoice.equals(secondPlayerChoice))
            return resultMessage + "draw";
        if ((pcChoice.equals(Rock) && secondPlayerChoice.equals(Scissors))
                || (pcChoice.equals(Scissors) && secondPlayerChoice.equals(Paper))
                || (pcChoice.equals(Paper) && secondPlayerChoice.equals(Rock)))
            return resultMessage + "you lose";
        return resultMessage + "you win";
    }

    private Choice generateAnswer() {
        return Choice.values()[random.nextInt(values().length)];
    }

    private void sendMessage(int clientPort, String input, DatagramSocket socket) {
        try {
            byte[] bytes = input.getBytes();
            DatagramPacket packet =
                    new DatagramPacket(bytes, bytes.length, InetAddress.getByName("127.0.0.1"), clientPort);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
