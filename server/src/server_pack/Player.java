package server_pack;

public class Player {
    private int port;
    private Choice choices;

    public Player() {
    }

    public Player(int port, Choice choices) {
        this.port = port;
        this.choices = choices;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Choice getChoice() {
        return choices;
    }

    public void setChoice(Choice choices) {
        this.choices = choices;
    }
}