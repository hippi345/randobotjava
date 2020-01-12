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
import javafx.stage.Stage;
import sample.models.Point;
import java.util.function.Consumer;

public class View
{
    static Stage mainGame;
    private static View instance = null;
    GridPane gridPane;
    int gridSize = Constants.DEFAULT_GRIDSIZE;

    // Constructor must be private for the Singleton pattern.
    View()
    {
        mainGame = new Stage();
        this.gridPane = new GridPane();
    }

    // Joel note
    // So trying to use get instance kept returning a blank view and so
    // it was not working correctly
    // could have been in how I was setting up what was calling it but I was not sure
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

    // setter for grid size
    private void setGridSize(int gridSize)
    {
        this.gridSize = gridSize;
    }

    // setting up gaps specific to every grid pane
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
        // gap between elements in the start screen
        this.gridPane.setHgap(8);
        this.gridPane.setVgap(8);

        // elements for the start screen gui stage
        Text handle = new Text("Welcome to the Random Bot Game!");
        Text argInquire = new Text("What is the Grid size? (size <= 15)");
        Button submitStart = new Button("Go!");
        CheckBox defaultBehaviors = new CheckBox("Defaults for the application");
        TextField gridSizeStart = new TextField();

        // force the field to be numeric only
        gridSizeStart.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    gridSizeStart.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // button action to start the actual game
        submitStart.setOnAction(actionEvent -> prepTheGame(defaultBehaviors, gridSizeStart, startGUI));
        // listener on the checkbox such that the defaults are applied, the text is cleared
        // the checkbox is also checked on the game running for whether to apply defaults or not
        defaultBehaviors.selectedProperty().addListener((observable, oldValue,
                                                         newValue) -> gridSizeStart.setText(""));
        // add the elements to the grid pane
        this.gridPane.add(handle,0,0);
        this.gridPane.add(argInquire,0, 1);
        this.gridPane.add(gridSizeStart,0,2);
        this.gridPane.add(defaultBehaviors,1,2);
        this.gridPane.add(submitStart,0,3);
    }

    private void prepTheGame(CheckBox defaults, TextField textInput, Stage startGUI)
    {
        int gridSize;
            // if defaults are checked then apply the default 5x5 size
            if (defaults.isSelected())
            {
                textInput.clear();
                gridSize = Constants.DEFAULT_GRIDSIZE;
            }
            else {
                    gridSize = Integer.parseInt(textInput.getText());
                    if (gridSize > Constants.MAX_GRIDSIZE)
                    {
                        gridSize = Constants.MAX_GRIDSIZE;
                        alertMsgOnMax();
                    }
                }
            startGUI.close();
            // apply the computed grid size to the grid pane used in main which
            // is the central grid pane resource for setting up the game gui
            Main.gridSizeForGame = gridSize;
            // start the actual game gui
            startGameGUI(gridSize);
        }

    // creates a pop up GUI stage alerting the user that the number provided exceeds the max so the max was used
    private void alertMsgOnMax()
    {
        Stage alert = new Stage();
        Text message = new Text("Applying the max size (15) as the actual grid size since input exceeded it.");
        Button ok = new Button("Ok");
        ok.setOnAction(actionEvent -> alert.close());
        GridPane msgGridPane = new GridPane();
        msgGridPane.add(message, 0, 0);
        msgGridPane.add(ok, 0, 1);
        alert.setScene(new Scene(msgGridPane, 400, 75));
        alert.show();
        alert.setAlwaysOnTop(true);
    }

    // startup of the game gui stage
    private void startGameGUI(int parseInt)
        {
            // creating the new game stage gui and view
            // Stage mainGame = new Stage();
            View gameView = new View();
            // setting the view grid size
            gameView.setGridSize(parseInt);
            // setting the main game view to a fresh game view
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

    // sets the text on the nodes to reflect the move just made
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

    // setting up the button element components of the game gui
    void setupButtons(Consumer<Object> nextFunction, Consumer<Object> resetFunction, Consumer<Object> autoPlayFunction,
    Consumer<Object> leaveGame, Consumer<Object> upFunction, Consumer<Object> downFunction,
                      Consumer<Object> leftFunction, Consumer<Object> rightFunction)
    {
        // four main buttons
        Button nextPlay = new Button("Next move");
        Button reset = new Button("Reset");
        Button autoPlay = new Button("AutoPlay");
        Button exit = new Button("Leave game");

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
        exit.setOnAction(actionEvent -> leaveGame.accept(null));

        // cardinal actions on click
        upMovement.setOnAction(actionEvent -> upFunction.accept(null));
        downMovement.setOnAction(actionEvent -> downFunction.accept(null));
        leftMovement.setOnAction(actionEvent -> leftFunction.accept(null));
        rightMovement.setOnAction(actionEvent -> rightFunction.accept(null));

        // adding the buttons to the grid pane
        gridPane.add(nextPlay,1, gridSize + 1);
        gridPane.add(reset,1, gridSize + 2);
        gridPane.add(autoPlay,1,gridSize + 3);
        gridPane.add(exit, 1, gridSize + 4);

        // buttons to the grid pane
        gridPane.add(upMovement, 1, gridSize + 5);
        gridPane.add(downMovement, 1, gridSize + 7);
        gridPane.add(leftMovement, 0, gridSize + 6);
        gridPane.add(rightMovement, 2, gridSize + 6);
    }
}
