package logic.creatures.monster;/*
 * @author:Wu Gang
 * @create: 2018-11-21 19:17
 */

import controllers.GameController;
import javafx.scene.image.Image;
import logic.creatures.Monster;
import logic.creatures.enmu.MonsterEnum;

public class Snake extends Monster {
    public Snake() {
        super(MonsterEnum.snake);
        setAttributes(40, 7, 100);
    }

    @Override
    public void releaseSkill() {

    }
}
