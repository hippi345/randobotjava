package sample;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import sample.models.Bot;
import sample.models.GameStatusEnum;
import sample.models.MoveEnum;
import sample.models.Treasure;

class GameTest {

    @Test
    void firstMoveSetsStatusToInProgress() {
        RecordingListener listener = new RecordingListener();
        Game game = new Game(5, listener);
        Bot bot = (Bot) game.getBot();
        bot.relocate(2, 2);
        assertEquals(GameStatusEnum.NotStarted, game.getStatus());
        game.makeMove(MoveEnum.Up);
        assertEquals(GameStatusEnum.InProgress, game.getStatus());
    }

    @Test
    void movingOntoTreasureCompletesGame() {
        RecordingListener listener = new RecordingListener();
        Game game = new Game(5, listener);
        Bot bot = (Bot) game.getBot();
        Treasure treasure = (Treasure) game.getTreasure();
        bot.relocate(0, 0);
        treasure.relocate(1, 0);
        listener.reset();

        game.makeMove(MoveEnum.Right);

        assertEquals(GameStatusEnum.Complete, game.getStatus());
        assertEquals(1, listener.updateCount);
    }

    private static final class RecordingListener implements GameBoardListener {
        int updateCount;

        @Override
        public void onPositionsChanged(
                sample.interfaces.IPoint previousBotPosition,
                sample.interfaces.IPoint currentBotPosition,
                sample.interfaces.IPoint treasurePosition) {
            updateCount++;
        }

        void reset() {
            updateCount = 0;
        }
    }
}
