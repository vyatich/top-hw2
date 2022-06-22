package socket_executors;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;

public class UdpSocketExecutor implements Closeable{

    private InetAddress inetAddress;
    private DatagramSocket datagramSocket;

    public UdpSocketExecutor(String address) {
        try {
            this.inetAddress = InetAddress.getByName(address);
            this.datagramSocket = new DatagramSocket();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public UdpSocketExecutor(int port, String address) {
        try {
            this.inetAddress = InetAddress.getByName(address);
            this.datagramSocket = new DatagramSocket(port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message, int port) {
        try {
            datagramSocket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, inetAddress, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readMessage() {
        String message = null;
        try {
            byte[] bytes = new byte[10480];
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
            datagramSocket.receive(datagramPacket);
            byte[] data = datagramPacket.getData();
            message = new String(data, 0, datagramPacket.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public void close() throws IOException {
        datagramSocket.close();
    }
}
