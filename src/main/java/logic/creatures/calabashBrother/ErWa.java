package logic.creatures.calabashBrother;/*
 * @author:Wu Gang
 * @create: 2018-11-21 18:52
 */

import controllers.GameController;
import javafx.scene.image.Image;
import logic.battle.EvilSide;
import logic.creatures.CalabashBrother;
import logic.creatures.enmu.CalabashBrotherEnum;

public class ErWa extends CalabashBrother {
    public ErWa() {
        super(CalabashBrotherEnum.second);
        setAttributes(17, 5, 80);
    }

    @Override
    public void releaseSkill() {
        if (isAlive()){
            if(cooldown == 0) {
                String content = "二娃发动技能，锁定蛇精（倘若都已经死了，则技能无效）使其失去一定血量";
                GameController.playBox(new Image("skill2.gif"), content, 3);
                synchronized (EvilSide.getInstance().getEncouragement()) {
                    if(EvilSide.getInstance().getEncouragement().isAlive())
                        EvilSide.getInstance().getEncouragement().HPChange(-1 * (getAck() + 10));
                }
                skillCool(20);
            }else {
                System.out.println(getName() + "技能没有冷却好 剩余:" + cooldown);
            }
        }
    }
}
