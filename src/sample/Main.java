package sample;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;

public class Main extends Application {

    // Matt 1/7
    // Does this still hold?
    // made this button static since it is accessed via Player for whether to enable auto-play or not
    static Button autoPlay = new Button("AutoPlay");
    static int gridSize = 0;
    private Game game;

    // this is the start method which sets up the GUI and elements within the grid pane object
    // it includes the buttons for actions, the labels for locations and objects (or lack thereof) which are there
    // Includes initial formatting of UI
    // TODO base the size of the GUI off the estimated size of the grid (based on arg passed) and the static buttons

    // Joel Note 1/7 this is a work item for to scale the GUI to the input arg so unless you want to point me in a
    // direction for fun, this is something I am fully planning to research and implement on my side
    // I am guessing you have to compute pixel sizes of elements (including gaps between them) and then pass
    // that number into the "Scene" method

    @Override
    public void start(Stage primaryStage)
    {
        final List<String> parameters = getParameters().getRaw();
        GridPane gridPane = new GridPane();

        if (!inputsAreValid(parameters))
        {
            System.out.println("Invalid args: exiting...");
            System.exit(1);
        }

        gridPane.setHgap(30);
        gridPane.setVgap(30);
        primaryStage.setTitle("Treasure Hunt");
        gridSize = Integer.parseInt(parameters.get(0));

        // setup the labels based on the input
        setupGUILabelsFromInput(gridSize, gridPane);

        prepareGame(gridSize, gridPane);

        // setup the buttons
        setupButtons(gridPane, gridSize);

        // set the stage and start the show
        primaryStage.setScene(new Scene(gridPane, 450, 450));
        primaryStage.show();
    }

    // sets up the labels from the grid size passed into the command line
    private static void setupGUILabelsFromInput(int gridSize, GridPane gridPane)
    {
        for (int i = 0; i < gridSize; i++)
        {
            for (int j = 0; j < gridSize; j++)
            {
                gridPane.add(new Text(i + " " + j + " empty"), i, j);
            }
        }
    }

    // sets up the buttons and adds them to the grid pane
    private void setupButtons(GridPane gridPane, int gridSize)
    {
        // four main buttons
        Button nextPlay = new Button("Next move");
        Button reset = new Button("Reset");

        // actions on clicks for buttons

        // Matt 1/7
        // Do we really need a set bot and set treasure button?  Can't we do that when
        // the game starts instead, that way we insure that it's always set too and we don't have
        // to worry about disabling a button or anything.
        nextPlay.setOnAction(event -> this.game.moveBot(gridPane));
        reset.setOnAction(event -> resetGame(gridPane, gridSize));
        autoPlay.setOnAction(event -> RunAutoplay(gridPane, gridSize));

        // adding the buttons to the grid pane
        gridPane.add(nextPlay,1, gridSize + 1);
        gridPane.add(reset,1, gridSize + 2);
        gridPane.add(autoPlay,1,gridSize + 3);
    }

    private boolean inputsAreValid(List<String> inputParameters)
    {
        return inputParameters.size() == 1 && Character.isDigit(inputParameters.get(0).toCharArray()[0]);
    }

    private void RunAutoplay(GridPane gridPane, int gridSize)
    {
        resetGame(gridPane, gridSize);

        while(true)
        {
            this.game.moveBot(gridPane);
        }
    }

    // clear the board when reset is clicked
    private void resetGame(GridPane gridPane, int gridSize)
    {
        this.game = new Game(gridSize);
        this.game.InitializeGame(gridSize, gridPane);

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text)
            {
                int xToSet = GridPane.getColumnIndex(node);
                int yToSet = GridPane.getRowIndex(node);
                ((Text) node).setText(xToSet + " " + yToSet + " " + "empty");
            }
        }
    }

    private void prepareGame(int gridSize, GridPane gridPane)
    {
        this.game = new Game(gridSize);
        this.game.InitializeGame(gridSize, gridPane);
    }
}