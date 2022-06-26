package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class ClientUDPComputer {

    private final String ADDRESS;
    private final int SERVERPORT;
    private final byte[] RECEIVEGBUF;

    public ClientUDPComputer(String ADDRESS, int SERVERPORT, byte[] RECEIVEGBUF) {
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
                //Рандомно выбираем значение из перечислений для другого игрока
                GameValues[] valuesComputer = GameValues.values();
                int randomValue = new Random().nextInt(valuesComputer.length);
                String valueFromComputer = valuesComputer[randomValue].name;
                System.out.println(valueFromComputer.toUpperCase());

                //Отправляем запрос серверу
                byte[] inputMsg = valueFromComputer.getBytes();
                DatagramPacket sendMsg =
                        new DatagramPacket(inputMsg, inputMsg.length,
                                InetAddress.getByName(ADDRESS), SERVERPORT);
                clientSocket.send(sendMsg);

                //Получаем ответ от сервера
                DatagramPacket receiveMsgToServer =
                        new DatagramPacket(RECEIVEGBUF, RECEIVEGBUF.length);
                clientSocket.receive(receiveMsgToServer);

                // Вывод на экране полученные данные
                String getValuesFromServer =
                        new String(receiveMsgToServer.getData()).replaceAll("[^A-Za-zА-Яа-я\\d\\s+]", "");
                System.out.println("Ответ от игрока: " + getValuesFromServer);

                GameValues.compareValues(valueFromComputer, getValuesFromServer);
                System.out.println("Количество ходов: " + ++count);
            }
        } catch (IOException e) {
            System.out.println("Сообщение: " + e.getMessage());
        }
    }
}
