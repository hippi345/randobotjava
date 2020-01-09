package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        final List<String> parameters = getParameters().getRaw();
        if (!inputsAreValid(parameters))
        {
            System.out.println("Invalid args: exiting...");
            System.exit(1);
        }

        int gridSize = Integer.parseInt(parameters.get(0));
        View gameView = new View();

        Game mainGame = prepareGame(gridSize, gameView.gridPane, gameView);
        // establishes the actual View for the game which is managed in the View class

        gameView.setupTheGridPane(gameView, gridSize, mainGame.bot, mainGame.treasure, mainGame);

        // setup the labels based on the input
        gameView.setupGUILabelsFromInput(gridSize, gameView.gridPane);

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

    // run moveBot continually with the warning on infinite loops suppressed
    @SuppressWarnings("InfiniteLoopStatement")
    static void RunAutoPlay(Game game, GridPane gridPane, View gameView) throws InterruptedException {
        // The game calls exit by itself, we might want to refactor to be able to play multiple games in 1 session.
        // I'd also really like to see this auto-play update the UX as well, but we should separate out into a
        // ViewModel and a View and have the view update on a separate thread.

        // Joel Note: 1/9 - I am also seeing auto-play does not update the UI and I was trying to figure out to do that
        // Happy to do it but I did not know if you had any thoughts?
        while(true)
        {
            game.executeMove(gridPane, gameView);
        }
    }

    // sets up the Game object with bot and treasure objects in place with coordinates on the grid
    static Game prepareGame(int gridSize, GridPane gridPane, View gameView) throws InterruptedException {
        Game game = new Game(gridSize);
        game.InitializeGame(gridSize, gridPane, gameView);
        return game;
    }
}