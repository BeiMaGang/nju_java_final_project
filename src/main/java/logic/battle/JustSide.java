package logic.battle;/*
 * @author:Wu Gang
 * @create: 2018-10-07 20:19
 */

import logic.creatures.calabashBrother.*;
import logic.creatures.Creature;
import logic.creatures.GrandPa;

import java.util.ArrayList;


public class JustSide extends Sides{
    private static JustSide justSide = new JustSide();

    public static JustSide getInstance(){
        return justSide;
    }

    private JustSide() {
        this.side = 0;
        this.creatures = new ArrayList<>();
        int i = 0;

        this.creatures.add(new DaWa());
        this.creatures.add(new ErWa());
        this.creatures.add(new SanWa());
        this.creatures.add(new SiWa());
        this.creatures.add(new WuWa());
        this.creatures.add(new LiuWa());
        this.creatures.add(new QiWa());

        this.encouragement = new GrandPa();
        this.encouragement.moveTo(formation.board.get(0,0));
        this.changeFormation("snake");
//        formation.board.setCreatureOnPosition(0,0, this.encouragement);
    }
    public void restart(){
        justSide = new JustSide();
    }
    @Override
    public void updateStatus() {

    }
}
