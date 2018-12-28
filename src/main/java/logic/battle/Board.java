package logic.battle;

import javafx.scene.image.Image;
import logic.creatures.Creature;
import logic.position.Position;

public class Board {

    private static Board instance = new Board();
    public static Board getInstance(){
        return instance;
    }

    private final int SIZE = 11;

    private Position<Creature>[][] board;
    private Board(){
        this.board = new Position[SIZE][SIZE];
        for(int i = 0;i < SIZE;i++){
            for(int j = 0; j < SIZE; j++){
                this.board[i][j] = new Position<>(i, j, null);
            }
        }
    }

    Position getPosition(int x, int y){
        return this.board[x][y];
    }

    public int getSIZE() {
        return SIZE;
    }

    public boolean isBeyondTheMark(int x){
        return x < 0 || x > this.SIZE - 1;
    }

    public void printBoard(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                board[i][j].printPosition();
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public Position<Creature>get(int x,int y){
        return board[x][y];
    }

    public Image getMyImage(int i, int j){
        return this.board[i][j].getMyImage();
    }
}
