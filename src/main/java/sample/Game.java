package sample;

import sample.interfaces.IGame;
import sample.interfaces.IMoveablePoint;
import sample.interfaces.IPoint;
import sample.models.Bot;
import sample.models.GameStatusEnum;
import sample.models.MoveEnum;
import sample.models.Point;
import sample.models.Treasure;

class Game implements IGame {
    private int turnCount = 0;
    private GameStatusEnum status;
    private final int gridSize;
    private final GameBoardListener boardListener;
    IMoveablePoint bot;
    Treasure treasure;

    Game(int gridSize, GameBoardListener boardListener) {
        this.gridSize = gridSize;
        this.boardListener = boardListener;
        status = GameStatusEnum.NotStarted;
        this.bot = new Bot(gridSize);
        this.treasure = new Treasure();
        initializePositions();
    }

    @Override
    public void makeMove() {
        makeMove(this.bot.determineMovement());
    }

    @Override
    public void makeMove(MoveEnum move) {
        executeTurn(move);
    }

    @Override
    public GameStatusEnum getStatus() {
        return status;
    }

    @Override
    public IPoint getBot() {
        return bot;
    }

    @Override
    public IPoint getTreasure() {
        return treasure;
    }

    int getGridSize() {
        return gridSize;
    }

    void initializePositions() {
        this.treasure.randomizeLocation(gridSize);
        this.bot.randomizeLocation(gridSize);
        while (this.bot.equals(this.treasure)) {
            this.bot.randomizeLocation(gridSize);
        }
        boardListener.onPositionsChanged(null, this.bot, this.treasure);
    }

    private void executeTurn(MoveEnum botMovementDirection) {
        if (status == GameStatusEnum.Complete) {
            return;
        } else if (status == GameStatusEnum.NotStarted) {
            status = GameStatusEnum.InProgress;
        }

        ++turnCount;
        System.out.println("Current turn: " + turnCount);

        IPoint previousBotPoint = new Point(this.bot);
        this.bot.move(botMovementDirection);

        determineCurrentStatus();
        boardListener.onPositionsChanged(previousBotPoint, this.bot, this.treasure);
        if (status == GameStatusEnum.Complete) {
            System.out.println("You found the treasure!");
            boardListener.onGameComplete();
        }
    }

    private boolean treasureIsFound() {
        return this.bot.equals(this.treasure);
    }

    private void determineCurrentStatus() {
        if (treasureIsFound()) {
            status = GameStatusEnum.Complete;
        }
    }
}
