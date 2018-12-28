/*
 * @class name:test
 * @author:Wu Gang
 * @create: 2018-12-28 16:49
 * @description:
 */

import logic.battle.Board;
import org.junit.Test;

public class MyTest {
    @Test
    public void test() {
        Board board1 = Board.getInstance();
        Board board2 = Board.getInstance();
        assert board1 == board2;
    }
}
