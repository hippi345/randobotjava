package sample;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    // Matt Comment
    // Since x and y are things, you might be better to make them into a class called
    // point or something, so that idea is abstracted up higher.

    // Some of the variables you have in here aren't used at all.  If it isn't used, it's just noise
    // and I'd recommend you remove it.  You can always add it back in when you need it later.

    // Last, I'd write comments above each function as to what they do, just so it's easier for people
    // to come in and be like "reset.. oh yeah okay it resets the game, yeah that makes sense"... looking
    // now, reset could be resetting anything.. placement of something, turns.. no idea.
    private static int botX;
    private static int botY;
    private static int treasureX;
    private static int treasureY;
    private static int turnCount = 0;
    private static boolean botSet = false;
    private static boolean treasureSet = false;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Matt Comment
        // There's 2 variables here that basically have the same name, which on first
        // read is kind of confusing.  I'd simplify it down to just like "getParameters().getRaw()",
        // if someone is reading this and it takes more than 1 read over, it's too confusing.
        final Parameters params = getParameters();
        final List<String> parameters = params.getRaw();
        GridPane gridPane = new GridPane();

        // Matt Comment
        // This section is too long, and there's a lot of repeated code.  Try to break
        // this out into functions somehow to make it easier to read.
        // Like the first 2 are basically doing the same thing, just different because
        // one is 0 args and one is more than 1 arg.  I'm also not sure why you're doing any
        // stage setting work if these are error conditions, it should just be like "error"
        // then bounce.
        if (parameters.size() == 0)
        {
            primaryStage.setX(50);
            primaryStage.setY(50);
            Text tf = new Text("Invalid args: 0 args");
            LOGGER.log(Level.SEVERE, "Invalid args: 0 args");
            gridPane.add(tf,0,0);
            primaryStage.setTitle("Treasure Hunt: Error");
            primaryStage.setScene(new Scene(gridPane, 150, 150));
            primaryStage.show();
            System.exit(1);
        }

        else if (parameters.size() != 1)
        {
            primaryStage.setX(50);
            primaryStage.setY(50);
            Text tf = new Text("Invalid args: more than one arg");
            LOGGER.log(Level.SEVERE, "Invalid args: more than one arg");
            gridPane.add(tf,0,0);
            primaryStage.setTitle("Treasure Hunt: Error");
            primaryStage.setScene(new Scene(gridPane, 100, 100));
            primaryStage.show();
            System.exit(1);
        }

        else
        {
            gridPane.setHgap(30);
            gridPane.setVgap(30);
            primaryStage.setTitle("Treasure Hunt");
            int gridSize = Integer.parseInt(parameters.get(0));
            for (int i = 0; i < gridSize; i++)
            {
                for (int j = 0; j < gridSize; j++)
                {
                    gridPane.add(new Text(i + " " + j + " empty"), i, j);
                }
            }
            // setup the buttons
            Button setBot = new Button("Place bot");
            Button setTreasure = new Button("Place treasure");
            Button nextPlay = new Button("Next move");
            Button reset = new Button("Reset");
            Button autoPlay = new Button("AutoPlay");

            setBot.setOnAction(event -> setBot(gridSize, gridPane, setBot, autoPlay));
            setTreasure.setOnAction(event -> setTreasure(gridSize, gridPane, setTreasure, autoPlay));
            nextPlay.setOnAction(event -> nextPlay(turnCount, gridSize, gridPane));
            reset.setOnAction(event -> reset(setBot, setTreasure, gridPane, gridSize));
            autoPlay.setOnAction(event -> autoPlay(gridSize, gridPane));

            autoPlay.setDisable(true);

            gridPane.add(setBot,1, gridSize+1);
            gridPane.add(setTreasure,1, gridSize+2);
            gridPane.add(nextPlay,1, gridSize+3);
            gridPane.add(reset,1, gridSize+4);
            gridPane.add(autoPlay,1,gridSize+5);

            primaryStage.setScene(new Scene(gridPane, 450, 450));
            primaryStage.show();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    private static void setBot(int gridSize, GridPane gridPane, Button setBot, Button autoplay)
    {
        setBot.setDisable(true);
        botSet = true;
        if (treasureSet)
        {
            autoplay.setDisable(false);
        }
        // place the bot
        botX = (int) Math.round(Math.random()*(gridSize-1));
        botY = (int) Math.round(Math.random()*(gridSize-1));
        System.out.println("Bot X: " + botX);
        System.out.println("Bot Y: " + botY);

        // set the appropriate label
        // Matt Comment
        // If you know the position of the bot, why are you looping
        // through all the nodes to find it?
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text
                    && (GridPane.getColumnIndex(node) == botX
                    && GridPane.getRowIndex(node) == botY))
            {
                ((Text) node).setText(botX + " " + botY + " " + " bot");
            }
        }
    }

    private static void setTreasure(int gridSize, GridPane gridPane, Button setTreasure, Button autoplay)
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

    // TODO
    // this is not working yet but GG on the rest
    // TODO
    // automate the plays without the button through an "automate" button

    // Matt Comment
    // This is a mess and really hard to follow.  I'd suggest moving a lot of this out to different functions if possible.
    // Think about how to simplify the logic, and rethink naming of variables.
    private static void nextPlay(int turns, int gridSize, GridPane gridPane)
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

    private static void reset(Button bot, Button treasure, GridPane gridPane, int gridSize)
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

    private static void treasureNotBot(int gridSize)
    {
        treasureX = (int) Math.round(Math.random()*(gridSize-1));
        treasureY = (int) Math.round(Math.random()*(gridSize-1));
    }

    private static void autoPlay(int gridSize, GridPane gridPane)
    {
        while (true)
        {
            nextPlay(turnCount, gridSize, gridPane);
        }
    }
}
