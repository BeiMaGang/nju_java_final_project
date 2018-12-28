package logic.creatures.calabashBrother;/*
 * @author:Wu Gang
 * @create: 2018-11-21 18:54
 */

import controllers.GameController;
import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.battle.EvilSide;
import logic.creatures.CalabashBrother;
import logic.creatures.Creature;
import logic.creatures.enmu.CalabashBrotherEnum;

import java.util.concurrent.TimeUnit;

public class LiuWa extends CalabashBrother {
    public LiuWa(){
        super(CalabashBrotherEnum.sixth);
        setAttributes(15, 3, 60);
    }

    @Override
    public void releaseSkill() {
        if (isAlive()) {
            if (cooldown == 0) {
                String content = "六娃发动技能，自己无敌一段时间";
                Platform.runLater(() -> GameController.playBox(new Image("skill6.gif"), content, 3));
                skillCool(20);
                status = Status.deadJustNow;
                new Thread(()->{
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    status = Status.alive;
                    new Thread(this).start();
                }).start();
            } else {
                System.out.println(getName() + "技能没有冷却好 剩余:" + cooldown);
            }
        }
    }
}
