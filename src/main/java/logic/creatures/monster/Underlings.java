package logic.creatures.monster;/*
 * @author:Wu Gang
 * @create: 2018-11-21 19:18
 */

import logic.creatures.Monster;
import logic.creatures.enmu.MonsterEnum;

public class Underlings extends Monster {
    public Underlings() {
        super(MonsterEnum.underlings);
        setAttributes(35, 8, 80);
    }

    @Override
    public void releaseSkill() {

    }
}
