package sample;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import static sample.ValuesForGameRun.*;

// Joel Response 1/7
// Renamed the class to Game and the methods in this class to things more specific to what is occurring

// suppressed infinite loop warning since the warning is annoying to see but it never breaks anything
@SuppressWarnings("InfiniteLoopStatement")
class Game
{
    // Joel comment 1/7/2019
    // Let me know your thoughts on whether this is easier to follow

    static void moveBotOnce(int gridSize, GridPane gridPane)
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
        Move moveToMake = Move.WithRandomValues();

        // condition on whether we move on the x direction or not based on the move object
        if(moveToMake.getMoveXDir())
        {
            // condition on whether we move in the positive direction
            if (moveToMake.getMovePositive())
            {
                // bounds check to make sure we don't go off the grid (lol pun)
                if (!(botX >= (gridSize-1)))
                {
                    // increment the bot positive in the x-direction
                    botX++;
                    // did we find the treasure?
                    // this system exits if evaluates to true in the method
                    didWeFindTheTreasure();

                    // if we don't find it then
                    // changing the label on the new bot location
                    setTheLabelTexts(gridPane);
                }
            }
            else
            {
                // subtraction on x
                if (botX > 0)
                {
                    botX--;
                    // first did we find the treasure?
                    didWeFindTheTreasure();
                    // changing the label on the new bot location
                    setTheLabelTexts(gridPane);
                }
            }
        }
        else
        {
            // y operation
            if (moveToMake.getMovePositive())
            {
                // addition
                if (!(botY >= (gridSize-1)))
                {
                    botY++;
                    // first did we find the treasure?
                    didWeFindTheTreasure();

                    // changing the label on the new bot location
                    setTheLabelTexts(gridPane);
                }
            }
            else
            {
                // subtraction on y
                if (botX > 0)
                {
                    botY--;
                    // first did we find the treasure?
                    didWeFindTheTreasure();

                    // changing the label on the new bot location
                    setTheLabelTexts(gridPane);
                }
            }
        }
    }

    // Joel Note 1/7
    // renamed to something more specific
    static void resetTheGrid(Button bot, Button treasure, GridPane gridPane)
    {
        // set the buttons for setBot and setTreasure to true
        // kind of dumb there is not a setEnable function which is the inverse of setDisable
        bot.setDisable(false);
        treasure.setDisable(false);
        // reset the board
        clearTheLabels(gridPane);
    }

    // method call to run moveBotOnce until game is over with a System.Exit call
    static void autoPlayTheGame(int gridSize, GridPane gridPane)
    {
        while (true)
        {
            moveBotOnce(gridSize, gridPane);
        }
    }

    // checks for auto-play button to become enabled on both the treasure and the bot being set
    static void checkToSetAutoplay()
    {
        if (ValuesForGameRun.getTreasureSet() && ValuesForGameRun.getBotSet())
        {
            Main.autoPlay.setDisable(false);
        }
    }

    private static void didWeFindTheTreasure()
    {
        if (botX == treasureX && botY == treasureY)
        {
            System.out.println("you found the treasure!");
            System.exit(69);
        }
        else
            System.out.println("Treasure not found yet :-(");
    }

    private static void setTheLabelTexts(GridPane gridPane)
    // if we don't find it then
    // changing the label on the new bot location
    {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text
                    && (GridPane.getColumnIndex(node) == botX
                    && GridPane.getRowIndex(node) == botY))
            {
                ((Text) node).setText(botX + " " + botY + " " + " bot");
            }
            else if (node instanceof Text
                    && (GridPane.getColumnIndex(node) == treasureX
                    && GridPane.getRowIndex(node) == treasureY))
            {
                ((Text) node).setText(treasureX + " " + treasureY+ " " + " treasure");
            }
            else if (node instanceof Text)
            {
                String textFromNode = ((Text) node).getText();
                String[] textArrFromNode = textFromNode.split(" ");
                String y = textArrFromNode[0];
                String x = textArrFromNode[1];
                ((Text) node).setText(x + " " + y + " " + " empty");
            }
        }
    }

    // clear the board when reset is clicked
    private static void clearTheLabels(GridPane gridPane)
    {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text)
            {
                int xToSet = GridPane.getRowIndex(node);
                int yToSet = GridPane.getColumnIndex(node);
                ((Text) node).setText(yToSet + " " + xToSet + " " + "empty");
            }
        }
    }
}
