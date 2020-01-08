package sample;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sample.models.Bot;
import sample.models.Treasure;

// Joel Response 1/7
// Renamed the class to Game and the methods in this class to things more specific to what is occurring

// suppressed infinite loop warning since the warning is annoying to see but it never breaks anything
@SuppressWarnings("InfiniteLoopStatement")
class Game
{
    private static int turnCount = 0;

    private Bot bot;
    private Treasure treasure;

    public Game(int gridSize)
    {
        this.bot = new Bot(gridSize);
        this.treasure = new Treasure();
    }

    public void InitializeGame(int gridSize, GridPane gridPane)
    {
        this.treasure.RandomizeLocation(gridSize);

        this.bot.RandomizeLocation(gridSize);

        while(this.treasure.getX() == this.bot.getX() && this.treasure.getY() == this.bot.getY())
        {
            this.bot.RandomizeLocation(gridSize);
        }

        setTheLabelTexts(gridPane);
    }

    void moveBot(GridPane gridPane)
    {
        // make next move
        ++turnCount;
        System.out.println("Current turn: " + turnCount);

        this.bot.Move();

        if(treasureIsFound())
            completeGame();
        else
            System.out.println("Treasure not found yet :-(");

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
        // TODO: these loops are choking out the game we have to figure out something better for this

        for (Node node : gridPane.getChildren()) {
            if(node instanceof Button) continue;

            if (node instanceof Text
                    && (GridPane.getColumnIndex(node) == this.bot.getX()
                    && GridPane.getRowIndex(node) == this.bot.getY()))
            {
                ((Text) node).setText(this.bot.getX() + " " + this.bot.getY() + " " + " bot");
            }
            else if (node instanceof Text
                    && (GridPane.getColumnIndex(node) == this.treasure.getX()
                    && GridPane.getRowIndex(node) == this.treasure.getY()))
            {
                ((Text) node).setText(this.treasure.getX() + " " + this.treasure.getY() + " " + " treasure");
            }
            else
            {
                int y = GridPane.getRowIndex(node);
                int x = GridPane.getColumnIndex(node);
                ((Text) node).setText(x + " " + y + " empty");
            }
        }
    }
}
