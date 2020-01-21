package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// needs an extensive review before moving forward
public class Main extends Application
{
    static Game game;
    static int gridSizeForGame;
    static View gameView;

    private static Stage startGUI = new Stage();

    @Override
    public void start(Stage primaryStage)
    {
        View startView = new View();
        startView.startScreen(startGUI, game);

        // set the stage and start the show
        startGUI.setTitle("Treasure Hunt");
        startGUI.setScene(new Scene(startView.gridPane, 400, 200));
        startGUI.show();
    }

    static void setupView(View gameView)
    {
        // creation of the grid pane
        gameView.setupTheGridPane();

        // setting up the buttons which go into the UI
        gameView.setupButtons(
                (o) -> game.executeAutoMove(),
                (o) -> prepareGame(),
                (o) -> RunAutoPlay(),
                (o) -> backToStartup());

        gameView.setupDirectionButtons(game, gameView);
        gameView.redrawGrid(game.bot, game.treasure);
    }

    // run moveBot continually with the warning on infinite loops suppressed
    @SuppressWarnings("InfiniteLoopStatement")
    private static void RunAutoPlay()
    {
        while(!Constants.GAME_COMPLETE)
        {
            game.executeAutoMove();
        }
    }

    // sets up the Game object with bot and treasure objects in place with coordinates on the grid
    static void prepareGame() {
        game = new Game(gridSizeForGame);
        game.InitializeGame(gameView);
    }

    // method to return the GUI to the main menu
    static void backToStartup()
    {
        View.mainGame.close();
        View startView = new View();
        startView.startScreen(startGUI, game);
        startGUI.show();
    }
}