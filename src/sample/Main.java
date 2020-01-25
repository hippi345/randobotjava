package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.interfaces.IGame;
import sample.models.GameStatusEnum;

// needs an extensive review before moving forward
public class Main extends Application
{
    private static IGame game;
    static int gridSizeForGame;
    static View gameView;

    public static Stage startGUI = new Stage();

    @Override
    public void start(Stage primaryStage)
    {
        View startView = new View();
        startView.startScreen(startGUI, (Game) game);

        // set the stage and start the show
        startGUI.setTitle("Treasure Hunt");
        startGUI.setScene(new Scene(startView.gridPane, 400, 200));
        startGUI.show();
    }

    static void setupView(View gameView)
    {
        // setting up the buttons which go into the UI
        gameView.setupButtons(
                (o) -> game.MakeMove(),
                (o) -> prepareGame(),
                (o) -> RunAutoPlay(),
                (o) -> backToStartup());

        gameView.setupDirectionButtons(game, gameView);
    }

    // run moveBot continually with the warning on infinite loops suppressed
    @SuppressWarnings("InfiniteLoopStatement")
    private static void RunAutoPlay()
    {
        while(game.GetStatus() != GameStatusEnum.Complete)
        {
            game.MakeMove();
        }
    }

    // sets up the Game object with bot and treasure objects in place with coordinates on the grid
    static void prepareGame() {
        game = new Game(gridSizeForGame, gameView);
    }

    // method to return the GUI to the main menu
    public static void backToStartup()
    {
        View.mainGame.close();
        View startView = new View();
        startView.startScreen(startGUI, (Game) game);
        startGUI.show();
    }
}