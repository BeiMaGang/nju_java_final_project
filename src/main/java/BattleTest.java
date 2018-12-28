import logic.battle.*;
import logic.creatures.Creature;

public class BattleTest {
    public static void main(String argv[]){
        /* n * n 棋盘 */
        Board board = Board.getInstance();
        Creature.setBoard(board);
        Sides.formation = new Formation(board);

        JustSide justSide = JustSide.getInstance();
        EvilSide evilSide = EvilSide.getInstance();

        /*葫芦娃摆出蛇阵*/
        justSide.changeFormation("snake");

        /*妖精摆出大雁阵*/
        evilSide.changeFormation("goose");
        board.printBoard();
        System.out.println();

        /*妖精摆出鹤翼阵*/
        evilSide.changeFormation("flank");
        board.printBoard();

        System.out.println(board.getClass().getResource("/resources/DaWa.png"));
    }
}
