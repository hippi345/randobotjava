package sample;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sample.models.Bot;
import sample.models.Treasure;

// class for the game components
class Game
{
    private static int turnCount = 0;

    private Bot bot;
    private Treasure treasure;

    Game(int gridSize)
    {
        this.bot = new Bot(gridSize);
        this.treasure = new Treasure();
    }

    void InitializeGame(int gridSize, GridPane gridPane)
    {
        this.treasure.RandomizeLocation(gridSize);

        this.bot.RandomizeLocation(gridSize);

        while(this.treasure.getX() == this.bot.getX() && this.treasure.getY() == this.bot.getY())
        {
            this.bot.RandomizeLocation(gridSize);
        }

        setTheLabelTexts(gridPane);
    }

    void executeMove(GridPane gridPane)
    {
        // make next move
        ++turnCount;
        System.out.println("Current turn: " + turnCount);

        this.bot.Move();

        if(treasureIsFound())
            completeGame();

        setTheLabelTexts(gridPane);
    }

    private void completeGame()
    {
        System.out.println("you found the treasure!");
        System.exit(69);
    }

    private boolean treasureIsFound()
    {
        return this.bot.getX() == this.treasure.getX() && this.bot.getY() == this.treasure.getY();
    }

    private void setTheLabelTexts(GridPane gridPane)
    {
        for (Node node : gridPane.getChildren()) {
            int currentColumnIndex = GridPane.getColumnIndex(node);
            int currentRowIndex = GridPane.getRowIndex(node);

            if(node instanceof Text)
            {
                if(this.bot.getX() == currentColumnIndex && this.bot.getY() == currentRowIndex)
                {
                    ((Text) node).setText(this.bot.getX() + " " + this.bot.getY() + " " + " bot");
                }
                else if(this.treasure.getX() == currentColumnIndex && this.treasure.getY() == currentRowIndex)
                {
                    ((Text) node).setText(this.treasure.getX() + " " + this.treasure.getY() + " " + " treasure");
                }
                else
                {
                    ((Text) node).setText(currentColumnIndex + " " + currentRowIndex + " empty");
                }
            }
        }
    }
}
