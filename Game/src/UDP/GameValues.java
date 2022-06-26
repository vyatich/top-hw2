package UDP;

public enum GameValues {
    ROCK("Камень"),
    PAPER("Бумага"),
    SCISSORS("Ножниц");

    final String name;

    GameValues(String name) {
        this.name = name;
    }

    public static GameValues valueOfLabel(String name) {
        for (GameValues e : values()) {
            if (e.name.equals(name)) {
                return e;
            }
        }
        return null;
    }

    public static void compareValues(String clientValue, String computerValue) {
        GameValues titleNameClient = GameValues.valueOfLabel(clientValue);
        GameValues titleNameComputer = GameValues.valueOfLabel(computerValue);

        assert titleNameClient != null;
        int valueClient = titleNameClient.ordinal();
        assert titleNameComputer != null;
        int valueComputer = titleNameComputer.ordinal();
        //Сравниваем значения игроков
        int compareValues = Integer.compare(valueClient, valueComputer);

        //Тут проставил "костыли", т.к. не смог придумать как по-другому
        //  сравнить логически большее значение Ножниц с меньшими Бумагой и Камнем
        String win = titleNameClient.name.toUpperCase() + " бьёт "
                + titleNameComputer.name.toUpperCase() + ". Вы победили!";
        String lose = titleNameComputer.name.toUpperCase() + " бьёт "
                + titleNameClient.name.toUpperCase() + ". Вы проиграли!";

        if ((valueClient == 0 && valueComputer == 2) || (valueComputer == 0 && valueClient == 2)) {
            if (valueClient == 0) {
                System.out.println(win);
            } else {
                System.out.println(lose);
            }
        } else {
            switch (compareValues) {
                case 0:
                    System.out.println("Ничья!");
                    break;
                case 1:
                    System.out.println(win);
                    break;
                case -1:
                    System.out.println(lose);
                    break;
            }
        }
    }
}
