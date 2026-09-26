package sample.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class PointTest {

    @Test
    void equalsAndHashCodeUseCoordinates() {
        Point a = new Point(2, 3);
        Point b = new Point(2, 3);
        Point c = new Point(2, 4);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    @Test
    void moveInDirectionUpdatesCoordinates() {
        Bot bot = new Bot(5);
        bot.relocate(2, 2);
        bot.move(MoveEnum.Up);
        assertEquals(2, bot.getX());
        assertEquals(1, bot.getY());

        bot.move(MoveEnum.Right);
        assertEquals(3, bot.getX());
        assertEquals(1, bot.getY());
    }

    @Test
    void botRespectsGridBoundaries() {
        Bot bot = new Bot(3);
        bot.relocate(0, 0);
        bot.move(MoveEnum.Left);
        assertEquals(0, bot.getX());
        bot.move(MoveEnum.Up);
        assertEquals(0, bot.getY());

        bot.relocate(2, 2);
        bot.move(MoveEnum.Right);
        assertEquals(2, bot.getX());
        bot.move(MoveEnum.Down);
        assertEquals(2, bot.getY());
    }
}
