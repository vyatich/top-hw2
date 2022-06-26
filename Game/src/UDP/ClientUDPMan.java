package UDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUDPMan {

    private final String ADDRESS;
    private final int SERVERPORT;
    private final byte[] RECEIVEGBUF;

    public ClientUDPMan(String ADDRESS, int SERVERPORT, byte[] RECEIVEGBUF) {
        this.ADDRESS = ADDRESS;
        this.SERVERPORT = SERVERPORT;
        this.RECEIVEGBUF = RECEIVEGBUF;
    }

    public void clientMove() {

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            int count = 0;
            while (!(count == 5)) {
                // Выведем запрос на ввод
                System.out.println("\nНапишите: Камень, Ножниц или Бумага");
                //Получаем данные с ввода из консоли и кодируем в байты
                String input = new BufferedReader(new InputStreamReader(System.in)).readLine();

                if (input.equals("Камень") || input.equals("Ножниц") || input.equals("Бумага")) {
                    byte[] inputMsg = input.getBytes();

                    //Отправляем запрос серверу
                    DatagramPacket sendMsg =
                            new DatagramPacket(inputMsg, inputMsg.length,
                                    InetAddress.getByName(ADDRESS), SERVERPORT);
                    clientSocket.send(sendMsg);
                } else System.out.println("Не верно выбрано слово");

                //Получаем ответ от сервера
                DatagramPacket receiveMsgToServer =
                        new DatagramPacket(RECEIVEGBUF, RECEIVEGBUF.length);
                clientSocket.receive(receiveMsgToServer);

                // Вывод на экране полученные данные
                String getValuesFromServer =
                        new String(receiveMsgToServer.getData()).replaceAll("[^A-Za-zА-Яа-я\\d\\s+]", "");
                System.out.println("Ответ от игрока: " + getValuesFromServer);

                GameValues.compareValues(input, getValuesFromServer);
                System.out.println("Количество ходов: " + ++count);
            }
        } catch (IOException e) {
            System.out.println("Сообщение: " + e.getMessage());
        }
    }
}

