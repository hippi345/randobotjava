package sample;

import javafx.concurrent.Task;
import sample.interfaces.IGame;
import sample.interfaces.IMoveablePoint;
import sample.interfaces.IPoint;
import sample.models.Bot;
import sample.models.GameStatusEnum;
import sample.models.MoveEnum;
import sample.models.Point;
import sample.models.Treasure;

// class for the game components
class Game implements IGame
{
    private int turnCount = 0;
    private GameStatusEnum _status;
    IMoveablePoint bot;
    Treasure treasure;

    // Game constructor
    Game(int gridSizePassed, View gameView)
    {
        _status = GameStatusEnum.NotStarted;
        this.bot = new Bot(gridSizePassed);
        this.treasure = new Treasure();
        InitializeGame(gameView);
    }

    @Override
    public void MakeMove()
    {
        MoveEnum move = this.bot.DetermineMovement();
        MakeMove(move);
    }

    @Override
    public void MakeMove(MoveEnum move)
    {
        executeTurn(move);
    }

    @Override
    public GameStatusEnum GetStatus()
    {
        return _status;
    }

    @Override
    public IPoint getBot()
    {
        return bot;
    }

    @Override
    public IPoint getTreasure()
    {
        return treasure;
    }

    // Private methods
    // game element initialization
    private void InitializeGame(View gameView)
    {
        this.treasure.RandomizeLocation(gameView.gridSize);
        this.bot.RandomizeLocation(gameView.gridSize);
        while(this.bot.equals(this.treasure))
        {
            this.bot.RandomizeLocation(gameView.gridSize);
        }
        gameView.adjustBotAndTreasureLocations(null, this.bot, this.treasure);
    }

    // auto move execution
    private void executeTurn(MoveEnum botMovementDirection)
    {
        // Set status
        if(_status == GameStatusEnum.Complete) return;
        else if(_status == GameStatusEnum.NotStarted)
            _status = GameStatusEnum.InProgress;

        ++turnCount;
        System.out.println("Current turn: " + turnCount);
        IPoint previousBotPoint = new Point(this.bot);
        this.bot.Move(botMovementDirection);

        DetermineCurrentStatus();
        Main.gameView.adjustBotAndTreasureLocations(previousBotPoint, this.bot, this.treasure);
        if (_status == GameStatusEnum.Complete)
        {
            System.out.println("You found the treasure!");
            Task task = new Task<Void>()
            {
                @Override public Void call()
                {
                    Main.startGUI.close();
                    Thread currThreadOutside = Thread.currentThread();
                    System.out.println(currThreadOutside.toString());
                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.start();
            View.setupEndGameGUI();
        }
    }

    // condition checking for whether the bot is on the treasure location
    private boolean treasureIsFound()
    {
        return this.bot.equals(this.treasure);
    }

    private void DetermineCurrentStatus()
    {
        if (treasureIsFound())
        {
            _status = GameStatusEnum.Complete;
        }
    }

    // run moveBot continually with the warning on infinite loops suppressed
    @SuppressWarnings("InfiniteLoopStatement")
    static void RunAutoPlay()
    {
        while(Main.game.GetStatus() != GameStatusEnum.Complete)
        {
            Main.game.MakeMove();
        }
    }

    // sets up the Game object with bot and treasure objects in place with coordinates on the grid
    static void prepareGame() {
        Main.game = new Game(Main.gridSizeForGame, Main.gameView);
    }
}
