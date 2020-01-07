package sample;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import static sample.ValuesForGameRun.*;

// Joel Response 1/7
// Renamed the class to Game and the methods in this class to things more specific to what is occurring
@SuppressWarnings("InfiniteLoopStatement")
class Game
{
    // Matt Comment
    // This is a mess and really hard to follow.  I'd suggest moving a lot of this out to different functions if possible.
    // Think about how to simplify the logic, and rethink naming of variables.

    // Joel Response 1/7
    // I am going to focus on refactoring this one out later tonight (1/7/2019) as it is a tall order
    // but I wanted to push the things I have done thus far
    static void moveBotOnce(int turns, int gridSize, GridPane gridPane)
    {
        // make next move
        turnCount++;
        System.out.println("Current turn: " + turnCount);

        // Matt Comment
        // These variable names are confusing, and it isn't giving me a good idea of what they're
        // actually supposed to do.
        int xOrY = (int) Math.round(Math.random()*2);
        int addOrSubtract = (int) Math.round(Math.random()*2);

        if (xOrY > 1)
        {
            // x operation
            if (addOrSubtract > 1)
            {
                // addition
                if (!(botX >= (gridSize-1)))
                {
                    botX++;
                    // first did we find the treasure?
                    if (botX == treasureX && botY == treasureY)
                    {
                        System.out.println("you found the treasure!");
                        System.exit(69);
                    }
                    else
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
                }
            }
            else
            {
                // subtraction on x
                if (botX > 0)
                {
                    botX--;
                    // first did we find the treasure?
                    if (botX == treasureX && botY == treasureY)
                    {
                        System.out.println("you found the treasure!");
                        System.exit(69);
                    }
                    else
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
                }
            }
        }
        else
        {
            // y operation
            if (addOrSubtract > 1)
            {
                // addition
                if (!(botY >= (gridSize-1)))
                {
                    botY++;
                    // first did we find the treasure?
                    if (botX == treasureX && botY == treasureY)
                    {
                        System.out.println("you found the treasure!");
                        System.exit(69);
                    }
                    else
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
                }
            }
            else
            {
                // subtraction on y
                if (botX > 0)
                {
                    botY--;
                    // first did we find the treasure?
                    if (botX == treasureX && botY == treasureY)
                    {
                        System.out.println("you found the treasure!");
                        System.exit(69);
                    }
                    else
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
                }
            }
        }
    }

    // resets the board by setting all the labels back to empty and enabling the set buttons again
    // did a code review on this one to make sure it was not a mess and the only thing I could think of is
    // potentially line 215 (or there abouts) ((Text) node).setText(yToSet + " " + xToSet + " " + "empty");
    // is a little messy
    // TODO see into how to clean up that line

    // Joel Note 1/7
    // renamed to something more specific
    static void resetTheGrid(Button bot, Button treasure, GridPane gridPane)
    {
        bot.setDisable(false);
        treasure.setDisable(false);
        // reset the board
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text)
            {
                int xToSet = GridPane.getRowIndex(node);
                int yToSet = GridPane.getColumnIndex(node);
                ((Text) node).setText(yToSet + " " + xToSet + " " + "empty");
            }
        }
    }

    // method call to run moveBotOnce until game is over with a System.Exit call
    static void autoPlayTheGame(int gridSize, GridPane gridPane)
    {
        do {
            moveBotOnce(turnCount, gridSize, gridPane);
        } while (true);
    }

    // checks for auto-play button to become enabled on both the treasure and the bot being set
    static void checkToSetAutoplay()
    {
        if (ValuesForGameRun.getTreasureSet() && ValuesForGameRun.getBotSet())
        {
            Main.autoPlay.setDisable(false);
        }
    }
}
