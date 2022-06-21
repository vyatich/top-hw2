package game_logic;

import java.util.Arrays;
import java.util.List;

public class RockPaperScissors {
    private int firstPlayerPoints = 0;
    private int secondPlayerPoint = 0;

    public void comparison(String firstPlayerChoose, String secondPlayerChoose) {
        if (firstPlayerChoose.equals(secondPlayerChoose)) {
            setFirstPlayerPoints();
            setSecondPlayerPoint();
        } else if (firstPlayerChoose.equals("/r") && secondPlayerChoose.equals("/p")) {
            setSecondPlayerPoint();
        } else if (firstPlayerChoose.equals("/r") && secondPlayerChoose.equals("/s")) {
            setFirstPlayerPoints();
        } else if (firstPlayerChoose.equals("/p") && secondPlayerChoose.equals("/s")) {
            setSecondPlayerPoint();
        } else if (firstPlayerChoose.equals("/p") && secondPlayerChoose.equals("/r")) {
            setFirstPlayerPoints();
        } else if (firstPlayerChoose.equals("/s") && secondPlayerChoose.equals("/r")) {
            setSecondPlayerPoint();
        } else if (firstPlayerChoose.equals("/s") && secondPlayerChoose.equals("/p")) {
            setFirstPlayerPoints();
        } else {
            System.out.println();
        }
    }

    public String botChoose() {
        List<String> rocksPapersScissorsList = Arrays.asList("/r", "/p", "/s");
        return rocksPapersScissorsList.get((int) (Math.random() * rocksPapersScissorsList.size()));
    }

    public String whoWin() {
        return firstPlayerPoints > secondPlayerPoint ? "First player win!!!  " + this : "Second player win!!!" + this;
    }

    public void setZero() {
        firstPlayerPoints = 0;
        secondPlayerPoint = 0;
    }

    public int getFirstPlayerPoints() {
        return firstPlayerPoints;
    }

    public int getSecondPlayerPoint() {
        return secondPlayerPoint;
    }

    public void setFirstPlayerPoints() {
        firstPlayerPoints += 1;
    }

    public void setSecondPlayerPoint() {
        secondPlayerPoint += 1;
    }

    @Override
    public String toString() {
        return "First player - " + getFirstPlayerPoints() +
                " <-------> Second player - " + getSecondPlayerPoint();
    }
}
