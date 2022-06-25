package serverpack;

public class ServerApp {
    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.startServer();
        //CompVsCompServer cc = new CompVsCompServer();
       // cc.startCvsCServer();
    }
}
