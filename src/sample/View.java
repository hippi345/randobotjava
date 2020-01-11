package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sample.models.Point;
import java.util.function.Consumer;

public class View
{
    private static View instance = null;
    GridPane gridPane;
    int gridSize = Constants.DEFAULT_GRIDSIZE;

    // Constructor must be private for the Singleton pattern.
    View()
    {
        this.gridPane = new GridPane();
    }

    View(int gridSize)
    {
        this.gridPane = new GridPane();
        this.gridSize = gridSize;
    }

    static View getInstance()
    {
        if(instance == null)
        {
            instance = new View();
        }

        return instance;
    }

    private void setGridSize(int gridSize)
    {
        this.gridSize = gridSize;
    }

    void setupTheGridPane() {
        this.gridPane.setHgap(8);
        this.gridPane.setVgap(8);
        setupGUI();
    }

    // sets up the labels from the grid size passed into the command line
    private void setupGUI()
    {
        for (int i = 0; i < this.gridSize; i++)
        {
            for (int j = 0; j < this.gridSize; j++)
            {
                this.gridPane.add(new Text(""), i, j);
            }
        }
    }

    // setting up the start screen
    void startScreen(Stage startGUI)
    {
        this.gridPane.setHgap(8);
        this.gridPane.setVgap(8);

        Text handle = new Text("Welcome to the Random Bot Game!");
        handle.setTextAlignment(TextAlignment.CENTER);
        Text argInquire = new Text("What is the Grid size?");
        argInquire.setTextAlignment(TextAlignment.CENTER);
        Button submitStart = new Button("Go!");
        submitStart.setTextAlignment(TextAlignment.CENTER);
        CheckBox defaultBehaviors = new CheckBox("Defaults for the application");
        defaultBehaviors.setTextAlignment(TextAlignment.CENTER);
        TextField gridSizeStart = new TextField();

            submitStart.setOnAction(actionEvent -> prepTheGame(argInquire, defaultBehaviors,
                    gridSizeStart, startGUI));
        defaultBehaviors.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                gridSizeStart.setText("");
            }
        });

        this.gridPane.add(handle,0,0);
        this.gridPane.add(argInquire,0, 1);
        this.gridPane.add(gridSizeStart,0,2);
        this.gridPane.add(defaultBehaviors,1,2);
        this.gridPane.add(submitStart,0,3);
    }

    private void prepTheGame(Text helpText, CheckBox defaults, TextField textInput, Stage startGUI)
    {
        int gridSize;

            if (defaults.isSelected())
            {
                textInput.clear();
                gridSize = 5;
            }
            else {
                try {
                    gridSize = Integer.parseInt(textInput.getText());
                } catch (NumberFormatException e) {
                    helpText.setText("Please enter a number or check 'defaults'");
                    textInput.clear();
                    return;
                }
            }
            Main.gridSizeForGame = gridSize;
            startGameGUI(gridSize, startGUI, defaults.isSelected(), helpText);
        }


    private void startGameGUI(int parseInt, Stage startGUI, boolean defaultsOn, Text help)
        {
            startGUI.close();

            Stage mainGame = new Stage();
            View gameView = new View();
            if (defaultsOn)
            {
                gameView.gridSize = 5;
                gameView.setGridSize(5);
            }
            else
            {
                try
                {
                    gameView.setGridSize(parseInt);
                }
                catch (NumberFormatException numException)
                {
                    help.setText("You cannot pass an empty value. Either enter a number or" +
                            "click the default checkbox.");

                }
            }
            Main.gameView = gameView;
            // preparation of the game components for movement and treasure hunting
            Main.prepareGame();

            // establishes the actual View for the game which is managed in the View class
            Main.setupView(gameView);

            // size based on arg algorithm
            double size = (13.0 * Math.pow(parseInt,2)) + 50;

            // set the stage and start the show
            mainGame.setTitle("Treasure Hunt");
            mainGame.setScene(new Scene(gameView.gridPane, size, size));
            mainGame.show();

        }


    public void adjustBotAndTreasureLocations(Point bot, Point treasure) {
        for (Node node : gridPane.getChildren()) {
            int currentColumnIndex = GridPane.getColumnIndex(node);
            int currentRowIndex = GridPane.getRowIndex(node);

            if(node instanceof Text)
            {
                String textToSet  = "";

                if(bot.getX() == currentColumnIndex && bot.getY() == currentRowIndex)
                {
                    textToSet = bot.getX() + " " + bot.getY() + " " + " bot";
                }
                else if(treasure.getX() == currentColumnIndex && treasure.getY() == currentRowIndex)
                {
                    textToSet = treasure.getX() + " " + treasure.getY() + " " + " treasure";
                }
                else
                {
                    textToSet = currentColumnIndex + " " + currentRowIndex + " empty";
                }

                ((Text) node).setText(textToSet);
            }
        }
    }

    // sets up the buttons and adds them to the grid pane
    // The 3 consumers sent to this method should NOT TAKE AN INPUT.  We had to put the object to get around being
    // forced to send something to the method.
    void setupButtons(Consumer<Object> nextFunction, Consumer<Object> resetFunction, Consumer<Object> autoPlayFunction,
    Consumer<Object> upFunction, Consumer<Object> downFunction, Consumer<Object> leftFunction, Consumer<Object> rightFunction)
    {
        // four main buttons
        Button nextPlay = new Button("Next move");
        Button reset = new Button("Reset");
        Button autoPlay = new Button("AutoPlay");

        // cardinal buttons
        Button upMovement = new Button("Move Up");
        Button downMovement = new Button("Move Down");
        Button leftMovement = new Button("Move Left");
        Button rightMovement = new Button("Move Right");

        // so we have a button for each cardinal movement and then those would go to
        // functions in the Game class *moving over to there*
        nextPlay.setOnAction(actionEvent -> nextFunction.accept(null));
        reset.setOnAction(actionEvent -> resetFunction.accept(null));
        autoPlay.setOnAction(actionEvent -> autoPlayFunction.accept(null));

        // cardinal actions on click
        upMovement.setOnAction(actionEvent -> upFunction.accept(null));
        downMovement.setOnAction(actionEvent -> downFunction.accept(null));
        leftMovement.setOnAction(actionEvent -> leftFunction.accept(null));
        rightMovement.setOnAction(actionEvent -> rightFunction.accept(null));

        // adding the buttons to the grid pane
        gridPane.add(nextPlay,1, gridSize + 1);
        gridPane.add(reset,1, gridSize + 2);
        gridPane.add(autoPlay,1,gridSize + 3);

        // buttons to the grid pane
        gridPane.add(upMovement, 1, gridSize + 4);
        gridPane.add(downMovement, 1, gridSize + 6);
        gridPane.add(leftMovement, 0, gridSize + 5);
        gridPane.add(rightMovement, 2, gridSize + 5);
    }
}
