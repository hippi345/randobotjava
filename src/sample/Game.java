package sample;

import javafx.stage.Stage;
import sample.interfaces.IMoveablePoint;
import sample.models.Bot;
import sample.models.MoveEnum;
import sample.models.Treasure;

// class for the game components
class Game
{

    private int turnCount = 0;
    IMoveablePoint bot;
    Treasure treasure;

    // Game constructor
    Game(int gridSizePassed)
    {
        this.bot = new Bot(gridSizePassed);
        this.treasure = new Treasure();
    }

    // game element initialization
    void InitializeGame(View gameView)
    {
        this.treasure.RandomizeLocation(gameView.gridSize);
        this.bot.RandomizeLocation(gameView.gridSize);
        while(this.bot.equals(this.treasure))
        {
            this.bot.RandomizeLocation(gameView.gridSize);
        }
        // gameView.redrawGrid(this.bot, this.treasure);
    }

    // auto move execution
    void executeAutoMove()
    {
        // make next move
        ++turnCount;
        System.out.println("Current turn: " + turnCount);

        MoveEnum move = this.bot.DetermineMovement();
        this.bot.Move(move);
        treasureDetection(true);
        Main.gameView.redrawGrid(this.bot, this.treasure);
    }

    // actions on completion of the game
    private void completeGame(boolean autoplay)
    {
        System.out.println("you found the treasure!");
        Main.gameView.setupEndScreen(this.turnCount);
        if (autoplay)
            System.exit(69);
    }

    // condition checking for whether the bot is on the treasure location
    private boolean treasureIsFound()
    {
        return this.bot.equals(this.treasure);
    }

    void MoveInDirection(MoveEnum move)
    {
        this.bot.Move(move);
        treasureDetection(false);
        Main.gameView.redrawGrid(this.bot, this.treasure);
    }

    // treasure detection
    private void treasureDetection(boolean autoplay)
    {
        if(treasureIsFound())
            completeGame(autoplay);
    }
}
