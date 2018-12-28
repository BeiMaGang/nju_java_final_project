package logic.battle;/*
 * @author:Wu Gang
 * @create: 2018-10-07 22:14
 */

import annotation.Description;
import logic.creatures.Creature;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Sides implements Runnable{
    public static Formation formation;
    int side;
    ArrayList<Creature> creatures;
    Creature encouragement;//鼓励师

    public abstract void updateStatus();
    public void changeFormation(String name){
        int y = 0;
        if (side == 1){
            y = formation.board.getSIZE() / 2;
        }
        switch (name){
            case "snake":formation.snakeFormation(this, (formation.board.getSIZE() - creatures.size()) / 2, 1 + y);break;
            case "goose":formation.gooseFormation(this, (formation.board.getSIZE() - creatures.size()) / 2,
                    y * 2 - 1 + (formation.board.getSIZE() / 2 - creatures.size()) / 2);break;
            case "flank":formation.flankFormation(this, formation.board.getSIZE() / 2,
                    y + y / 3);
        }
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public Creature getEncouragement() {
        return encouragement;
    }

    public boolean isDeadAll(){
        if(this.encouragement.isAlive())
            return false;
        for(Creature creature: creatures){
            if(creature.isAlive())
                return false;
        }
        return true;
    }

    private void initThreads(){
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(this.encouragement);
        for(Creature creature: creatures){
            exec.execute(creature);
        }
        exec.shutdown();
    }

    public Sides() {
        formation = new Formation(Board.getInstance());
    }

    @Description(todo = "全部死亡清理线程")
    public void allToDie(){
        encouragement.dieRightNow();
        for(Creature creature: creatures){
            creature.dieRightNow();
        }
    }

    @Override
    public void run(){
        initThreads();
        updateStatus();
    }
}
