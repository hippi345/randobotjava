package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.interfaces.IGame;

public class Main extends Application
{
    static IGame game;
    static int gridSizeForGame;
    static View gameView;
    static Stage startGUI = new Stage();

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

    // this actually IMO makes sense to leave in main as its a different flavor of the same implementation
    // of start() which is in main. leaving for now.
    static void backToStartup()
    {
        View.mainGame.close();
        View startView = new View();
        startView.startScreen(startGUI, (Game) game);
        startGUI.show();
    }
}