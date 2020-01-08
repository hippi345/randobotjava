package sample;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;

// Joel Note: its buggy now and will go past the boundaries so if you could fix that bug please
// Also the performance was fine before even with the node iterations so I am not sure where it is lagging now

// Ways to observe these bugs: you can do a manual play of the game and see the bot will hop through edge boundaries and this was not happening before
// If you run a push prior to this refactoring, you will see there was no issue with time spent to execute autoplay or manual movements
// As these exist now, I am having difficulty tracking down the source of why these bugs are occurring

public class Main extends Application {

    private Game game;

    // this is the start method which sets up the GUI and elements within the grid pane object
    // it includes the buttons for actions, the labels for locations and objects (or lack thereof) which are there
    // Includes initial formatting of UI
    // TODO base the size of the GUI off the estimated size of the grid (based on arg passed) and the static buttons

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
        int gridSize = Integer.parseInt(parameters.get(0));

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
        Button autoPlay = new Button("AutoPlay");

        nextPlay.setOnAction(event -> this.game.moveBot(gridPane));
        reset.setOnAction(event -> resetGame(gridPane, gridSize));
        autoPlay.setOnAction(event -> RunAutoplay(gridPane));

        // adding the buttons to the grid pane
        gridPane.add(nextPlay,1, gridSize + 1);
        gridPane.add(reset,1, gridSize + 2);
        gridPane.add(autoPlay,1,gridSize + 3);
    }

    // input validation method
    private boolean inputsAreValid(List<String> inputParameters)
    {
        return inputParameters.size() == 1 && Character.isDigit(inputParameters.get(0).toCharArray()[0]);
    }

    // run moveBot continually with the warning on infinite loops suppressed
    @SuppressWarnings("InfiniteLoopStatement")
    private void RunAutoplay(GridPane gridPane)
    {
        while(true)
        {
            this.game.moveBot(gridPane);
        }
    }

    // clear the board when reset is clicked
    // Joel Note: for some reason this was not right and it was clearing the labels after initializing the game
    // fixed
    private void resetGame(GridPane gridPane, int gridSize)
    {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text)
            {
                int xToSet = GridPane.getColumnIndex(node);
                int yToSet = GridPane.getRowIndex(node);
                ((Text) node).setText(xToSet + " " + yToSet + " " + "empty");
            }
        }

        this.game = new Game(gridSize);
        this.game.InitializeGame(gridSize, gridPane);
    }

    private void prepareGame(int gridSize, GridPane gridPane)
    {
        this.game = new Game(gridSize);
        this.game.InitializeGame(gridSize, gridPane);
    }
}