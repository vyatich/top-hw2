package UDP;

import java.io.IOException;
import java.net.*;
import java.util.Random;

public class ServerUDP extends Thread {
    private final int PORT;
    private final byte[] RECEIVEBUF;

    public ServerUDP(int PORT, byte[] RECEIVEBUF) {
        this.PORT = PORT;
        this.RECEIVEBUF = RECEIVEBUF;
    }

    public static void main(String[] args) {
        new ServerUDP(8122, new byte[1024]).start();
    }

    public void run() {
        //Создаем порт для Сервера и ограничение буфера, для сообщений
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            DatagramPacket getValuesFromClient = new DatagramPacket(RECEIVEBUF, RECEIVEBUF.length,
                                                        InetAddress.getByName("127.0.0.1"), PORT);
            System.out.println("Ожидание подключения\n");

            while (true) {
                // Получаем адрес и порт клиента и выводим в консоль
                serverSocket.receive(getValuesFromClient);
                InetAddress clientAddress = getValuesFromClient.getAddress();
                int clientPort = getValuesFromClient.getPort();
                System.out.println("Клиент: " + clientAddress + ":" + clientPort);


                // Выводим в консоль полученное сообщение от клиента
                String receiveMsgFromClient = new String(getValuesFromClient.getData());
                System.out.println("Ответ от клиента: "
                        + receiveMsgFromClient.replaceAll("[^A-Za-zА-Яа-я\\d]", "") + "\n");

                //Рандомно выбираем значение из перечислений для другого игрока
                GameValues[] valuesComputer = GameValues.values();
                int randomValue = new Random().nextInt(valuesComputer.length);
                String valueFromComputer = valuesComputer[randomValue].name;
                System.out.println(valueFromComputer.toUpperCase());


                // Создаем ответ клиенту и отправляем на полученные раннее адрес и порт
                byte[] bufReceiveMsgFromClient = valueFromComputer.getBytes();
                DatagramPacket outputMsgToClient =
                        new DatagramPacket(bufReceiveMsgFromClient, bufReceiveMsgFromClient.length,
                                clientAddress, clientPort);
                serverSocket.send(outputMsgToClient);
            }
        } catch (IOException e) {
            System.out.println("Сообщение: " + e.getMessage());
        }
    }
}
