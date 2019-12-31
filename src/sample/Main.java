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
    private static int botX = 0;
    private static int botY = 0;
    private static int treasureX = 0;
    private static int treasureY = 0;
    private static int turnCount = 0;
    private static boolean autoPlay = false;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        final Parameters params = getParameters();
        final List<String> parameters = params.getRaw();
        GridPane gridPane = new GridPane();

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

        else if (parameters.size() > 1)
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

        else if (parameters.size() == 1)
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

            setBot.setOnAction(event -> setBot(gridSize, gridPane, setBot));
            setTreasure.setOnAction(event -> setTreasure(gridSize, gridPane, setTreasure));
            nextPlay.setOnAction(event -> nextPlay(turnCount, gridSize, gridPane));
            reset.setOnAction(event -> reset(setBot, setTreasure, gridPane, gridSize));
            autoPlay.setOnAction(event -> autoPlay(gridSize, gridPane));

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

    private static void setBot(int gridSize, GridPane gridPane, Button setBot)
    {
        setBot.setDisable(true);
        // place the bot
        botX = (int) Math.round(Math.random()*(gridSize-1));
        botY = (int) Math.round(Math.random()*(gridSize-1));
        System.out.println("Bot X: " + botX);
        System.out.println("Bot Y: " + botY);

        // set the appropriate label
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text
                    && (GridPane.getColumnIndex(node) == botX
                    && GridPane.getRowIndex(node) == botY))
            {
                ((Text) node).setText(botX + " " + botY + " " + " bot");
            }
        }
    }

    private static void setTreasure(int gridSize, GridPane gridPane, Button setTreasure)
    {
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
    private static void nextPlay(int turns, int gridSize, GridPane gridPane)
    {
        // make next move
        turnCount++;
        System.out.println("Current turn: " + turnCount);

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
