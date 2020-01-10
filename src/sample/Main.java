package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private Game game;
    // implements your default size if no arg is provided and makes a 5x5 grid
    private final ArrayList<String> parameters = new ArrayList<String>();

    @Override
    public void start(Stage primaryStage) {
        try {
            parameters.add(getParameters().getRaw().get(0));
        }
        catch (IndexOutOfBoundsException e)
        {
            parameters.add(String.valueOf(Constants.DEFAULT_GRIDSIZE));
        }

        if (!inputsAreValid(parameters))
        {
            System.out.println("Invalid args: exiting...");
            System.exit(1);
        }

        int gridSize = Integer.parseInt(parameters.get(0));

        View gameView = View.getInstance();
        gameView.setGridSize(gridSize);

        prepareGame();

        // establishes the actual View for the game which is managed in the View class
        setupView(gameView);

        // set the stage and start the show
        // this is where we scale based on number of args

        // size based on arg algorithm
        double size = (13.0 * Math.pow(gridSize,2)) + 50;

        primaryStage.setTitle("Treasure Hunt");
        primaryStage.setScene(new Scene(gameView.gridPane, size, size));
        primaryStage.show();
    }

    // input validation method
    private boolean inputsAreValid(List<String> inputParameters)
    {
        // added current supported max size on my machine
        if (Integer.parseInt(inputParameters.get(0)) > 16)
        {
            System.out.println("Chill out cowboy, we only support 16 as the max grid size right now.");
        }
        return inputParameters.size() == 1 && Character.isDigit(inputParameters.get(0).toCharArray()[0])
                && Integer.parseInt(inputParameters.get(0)) <= 16;
    }

    private void setupView(View gameView)
    {
        gameView.setupTheGridPane();
        
        // This took a long time to figure out, but I knew the pattern I wanted when
        // I started and I don't give up so easily lol.  We do this in .NET a lot,
        // so I had to kind of hack a way to pass the functions I wanted to execute.
        gameView.setupButtons(
                (o) -> this.game.executeMove(),
                (o) -> prepareGame(),
                (o) -> RunAutoPlay());

        gameView.adjustBotAndTreasureLocations(this.game.bot, this.game.treasure);
    }

    // run moveBot continually with the warning on infinite loops suppressed
    @SuppressWarnings("InfiniteLoopStatement")
    private void RunAutoPlay() {

        // Matt 1/9 - They're both running on the same thread, we need to figure out how we can have a separate UI thread
        // so that when we run the logic, it isn't blocking the UI thread.  That's basically what's happening now.
        while(true)
        {
            this.game.executeMove();
        }
    }

    // sets up the Game object with bot and treasure objects in place with coordinates on the grid
    private void prepareGame() {
        this.game = new Game();
        this.game.InitializeGame();
    }
}