package serverpack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class CompVsCompServer {

    public void startCvsCServer() {
        try (DatagramSocket datagramSocket = new DatagramSocket()){
            String move1 = GameServer.generateComputerMove();
            String move2 = GameServer.generateComputerMove();
            String result = "1 ход: " + move1 + " 2 ход: " + move2;
            byte[] bytes = result.getBytes(StandardCharsets.UTF_8);
            InetAddress address = InetAddress.getByName("127.0.0.1");
            int port = 8081;
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, address, port);
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
