package sample.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BotTest {

    @Test
    void singleCellGridStaysPut() {
        Bot bot = new Bot(1);
        assertEquals(MoveEnum.Stay, bot.determineMovement());
    }

    @Test
    void determinedMovesNeverLeaveTheGrid() {
        Bot bot = new Bot(3);
        for (int i = 0; i < 500; i++) {
            bot.move(bot.determineMovement());
            assertTrue(bot.getX() >= 0 && bot.getX() < 3);
            assertTrue(bot.getY() >= 0 && bot.getY() < 3);
        }
    }

    @Test
    void prefersTheOnlyUnvisitedNeighbor() {
        Bot bot = new Bot(2);
        bot.relocate(0, 0);
        bot.move(MoveEnum.Right);
        bot.move(MoveEnum.Left);
        for (int i = 0; i < 50; i++) {
            assertEquals(MoveEnum.Down, bot.determineMovement());
        }
    }
}
