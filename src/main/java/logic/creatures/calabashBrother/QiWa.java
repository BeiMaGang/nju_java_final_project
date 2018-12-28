package logic.creatures.calabashBrother;/*
 * @author:Wu Gang
 * @create: 2018-11-21 18:55
 */

import annotation.Description;
import controllers.GameController;
import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.battle.EvilSide;
import logic.creatures.CalabashBrother;
import logic.creatures.Creature;
import logic.creatures.enmu.CalabashBrotherEnum;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class QiWa extends CalabashBrother {
    public QiWa() {
        super(CalabashBrotherEnum.seventh);
        setAttributes(20, 5, 70);
        skillCool(15);
    }

    @Description(todo = "七娃技能，瞬间消灭对面一个怪物（蛇精除外）")
    @Override
    public void releaseSkill() {
        if(isAlive()) {
            if (cooldown == 0) {
                String content = "七娃拿出了自己的葫芦，瞬间击杀了一个怪物";
                System.out.println(content);
                Platform.runLater(() -> GameController.playBox(new Image("skill7.gif"), content, 3));
                Random random = new Random();
                int next = random.nextInt(EvilSide.getInstance().getCreatures().size());
                int i = 0;
                while (!EvilSide.getInstance().getCreatures().get(next).isAlive()) {
                    next = random.nextInt(EvilSide.getInstance().getCreatures().size());
                    i++;
                    if (i >= 100)
                        break;
                }
                if (i != 100) {
                    EvilSide.getInstance().getCreatures().get(next).die();
                }
                skillCool(30);
            } else {
                System.out.println(getName() + "技能没有冷却好 剩余:" + cooldown);
            }
        }
    }
}
