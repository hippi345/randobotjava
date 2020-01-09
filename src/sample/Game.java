package sample;

import javafx.scene.layout.GridPane;
import sample.models.Bot;
import sample.models.Treasure;

// class for the game components
class Game
{
    private int turnCount = 0;

    Bot bot;
    Treasure treasure;

    Game()
    {
        this.bot = new Bot(View.getInstance().gridSize);
        this.treasure = new Treasure();
    }

    void InitializeGame() {
        View view = View.getInstance();
        this.treasure.RandomizeLocation(view.gridSize);

        this.bot.RandomizeLocation(view.gridSize);

        while(this.bot.getX() == this.treasure.getX() && this.bot.getY() == this.treasure.getY())
        {
            this.bot.RandomizeLocation(view.gridSize);
        }

        view.adjustBotAndTreasureLocations(this.bot, this.treasure);
    }

    void executeMove() {
        // make next move
        ++turnCount;
        System.out.println("Current turn: " + turnCount);

        this.bot.Move();

        if(treasureIsFound())
            completeGame();

        View.getInstance().adjustBotAndTreasureLocations(this.bot, this.treasure);
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
