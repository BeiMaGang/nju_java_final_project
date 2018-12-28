package logic.creatures.monster;/*
 * @author:Wu Gang
 * @create: 2018-11-21 19:14
 */

import annotation.Description;
import logic.creatures.Bullet;
import logic.creatures.Monster;
import logic.creatures.enmu.MonsterEnum;

public class Scorpion extends Monster {
    public Scorpion() {
        super(MonsterEnum.scorpion);
        setAttributes(45, 10, 150);
    }

    @Description(todo = "治疗或伤害使得HP变化")
    public void HPChange(double change){
        super.HPChange(change);
        setAck(getAck() + change * -1);
    }

    @Override
    public void releaseSkill() {

    }
}
