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
        // bringing in the argument passed as this is not the main function
        // so it does not have immediate access to the args
        final List<String> parameters = getParameters().getRaw();
        GridPane gridPane = new GridPane();

        // Joel Note 1/7: I added in the check for isDigit because I just realized someone
        // could pass in a letter and fuck it all up big time so I got that cleared
        // If you see any other possible things to do input validation for then let me know

        // Joel Note 1/7: also is there a more elegant way to end the program without System.Exit to your knowledge?

        // errors out without GUI initialization if parameter is wrong
        // performance is optimal this way since we are not wasting memory to spin up an
        // unused GUI

        // Matt 1/7
        // I'd even move this to a function.  Do something like
        // if(!argsAreValid(parameters)
        // {
        //      System.out.println("Invalid args: exiting...");
        //      System.exit(1);
        // }
        // That way it can be contained, and if you have more arguments in the future, you can just add to
        // that argsAreValid function, and not add to the complexity of this part of the code.
        if (!inputsAreValid(parameters))
        {
            System.out.println("Invalid args: exiting...");
            System.exit(1);
        }

        // Joel Note: cleaned up the implementation to make the flow make more sense and for
        // one method (start) to not be doing everything
        // I also think this may ("MAY") be more memory efficient as things are not sticking around forever
        // some things go out of the memory stack/heap/pile/haystack after the methods exit

        // Matt 1/7
        // Else isn't needed here, you can just remove it.  It won't flow down to this code if the above
        // if statement is true.
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
        nextPlay.setOnAction(event -> this.game.moveBot(gridSize, gridPane));
        reset.setOnAction(event -> resetGame(gridPane, gridSize));
        autoPlay.setOnAction(event -> RunAutoplay(gridSize, gridPane));

        // disabling the autoplay on startup
        autoPlay.setDisable(true);

        // adding the buttons to the grid pane
        gridPane.add(nextPlay,1, gridSize + 1);
        gridPane.add(reset,1, gridSize + 2);
        gridPane.add(autoPlay,1,gridSize + 3);
    }

    private boolean inputsAreValid(List<String> inputParameters)
    {
        return inputParameters.size() == 1 && Character.isDigit(inputParameters.get(0).toCharArray()[0]);
    }

    private void RunAutoplay(int gridSize, GridPane gridPane)
    {
        while(true)
        {
            this.game.moveBot(gridSize, gridPane);
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
                int xToSet = GridPane.getRowIndex(node);
                int yToSet = GridPane.getColumnIndex(node);
                ((Text) node).setText(yToSet + " " + xToSet + " " + "empty");
            }
        }
    }

    private void prepareGame(int gridSize, GridPane gridPane)
    {
        this.game = new Game(gridSize);
        this.game.InitializeGame(gridSize, gridPane);
    }
}