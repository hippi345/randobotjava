package sample;

import sample.interfaces.IGame;
import sample.models.Bot;
import sample.models.MoveEnum;
import sample.models.Treasure;

// class for the game components
class Game implements IGame
{

    private int turnCount = 0;
    Bot bot;
    Treasure treasure;

    // Game constructor
    Game(int gridSizePassed, View gameView)
    {
        this.bot = new Bot(gridSizePassed);
        this.treasure = new Treasure();
        InitializeGame(gameView);
    }

    // game element initialization
    private void InitializeGame(View gameView)
    {
        this.treasure.RandomizeLocation(gameView.gridSize);
        this.bot.RandomizeLocation(gameView.gridSize);
        while(this.bot.getX() == this.treasure.getX() && this.bot.getY() == this.treasure.getY())
        {
            this.bot.RandomizeLocation(gameView.gridSize);
        }
        gameView.adjustBotAndTreasureLocations(this.bot, this.treasure);
    }

    /*void InitializeGame()
    {
        View view = View.getInstance();
        this.treasure.RandomizeLocation(view.gridSize);
        this.bot.RandomizeLocation(view.gridSize);
        while(this.bot.getX() == this.treasure.getX() && this.bot.getY() == this.treasure.getY())
            {
                this.bot.RandomizeLocation(view.gridSize);
            }
        view.adjustBotAndTreasureLocations(this.bot, this.treasure);
    }*/

    // auto move execution
    public void Move()
    {
        this.Move(MoveEnum.None);
    }

    public void Move(MoveEnum requestedMove)
    {
        executeMove(requestedMove);
    }

    public Bot getBot()
    {
        return this.bot;
    }

    public Treasure getTreasure()
    {
        return this.treasure;
    }

    private void executeMove(MoveEnum requestedMove)
    {
        // make next move
        ++turnCount;
        System.out.println("Current turn: " + turnCount);

        if (requestedMove == MoveEnum.None)
        {
            this.bot.MoveRandomly();
        }
        else
        {
            this.bot.Move(requestedMove);
        }

        if(treasureIsFound())
            completeGame();

        Main.gameView.adjustBotAndTreasureLocations(this.bot, this.treasure);
    }

    // actions on completion of the game
    private static void completeGame()
    {
        System.out.println("you found the treasure!");
        System.exit(69);
    }

    // condition checking for whether the bot is on the treasure location
    private boolean treasureIsFound()
    {
        return this.bot.getX() == this.treasure.getX() && this.bot.getY() == this.treasure.getY();
    }
}
