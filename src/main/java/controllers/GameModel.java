package controllers;

import annotation.Description;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import logic.battle.Board;
import logic.battle.EvilSide;
import logic.battle.JustSide;
import logic.creatures.Bullet;
import logic.creatures.Creature;
import util.ThreadInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameModel implements Runnable{
    enum GameStatus{
        going, justWin, evilWin
    }

    private static GameModel gameModel;
    private GameStatus gameStatus;//游戏状态
    private Board board = Board.getInstance();
    private ExecutorService exec = Executors.newCachedThreadPool();

    private List<Bullet> bullets = new ArrayList<>();

    private Canvas canvas;

    public static GameModel getInstance() {
        if (gameModel != null)
            return gameModel;
        else {
            return null;
//            throw new Exception("GameModel还没有初始化好");
        }
    }

    GameModel(Canvas canvas) {
        Creature.setBoard(this.board);
        gameStatus = GameStatus.going;
        this.canvas = canvas;
        gameModel = this;
    }

    public void restart(){
        clearThreads();
        gameModel = new GameModel(canvas);
        JustSide.getInstance().restart();
        EvilSide.getInstance().restart();
    }

    @Description(todo = "得到战场")
    public Board getBoard() {
        return board;
    }


    @Description(todo = "线程分配")
    private void initThreads(){
        exec.execute(JustSide.getInstance());
        exec.execute(EvilSide.getInstance());
    }


    @Description(todo = "战场的的显示内容")
    private void displayBoard(){
        double width = this.canvas.getWidth();
        double height = this.canvas.getHeight();
        int n = this.board.getSIZE();

        double boardWidth = height * 6 / 7;
        double startLayoutX = (width - height) / 2 + 70;
        double startLayoutY = (height - boardWidth) / 2;
        double creatureSize = boardWidth / n;

        Image image = new Image("map.jpg");
        synchronized (board) {
            this.canvas.getGraphicsContext2D().drawImage(image, 0, 0, image.getWidth(), image.getHeight());
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    double x = startLayoutX + i * creatureSize;
                    double y = startLayoutY + j * creatureSize;
                    if (!(this.board.get(j, i).isEmpty())) {
                        this.board.get(j, i).getCreature().draw(this.canvas.getGraphicsContext2D(), x, y, creatureSize);
                    }
                }
            }
        }
        synchronized (bullets) {
            for (Bullet bullet : bullets) {
                bullet.draw(this.canvas.getGraphicsContext2D(), startLayoutX, startLayoutY, creatureSize);
            }
        }
    }

    @Override
    public void run() {
        initThreads();
        new Thread(() -> {
            int i = 0;
            Image image = new Image("win.png");
            while (true){
                if(JustSide.getInstance().isDeadAll()){
                    gameStatus = GameStatus.evilWin;
                    System.out.println("妖怪赢了");
                    image = new Image("fail.png");
                    break;
                }else if(EvilSide.getInstance().isDeadAll()){
                    gameStatus = GameStatus.justWin;
                    System.out.println("葫芦娃赢了");
                    break;
                }
                try {
                    i++;
                    System.out.println("第" + i + "次");
                    synchronized (GameModel.class){
                        GameModel.class.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            displayBoard();
            Image finalImage = image;
            Platform.runLater(()->{
                GameController.playBox(finalImage);
            });
            System.out.println("Game Over");
            clearThreads();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ThreadInfo.threadPrint();
        }).start();
        new Thread(()->{
            while (gameStatus == GameStatus.going){
                Platform.runLater(this::displayBoard);
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Description(todo = "线程清理")
    private void clearThreads(){
        JustSide.getInstance().allToDie();
        EvilSide.getInstance().allToDie();
        for(Bullet bullet: bullets){
            bullet.fade();
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public boolean isHitBullet(int x, int y){
        synchronized (bullets){
            for(Bullet bullet: bullets){
                if(bullet.getX() == x && bullet.getY() == y){
                    return true;
                }
            }
        }
        return false;
    }
}
