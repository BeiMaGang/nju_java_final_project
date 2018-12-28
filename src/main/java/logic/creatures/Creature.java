package logic.creatures;/*
 * @author:Wu Gang
 * @create: 2018-10-07 19:15
 */

import annotation.Description;
import controllers.GameModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import logic.battle.Board;
import logic.position.Position;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class Creature extends logic.creatures.Being implements Skill, Runnable{
    protected enum Status{alive, deadJustNow, dead};
    private static int x[] = new int[]{0, 0, 1, -1};
    private static int y[] = new int[]{1, -1, 0, 0};
    private static Image deadImage = new Image("dead.png");
    protected static Board board;

    private Position<Creature> position = new Position<>(-1, -1, null);
    private Image myImage;
    private String imageURL;
    protected volatile Status status = Status.alive;

    protected Attributes attributes = new Attributes();

    public abstract void sayName();
    public abstract String getName();
    public abstract void randomMoveThread();
    public abstract void fightThread();


    public Creature() {
        String fileName = this.getClass().getName().split("\\.")[this.getClass().getName().split("\\.").length - 1] + ".png";
        this.imageURL = fileName;
        this.myImage = new Image(this.imageURL);
    }

    public void moveTo(Position<Creature> position){
        this.position.setCreature(null);
        this.position = position;
        this.position.setCreature(this);
    }

    protected void randomMove(int y1, int y2){
        Random random = new Random();
        int nextMove = random.nextInt(4);
        int nextMoveX = this.position.getX() + x[nextMove];
        int nextMoveY = this.position.getY() + y[nextMove];

        synchronized (board){
            if(isAlive() && (!board.isBeyondTheMark(nextMoveX))
                    && (nextMoveY >= y1 && nextMoveY <y2)
                    && board.get(nextMoveX, nextMoveY).getCreature() == null
                    && !GameModel.getInstance().isHitBullet(nextMoveX, nextMoveY)){
                moveTo(board.get(nextMoveX, nextMoveY));
            }
        }
    }

    @Description(todo = "死亡线程")
    private void dieThread(){
        (new Thread(() ->{
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                this.status = Status.dead;
                this.position.removeCreature();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, getName() + "die")).start();
    }

    @Description(todo = "为了加快游戏进度，每五秒会增加5点攻击力")
    private void addAckThread(){
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                setAck(getAck() + 5);
            }
        }).start();
    }

    public void testThread(){
        (new Thread(() ->{
            while (isAlive()) {
                try {
                    HPChange(-10);
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })).start();
    }

    @Description(todo = "在画布上画出自己")
    public void draw(GraphicsContext gc, double x, double y, double size){
        if(isAlive()) {
//            System.out.println(getName() + "在画自个" + x + " " + y);
            gc.drawImage(this.myImage, x, y, size, size);

            gc.setFill(Color.valueOf("red"));
            gc.fillRect(x, y, size, size / 6);

            gc.setFill(Color.color(0, 1, 0));
            double hpRatio = attributes.curHP / attributes.maxHP;
            double hpLength = size * hpRatio;
            gc.fillRect(x, y, hpLength, size / 6);
        }else if (isDeadJustNow()){
//            System.out.println(getName() + "画自己坟墓");
            gc.drawImage(deadImage, x, y, size, size);
        }
    }

    @Description(todo = "治疗或伤害使得HP变化")
    public void HPChange(double change){
        this.attributes.curHP += change;
        if (this.attributes.curHP <= 0) {
            this.attributes.curHP = 0;
            die();
        } else if (this.attributes.curHP > this.attributes.maxHP)
            this.attributes.curHP = this.attributes.maxHP;
    }

    public void die(){
        this.status = Status.deadJustNow;
        synchronized (GameModel.class){
            GameModel.class.notify();
        }
        dieThread();
    }

    @Override
    public void run(){
        randomMoveThread();
//        testThread();
        addAckThread();
        fightThread();
    }

    public Image getMyImage() {
        return myImage;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Position<Creature> getPosition() {
        return position;
    }

    public boolean isAlive() {
        return this.status == Status.alive;
    }

    public boolean isDeadJustNow() {
        return this.status == Status.deadJustNow;
    }

    public static void setBoard(Board board) {
        Creature.board = board;
    }

    public void setAttributes(double ack, double def, double HP) {
        attributes.ack = ack;
        attributes.curHP = HP;
        attributes.maxHP = HP;
        attributes.def = def;
    }

    public double getAck(){
        return attributes.ack;
    }

    public double getDef(){
        return attributes.def;
    }

    public double getCurHP(){
        return attributes.curHP;
    }

    public double getMaxHP(){
        return attributes.maxHP;
    }

    public void setAck(double value){
        attributes.ack = value;
    }

    public void setDef(double value){
        attributes.def = value;
    }

    public void setCurHP(double value){
        attributes.curHP = value;
    }

    public void setMaxHP(double value){
        attributes.maxHP = value;
    }

    @Description(todo = "立即死亡，用于清理线程")
    public void dieRightNow(){
        status = Status.dead;
        synchronized (GameModel.class){
            GameModel.class.notifyAll();
        }
        this.position.removeCreature();
    }
}

class Attributes{
    double maxHP = 60;
    double curHP = 60;
    double ack = 30;
    double def = 5;
}





