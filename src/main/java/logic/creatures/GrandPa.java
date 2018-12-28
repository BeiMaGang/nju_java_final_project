package logic.creatures;/*
 * @author:Wu Gang
 * @create: 2018-10-07 20:22
 */

import controllers.GameController;
import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.battle.JustSide;

import java.util.Random;
import java.util.concurrent.TimeUnit;

//爷爷
public class GrandPa extends Creature{
    final String name = "爷爷";

    public GrandPa(){
        setAttributes(0, 3, 100);
    }

    @Override
    public void sayName() {
        System.out.print(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void randomMoveThread() {
        (new Thread(() -> {
            while (this.isAlive()){
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    randomMove(0, board.getSIZE() / 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(name + " random move exited");
        })).start();
    }

    @Override
    public void fightThread(){
        Random random = new Random();
        new Thread(()->{
            while (isAlive()){
                try {
                    int num = random.nextInt(100);
                    if (num < 33) {
                        releaseSkill();
                    }
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void releaseSkill() {
        System.out.println("爷爷释放了治疗术,所有葫芦娃恢复了10HP");
//        Platform.runLater(() -> GameController.playBox(new Image("skillyy.gif"), "爷爷释放了治疗术，所有葫芦娃恢复了10HP", 2));
        for(Creature creature: JustSide.getInstance().getCreatures()){
            synchronized (creature){
                if(creature.isAlive())
                    creature.HPChange(10);
            }
        }
    }
}
