package logic.battle;

import logic.creatures.Creature;

import java.util.ArrayList;

public class Formation {
    Board board;
    private ArrayList<Creature> creatures;

    public Formation(Board board) {
        this.board = board;
    }

    //长蛇阵
    void snakeFormation(Sides sides, int beginX, int beginY){
        this.creatures = sides.creatures;
        for(Creature each: creatures){
            if(this.board.isBeyondTheMark(beginX) || this.board.isBeyondTheMark(beginY)){
                each.sayName();
                System.out.println("越界");
                continue;
            }else
                each.moveTo(this.board.getPosition(beginX, beginY));
            beginX++;
        }
    }

    //鹤翼阵
    void flankFormation(Sides sides, int beginX, int beginY){
        this.creatures = sides.creatures;
        int[] loop = new int[]{1, -1};

        int loopIndex = 0;
        int x;
        int y;
        int a = 0;
        for(Creature each: creatures){
            x = beginX;
            y = beginY + a * loop[loopIndex % 2];
            if(this.board.isBeyondTheMark(x) || this.board.isBeyondTheMark(y)){
                each.sayName();
                System.out.println("越界");
            }else
                each.moveTo(this.board.getPosition(x, y));
            if(loopIndex % 2 == 0){
                beginX--;
                a++;
            }
            loopIndex++;
        }
    }

    //雁行阵
    void gooseFormation(Sides sides, int beginX, int beginY){
        this.creatures = sides.creatures;
        int x;
        int y;
        for(Creature each: creatures){
            x = beginX;
            y = beginY;
            if(this.board.isBeyondTheMark(x) || this.board.isBeyondTheMark(y)){
                each.sayName();
                System.out.println("越界");
            }else
                each.moveTo(this.board.getPosition(x, y));
            beginX++;
            beginY--;
        }
    }

    //方阵
    public void matrixFormation(Sides sides, int beginX, int beginY){
        this.creatures = sides.creatures;
        int level = creatures.size() / 2 + 1;
    }
}
