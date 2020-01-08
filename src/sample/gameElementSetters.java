package sample;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import static sample.ValuesForGameRun.*;

// Joel Note 1/7
/*
    Cleaned this up a bit and modularized out the button enabling
    Put that in the Player class as it seemed most fitting for a location
    Also tried to make the code look cleaner so happy for feedback on that as well for this class
 */

class gameElementSetters
{
    // making the bot point static and private so the comparison with treasure looks
    // cleaner with mutual use of their getter functions for x and y in Point class
    private static Point botPoint;

    static void setBot(int gridSize, GridPane gridPane, Button setBot)
    {
        // disable the set bot button on setting the bot location (until the reset button is clicked)
        setBot.setDisable(true);
        ValuesForGameRun.botIsSet();

        // checks for whether to enable auto-play in the Player class for better modularization
        Game.checkToSetAutoplay();

        // Joel Note: implementation of the Point class to abstract away the calculation of coordinates
        botPoint = Point.WithRandomValues(gridSize);

        // place the bot
        ValuesForGameRun.setBotX(botPoint.getX());
        ValuesForGameRun.setBotY(botPoint.getY());
        System.out.println("Bot X: " + ValuesForGameRun.getBotX());
        System.out.println("Bot Y: " + ValuesForGameRun.getBotY());

        // changes the text of the label at the bot location to text indicating the bot is there
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text
                    && (GridPane.getColumnIndex(node) == ValuesForGameRun.getBotX()
                    && GridPane.getRowIndex(node) == botPoint.getX()))
            {
                ((Text) node).setText(botPoint.getX() + " " + botPoint.getY() + " " + " bot");
            }
        }
    }

    // method for settings the treasure and changing the label on the coordinate where the treasure exists
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

        // make use of the Point class to abstract away the point calculation
        Point treasurePoint = Point.WithRandomValues(gridSize);
        while (treasurePoint.getX() == botPoint.getX() && (treasurePoint.getY() == botPoint.getY()))
        {
            treasurePoint = Point.WithRandomValues(gridSize);
        }
        System.out.println("Treasure X: " + treasurePoint.getX());
        System.out.println("Treasure Y: " + treasurePoint.getY());

        // set the appropriate label for the treasure
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text
                    && (GridPane.getColumnIndex(node) == treasurePoint.getX()
                    && GridPane.getRowIndex(node) == treasurePoint.getX()))
            {
                ((Text) node).setText(treasurePoint.getX() + " " + treasurePoint.getY() + " " + " treasure");
            }
        }
    }
}
