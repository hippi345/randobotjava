package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.interfaces.IGame;
import sample.models.MoveEnum;

// needs an extensive review before moving forward
public class Main extends Application
{
    private static IGame game;
    static int gridSizeForGame;
    public static View gameView;

    private static Stage startGUI = new Stage();

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
                (o) -> game.Move(),
                (o) -> prepareGame(),
                (o) -> RunAutoPlay(),
                (o) -> backToStartup(),
                (o) -> game.Move(MoveEnum.Up),
                (o) -> game.Move(MoveEnum.Down),
                (o) -> game.Move(MoveEnum.Left),
                (o) -> game.Move(MoveEnum.Right));

        gameView.adjustBotAndTreasureLocations(game.getBot(), game.getTreasure());
    }

    // run moveBot continually with the warning on infinite loops suppressed
    @SuppressWarnings("InfiniteLoopStatement")
    private static void RunAutoPlay()
    {
        // Matt 1/9 - They're both running on the same thread, we need to figure out how we can have a separate UI thread
        // so that when we run the logic, it isn't blocking the UI thread.  That's basically what's happening now.
        while(true)
        {
            game.Move();
        }
    }

    // sets up the Game object with bot and treasure objects in place with coordinates on the grid
    static void prepareGame() {
        game = new Game(gridSizeForGame, gameView);
    }

    // method to return the GUI to the main menu
    private static void backToStartup()
    {
        View.mainGame.close();
        View startView = new View();
        startView.startScreen(startGUI);
        startGUI.show();
    }
}