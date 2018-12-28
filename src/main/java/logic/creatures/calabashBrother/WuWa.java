package logic.creatures.calabashBrother;/*
 * @author:Wu Gang
 * @create: 2018-11-21 18:54
 */

import controllers.GameController;
import javafx.scene.image.Image;
import logic.battle.EvilSide;
import logic.creatures.CalabashBrother;
import logic.creatures.Creature;
import logic.creatures.enmu.CalabashBrotherEnum;

public class WuWa extends CalabashBrother {
    public WuWa() {
        super(CalabashBrotherEnum.fifth);
        setAttributes(20, 7, 75);
        skillCool(5);
    }

    @Override
    public void releaseSkill() {
        if(isAlive()) {
        if (cooldown == 0) {
            String content = "四娃发动技能，敌方全体失去固定的30HP";
            GameController.playBox(new Image("skill5.gif"), content, 3);
            synchronized (EvilSide.getInstance().getEncouragement()){
                if (EvilSide.getInstance().getEncouragement().isAlive())
                    EvilSide.getInstance().getEncouragement().HPChange(-30);
            }
            for(Creature creature: EvilSide.getInstance().getCreatures()){
                synchronized (creature){
                    if (creature.isAlive())
                        creature.HPChange(-30);
                }
            }

            skillCool(10);
        } else {
            System.out.println(getName() + "技能没有冷却好 剩余:" + cooldown);
        }
    }

    }
}
