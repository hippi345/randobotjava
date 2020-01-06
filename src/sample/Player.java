package sample;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import static sample.Values.*;

// Matt Comment: 1/6
// This feels like a misnomer, I feel like these are things the GAME is doing, not the
// player.  Like yes, the player is the one pushing the button, but the game is the one
// actually executing the logic of "what does it mean to execute the next play?" or
// "What work do I need to do to reset?"
class Player
{
    // Matt Comment
    // This is a mess and really hard to follow.  I'd suggest moving a lot of this out to different functions if possible.
    // Think about how to simplify the logic, and rethink naming of variables.
    static void nextPlay(int turns, int gridSize, GridPane gridPane)
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

    static void reset(Button bot, Button treasure, GridPane gridPane, int gridSize)
    {
        bot.setDisable(false);
        treasure.setDisable(false);
        // reset the board
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text)
            {
                int xToSet = GridPane.getRowIndex(((Text) node));
                int yToSet = GridPane.getColumnIndex((Text) node);
                ((Text) node).setText(yToSet + " " + xToSet + " " + "empty");
            }
        }
    }

    static void autoPlay(int gridSize, GridPane gridPane)
    {
        while (true)
        {
            nextPlay(turnCount, gridSize, gridPane);
        }
    }
}
