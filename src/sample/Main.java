package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application {
    private static Game game;
    static int gridSizeForGame;
    public static View gameView;

    // implements your default size if no arg is provided and makes a 5x5 grid
    private final ArrayList<String> parameters = new ArrayList<>();

    private Stage startGUI = new Stage();

    @Override
    public void start(Stage primaryStage)
    {
        View startView = new View();
        startView.startScreen(startGUI);

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
                (o) -> game.moveUp(),
                (o) -> game.moveDown(),
                (o) -> game.moveLeft(),
                (o) -> game.moveRight());

        gameView.adjustBotAndTreasureLocations(game.bot, game.treasure);
    }

    // run moveBot continually with the warning on infinite loops suppressed
    @SuppressWarnings("InfiniteLoopStatement")
    private static void RunAutoPlay() {

        // Matt 1/9 - They're both running on the same thread, we need to figure out how we can have a separate UI thread
        // so that when we run the logic, it isn't blocking the UI thread.  That's basically what's happening now.
        while(true)
        {
            game.executeAutoMove();
        }
    }

    // sets up the Game object with bot and treasure objects in place with coordinates on the grid
    static void prepareGame() {
        game = new Game(gridSizeForGame);
        game.InitializeGame(gameView);
    }
}