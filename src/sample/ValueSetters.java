package sample;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import static sample.Values.*;
import static sample.Values.treasureY;

class ValueSetters
{
    static void setBot(int gridSize, GridPane gridPane, Button setBot, Button autoplay)
    {
        setBot.setDisable(true);
        Values.setBotSet();
        if (Values.getTreasureSet())
        {
            autoplay.setDisable(false);
        }
        // place the bot
        Values.setBotX((int) Math.round(Math.random()*(gridSize-1)));
        Values.setBotY((int) Math.round(Math.random()*(gridSize-1)));
        System.out.println("Bot X: " + Values.getBotX());
        System.out.println("Bot Y: " + Values.getBotY());

        // Matt Comment
        // If you know the position of the bot, why are you looping
        // through all the nodes to find it?

        // TODO
        // Joel Comment:
        // https://stackoverflow.com/questions/20825935/javafx-get-node-by-row-and-column
        // this is what they suggested on Stackyflow
        // I definitely wanted to just fetch it and assign it
        // if you see something else more efficient then I curious for sure lol

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text
                    && (GridPane.getColumnIndex(node) == Values.getBotX()
                    && GridPane.getRowIndex(node) == botY))
            {
                ((Text) node).setText(botX + " " + botY + " " + " bot");
            }
        }
    }

    static void setTreasure(int gridSize, GridPane gridPane, Button setTreasure, Button autoplay)
    {
        treasureSet = true;
        if (botSet)
        {
            autoplay.setDisable(false);
        }
        // place the treasure
        setTreasure.setDisable(true);

        treasureNotBot(gridSize);
        while ((treasureX == botX) && (treasureY == botY))
        {
            treasureNotBot(gridSize);
        }
        System.out.println("Treasure X: " + treasureX);
        System.out.println("Treasure Y: " + treasureY);

        // set the appropriate label
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text
                    && (GridPane.getColumnIndex(node) == treasureX
                    && GridPane.getRowIndex(node) == treasureY))
            {
                ((Text) node).setText(treasureX + " " + treasureY + " " + " treasure");
            }
        }
    }

    private static void treasureNotBot(int gridSize)
    {
        treasureX = (int) Math.round(Math.random()*(gridSize-1));
        treasureY = (int) Math.round(Math.random()*(gridSize-1));
    }
}
