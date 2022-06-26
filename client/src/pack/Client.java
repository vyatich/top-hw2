package pack;

import java.io.*;
import java.net.*;

public class Client {
    private static final String HOST = "localhost";
    private static final Integer PORT = 8081;
    private static final String msgRules = "\nПравила игры:" +
            "\n - (R)ock бьет (S)cissors" +
            "\n - (S)cissors бьет (P)aper" +
            "\n - (P)aper бьет (R)ock\n";

    public void startClient() {

        String input = "";
        String response;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        try (Socket client = new Socket(Client.HOST, Client.PORT)) {
            DataOutputStream outToServer = new DataOutputStream(client.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));

            do {
                if (input.equals("/rules")) {
                    System.out.println(Client.msgRules);
                }

                System.out.println("Запустите игру, выбрав (R)ock (P)aper, (S)cissors");
                System.out.print("или введите \"/rules\" чтобы увидеть правила: ");
                input = inFromUser.readLine();

            } while (!input.equals("R") && !input.equals("P") && !input.equals("S"));

            outToServer.writeBytes(input + "\n");
            System.out.println("\nВы ввели (" + input + ") -> ход передан на сервер. Ждите результат ...");

            response = inFromServer.readLine();

            System.out.println("Server>: " + response);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
