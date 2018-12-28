package logic.creatures.enmu;

public enum MonsterEnum {
    scorpion("蝎精",1),  snake("蛇精", 2), underlings("喽啰", 3);

    public String name;
    public int index;

    MonsterEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
}
