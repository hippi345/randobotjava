package sample;

import javafx.scene.layout.GridPane;
import sample.models.Bot;
import sample.models.Treasure;

// class for the game components
class Game
{
    private static int turnCount = 0;

    Bot bot;
    Treasure treasure;

    Game(int gridSize)
    {
        this.bot = new Bot(gridSize);
        this.treasure = new Treasure();
    }

    void InitializeGame(int gridSize, GridPane gridPane, View gameView) throws InterruptedException {
        this.treasure.RandomizeLocation(gridSize);

        this.bot.RandomizeLocation(gridSize);

        while(this.bot.getX() == this.treasure.getX() && this.bot.getY() == this.treasure.getY())
        {
            this.bot.RandomizeLocation(gridSize);
        }

        gameView.setTheLabelTexts(gridPane, this.bot, this.treasure);
    }

    void executeMove(GridPane gridPane, View gameView) throws InterruptedException {
        // make next move
        ++turnCount;
        System.out.println("Current turn: " + turnCount);

        this.bot.Move();

        if(treasureIsFound())
            completeGame();

        gameView.setTheLabelTexts(gridPane, this.bot, this.treasure);
    }

    private static void completeGame()
    {
        System.out.println("you found the treasure!");
        System.exit(69);
    }

    private boolean treasureIsFound()
    {
        return this.bot.getX() == this.treasure.getX() && this.bot.getY() == this.treasure.getY();
    }
}
