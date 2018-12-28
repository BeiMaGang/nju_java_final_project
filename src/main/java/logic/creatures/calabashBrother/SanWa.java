package logic.creatures.calabashBrother;/*
 * @author:Wu Gang
 * @create: 2018-11-21 18:52
 */

import controllers.GameController;
import javafx.scene.image.Image;
import logic.battle.EvilSide;
import logic.creatures.CalabashBrother;
import logic.creatures.enmu.CalabashBrotherEnum;

public class SanWa extends CalabashBrother {
    public SanWa() {
        super(CalabashBrotherEnum.third);
        setAttributes(30, 15, 80);
    }

    @Override
    public void releaseSkill() {
        if (isAlive()){
            if(cooldown == 0) {
                String content = "三娃发动技能， 永久性的增加防御力10，同时恢复已损失血量的20%";
                GameController.playBox(new Image("skill3.gif"), content, 3);
                setDef(getDef() + 10);
                synchronized (this) {
                    HPChange((getMaxHP() - getCurHP()) * 0.2);
                }
                skillCool(15);
            }else {
                System.out.println(getName() + "技能没有冷却好 剩余:" + cooldown);
            }
        }
    }
}
