public class ClientApplication {

    public static void main(String[] args) {
        Client client = new Client(8081, "localhost");
        client.run();
    }
}
