package logic.creatures.calabashBrother;/*
 * @author:Wu Gang
 * @create: 2018-11-21 18:49
 */

import controllers.GameController;
import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.battle.EvilSide;
import logic.creatures.CalabashBrother;
import logic.creatures.enmu.CalabashBrotherEnum;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DaWa extends CalabashBrother {
    public static DaWa daWa = new DaWa();
    public DaWa() {
        super(CalabashBrotherEnum.first);
        setAttributes(30, 5, 100);
    }
    @Override
    public void releaseSkill() {
        if(isAlive()) {
            if (cooldown == 0) {
                String content = "大娃发动技能，使自己攻击力永久性增加10";
                GameController.playBox(new Image("skill1.gif"), content, 3);
                setAck(getAck() + 10);
                skillCool(20);
            } else {
                System.out.println(getName() + "技能没有冷却好 剩余:" + cooldown);
            }
        }
    }
}
