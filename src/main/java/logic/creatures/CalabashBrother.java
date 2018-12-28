package logic.creatures;/*
 * @author:Wu Gang
 * @create: 2018-10-07 20:21
 */

import logic.battle.Board;
import logic.creatures.enmu.CalabashBrotherEnum;

import java.util.concurrent.TimeUnit;

//葫芦娃实体
public abstract class CalabashBrother extends Creature implements Shoot{
    private CalabashBrotherEnum oneCalabashBrother;
    protected int cooldown;//冷却时间

    public CalabashBrother(CalabashBrotherEnum calabashBrother) {
        this.oneCalabashBrother = calabashBrother;
    }

    @Override
    public String getName(){
        return oneCalabashBrother.name;
    }

    @Override
    public void sayName() {
        System.out.print(oneCalabashBrother.name);
    }

    @Override
    public void randomMoveThread(){
        (new Thread(() -> {
                while (isAlive()) {
                    try {
                        randomMove(0, board.getSIZE() / 2);
                        TimeUnit.MILLISECONDS.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            System.out.println(oneCalabashBrother.name + " random move exited");
        }, getName() + "move")).start();
    }

    @Override
    public void fightThread() {
        new Thread(() -> {
            while (isAlive()){
                new Thread(shoot()).start();
                try {
                    TimeUnit.MILLISECONDS.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, getName() + "fight").start();
    }

    @Override
    public Bullet shoot(){
        return new HuluwaBullet(getPosition().getY(), getPosition().getX(), 1, this);
    }

    public int getCooldown() {
        return cooldown;
    }

    public void skillCool(int time){
        new Thread(() -> {
            cooldown = time;
            while (cooldown != 0) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    cooldown--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, getName() + "技能冷却线程").start();
    }
}

class HuluwaBullet extends Bullet{

    public HuluwaBullet(int x, int y, int direction, Creature attacker) {
        super(x, y, direction, attacker);
    }

    @Override
    public boolean isHitGoal() {
        return !Board.getInstance().get(y, x).isEmpty()
                && Board.getInstance().get(y, x).getCreature().isAlive()
                && !(Board.getInstance().get(y,x).getCreature() instanceof CalabashBrother)
                && !(Board.getInstance().get(y,x).getCreature() instanceof GrandPa);
    }
}