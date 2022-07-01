package client_pack;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
    static Scanner sc = new Scanner(System.in);

    public void start() {
        System.out.println(
                        "/r - Rock\n" +
                        "/p - Paper\n" +
                        "/s - Scissors\n" +
                        "Enter your choice.");
        while (true) {
            try (DatagramSocket datagramSocket = new DatagramSocket()) {
                String s = sc.nextLine();
                byte[] output = s.getBytes();
                DatagramPacket datagramPacket =
                        new DatagramPacket(
                                output, output.length, InetAddress.getByName("localhost"), 8081);
                datagramSocket.send(datagramPacket);
                //
                byte[] buffer = new byte[10240];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(packet);
                byte[] inputData = packet.getData();
                String inputString = new String(inputData, 0, packet.getLength());
                System.out.println(inputString);
            } catch (IOException e) {
                System.out.println("Cannot connect to server. Try again later.");
            }
        }
    }
}