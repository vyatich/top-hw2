public class ServerApplication {
    public static void main(String[] args) {
        Server server = new Server(8081, "localhost");
        server.start();
    }
}
