package logic.creatures.calabashBrother;/*
 * @author:Wu Gang
 * @create: 2018-11-21 18:53
 */

import controllers.GameController;
import javafx.scene.image.Image;
import logic.battle.EvilSide;
import logic.creatures.CalabashBrother;
import logic.creatures.Creature;
import logic.creatures.enmu.CalabashBrotherEnum;

public class SiWa extends CalabashBrother {
    public SiWa() {
        super(CalabashBrotherEnum.fourth);
        setAttributes(20, 7, 75);
        skillCool(7);
    }

    @Override
    public void releaseSkill() {
        if(isAlive()) {
            if (cooldown == 0) {
                String content = "四娃发动技能，敌方全体失去自己攻击力的80%HP";
                GameController.playBox(new Image("skill4.gif"), content, 3);
                synchronized (EvilSide.getInstance().getEncouragement()){
                    if (EvilSide.getInstance().getEncouragement().isAlive())
                        EvilSide.getInstance().getEncouragement().HPChange(-1 * (getAck() * 0.8));
                }
                for(Creature creature: EvilSide.getInstance().getCreatures()){
                    synchronized (creature){
                        if (creature.isAlive())
                            creature.HPChange(-1 * (getAck() * 0.8));
                    }
                }

                skillCool(8);
            } else {
                System.out.println(getName() + "技能没有冷却好 剩余:" + cooldown);
            }
        }
    }
}
