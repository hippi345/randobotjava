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
    // Joel comment 1/7/2019
    // Let me know your thoughts on whether this is easier to follow

    // Matt 1/7
    // Nitpick, but do we need to have Once in the title of this method?  You can just call it moveBot

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

    void moveBot(int gridSize, GridPane gridPane)
    {
        // make next move
        turnCount++;
        System.out.println("Current turn: " + turnCount);

        // Matt Comment
        // These variable names are confusing, and it isn't giving me a good idea of what they're
        // actually supposed to do.

        // Joel Response 1/7
        // I created a Move class for movement decisions and values and I think it clears up a lot of cloud here
        // I also moved the repetitive code into common methods which are named in a very common sense way
        // Let me know your thoughts

        // implementing the Move class to make the determination of the direction to move
        // much less contrived

        this.bot.Move();

        didWeFindTheTreasure();

        setTheLabelTexts(gridPane);
    }

    private void didWeFindTheTreasure()
    {
        if (this.bot.getX() == this.treasure.getX() && this.bot.getY() == this.treasure.getY())
        {
            System.out.println("you found the treasure!");
            System.exit(69);
        }
        else
            System.out.println("Treasure not found yet :-(");
    }

    private void setTheLabelTexts(GridPane gridPane)
    // if we don't find it then
    // changing the label on the new bot location
    {
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
                ((Text) node).setText(x + " " + y + "empty");
            }
        }
    }
}
