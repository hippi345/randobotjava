package sample;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import static sample.Values.*;

// Joel Note 1/7
/*
    Cleaned this up a bit and modularized out the button enabling
    Put that in the Player class as it seemed most fitting for a location
    Also tried to make the code look cleaner so happy for feedback on that as well for this class
 */

class ValueSetters
{
    static void setBot(int gridSize, GridPane gridPane, Button setBot)
    {
        setBot.setDisable(true);
        Values.setBotSet();

        // checks for whether to enable auto-play in the Player class for better modularization
        Game.checkToSetAutoplay();

        // place the bot
        Values.setBotX((int) Math.round(Math.random()*(gridSize-1)));
        Values.setBotY((int) Math.round(Math.random()*(gridSize-1)));
        System.out.println("Bot X: " + Values.getBotX());
        System.out.println("Bot Y: " + Values.getBotY());

        // TODO
        // Research if alternative exists to parsing each node
        // I don't think there is as I also found the same solution at
        // https://stackoverflow.com/questions/57515339/javafx-how-to-locate-a-specific-button-in-a-gridpane
        // there is a method GridPane.getChildren().get(int i) but it only works for a one dimensional index which
        // is really really dumb that there is not an overload for 2D that would essentially use the getColumnIndex
        // and getRowIndex in the background. smh...

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text
                    && (GridPane.getColumnIndex(node) == Values.getBotX()
                    && GridPane.getRowIndex(node) == botY))
            {
                ((Text) node).setText(botX + " " + botY + " " + " bot");
            }
        }
    }

    static void setTreasure(int gridSize, GridPane gridPane, Button setTreasure)
    {
        treasureSet = true;

        // checks for auto-play to be enabled or not
        Game.checkToSetAutoplay();
        // disable the button on placement
        setTreasure.setDisable(true);

        // this assigns values to treasure location but checks those against the bot location as we do not want them
        // to be the same
        // TODO there may a smarter way to do this. Thoughts?
        // basically the while loop always makes me worry so open to alternatives

        getTreasureCoordinates(gridSize);
        while ((treasureX == botX) && (treasureY == botY))
        {
            getTreasureCoordinates(gridSize);
        }
        System.out.println("Treasure X: " + treasureX);
        System.out.println("Treasure Y: " + treasureY);

        // set the appropriate label for the treasure
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text
                    && (GridPane.getColumnIndex(node) == treasureX
                    && GridPane.getRowIndex(node) == treasureY))
            {
                ((Text) node).setText(treasureX + " " + treasureY + " " + " treasure");
            }
        }
    }

    // determines the location of the treasure
    private static void getTreasureCoordinates(int gridSize)
    {
        treasureX = (int) Math.round(Math.random()*(gridSize-1));
        treasureY = (int) Math.round(Math.random()*(gridSize-1));
    }
}
