package logic.battle;/*
 * @author:Wu Gang
 * @create: 2018-10-07 20:39
 */

import logic.creatures.monster.Scorpion;
import logic.creatures.monster.Snake;
import logic.creatures.monster.Underlings;

import java.util.ArrayList;

public class EvilSide extends Sides{
    private static EvilSide evilSide = new EvilSide();

    public static EvilSide getInstance(){
        return evilSide;
    }

    private int numOfMonsters;

    private EvilSide(int n) {
        this.side = 1;
        this.numOfMonsters = n;
        init();
    }

    private EvilSide() {
        this.side = 1;
        this.numOfMonsters = 6;
        init();
    }

    private void init(){
        this.encouragement = new Snake();
        this.creatures = new ArrayList<>();
        creatures.add(new Scorpion());
        for(int i = 1; i < numOfMonsters; i++)
            creatures.add(new Underlings());
        this.encouragement.moveTo(formation.board.get(formation.board.getSIZE()-1,formation.board.getSIZE()-1));
        this.changeFormation("snake");
    }

    public void restart(){
        evilSide = new EvilSide();
    }

    @Override
    public void updateStatus() {

    }
}
