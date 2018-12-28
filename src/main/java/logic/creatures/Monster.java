package logic.creatures;/*
 * @author:Wu Gang
 * @create: 2018-10-07 20:22
 */

import logic.battle.Board;
import logic.creatures.enmu.MonsterEnum;
import logic.creatures.util.Pictures;

import java.util.concurrent.TimeUnit;

//怪兽实体
public abstract class Monster extends Creature implements Shoot{
    private MonsterEnum oneMonster;

    public Monster(MonsterEnum oneMonster) {
        this.oneMonster = oneMonster;
    }

    @Override
    public String getName(){
        return oneMonster.name;
    }

    @Override
    public void sayName() {
        System.out.print(oneMonster.name);
    }

    @Override
    public void releaseSkill(){
        assert false;
    }

    @Override
    public void randomMoveThread(){
        (new Thread(new Runnable() {
            @Override
            public void run() {
                while (isAlive()){
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                        randomMove(board.getSIZE() / 2, board.getSIZE());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        })).start();
    }


    @Override
    public Bullet shoot() {
        return new MonsterBullet(getPosition().getY(), getPosition().getX(), -1, this);
    }

    @Override
    public void fightThread(){
        new Thread(() -> {
            while (isAlive()){
                new Thread(shoot()).start();
                try {
                    TimeUnit.MILLISECONDS.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

class MonsterBullet extends Bullet{
    @Override
    public boolean isHitGoal() {
        return !Board.getInstance().get(y, x).isEmpty()
                && Board.getInstance().get(y, x).getCreature().isAlive()
                && !(Board.getInstance().get(y,x).getCreature() instanceof Monster);
    }

    public MonsterBullet(int x, int y, int direction, Creature attacker) {
        super(x, y, direction, attacker);
        setBulletImage(Pictures.bulletEvil);
        setHitImage(Pictures.hitEvil);
    }
}