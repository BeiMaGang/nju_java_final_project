package logic.creatures;

import annotation.Description;
import controllers.GameModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import logic.battle.Board;
import logic.creatures.util.Pictures;

import java.util.concurrent.TimeUnit;

/*
 * @class name:Bullet
 * @author:Wu Gang
 * @create: 2018-12-23 12:50
 * @description:
 */
public abstract class Bullet extends Being implements Runnable{
    enum Status{flying, hit, end}
    protected int x;
    protected int y;
    private int direction;
    private Creature attacker;
    private long flyingTimeBreak = 500;
    private Image bulletImage = Pictures.bulletJust;
    private Image hitImage = Pictures.hitJust;
    private double sizeImage = 1;
    protected Status status = Status.flying;

    @Description(todo = "判断是否击中")
    public abstract boolean isHitGoal();

    @Description(todo = "子弹打中对方事件")
    public void hit(Creature victim){
        double change = victim.attributes.def - getAttacker().attributes.ack;
        if(change >= 0)
            change = -1;
        victim.HPChange(change);
    }

    public Bullet(int x, int y, int direction, Creature attacker) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.attacker = attacker;
    }

    @Description(todo = "在画布上画出自己")
    public void draw(GraphicsContext gc, double layoutX, double layoutY, double size){
        double startX = layoutX + x * size;
        double startY = layoutY + y * size;
        size = sizeImage * size;
        if(isFlying()) {
            gc.setFill(Color.BLUE);
//            gc.fillOval(startX + size / 2, startY + size / 2, size / 6, size / 6);
            gc.drawImage(bulletImage, startX, startY + size / 3,size * 3 / 4, size / 3);
        }
        else if(isHit()){
            gc.setFill(Color.rgb(255,0,0));
            gc.drawImage(hitImage, startX, startY,size, size);
        }
    }

    @Description(todo = "子弹前行速度")
    private void moveThread() {
        new Thread(() -> {
            while (isFlying()){
                try {
                    x += direction;
                    Board board = Board.getInstance();
                    synchronized (board){
                        if (board.isBeyondTheMark(x)){
                            toEnd();
                        }else if(isHitGoal()){
//                                System.out.println("hit " + board.get(y,x).getCreature());
                            hitThread();
                            hit(board.get(y,x).getCreature());
                        }
                    }
                    TimeUnit.MILLISECONDS.sleep(flyingTimeBreak);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, attacker.getName() + "子弹").start();
    }

    @Description(todo = "击中线程")
    private void hitThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    status = Status.hit;
                    TimeUnit.MILLISECONDS.sleep(300);
                    toEnd();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void run(){
        try {
            GameModel gameModel = GameModel.getInstance();
            synchronized (gameModel.getBullets()) {
                gameModel.getBullets().add(this);
            }
                moveThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getDirection() {
        return direction;
    }

    public Creature getAttacker() {
        return attacker;
    }

    public boolean isFlying(){
        return status == Status.flying;
    }

    public boolean isHit(){
        return status == Status.hit;
    }

    public boolean isEnd(){
        return status == Status.end;
    }

    public void setFlyingTimeBreak(long flyingTimeBreak) {
        this.flyingTimeBreak = flyingTimeBreak;
    }

    public void setBulletImage(Image bulletImage) {
        this.bulletImage = bulletImage;
    }

    public void setHitImage(Image hitImage) {
        this.hitImage = hitImage;
    }

    @Description(todo = "子弹状态变为end并从子弹列表里面剔除")
    public void toEnd(){
        status = Status.end;
        try {
            synchronized (GameModel.getInstance().getBullets()) {
                GameModel.getInstance().getBullets().remove(Bullet.this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Description(todo = "立刻消失，用于清理线程")
    public void fade(){
        status = Status.end;
    }
}
