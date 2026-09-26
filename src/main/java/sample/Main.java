package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.interfaces.IGame;
import sample.models.GameStatusEnum;

public class Main extends Application {
    private static IGame game;
    static int gridSizeForGame;
    static View gameView;

    public static Stage startGui = new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        View startView = new View();
        startView.startScreen(startGui, (Game) game);

        startGui.setTitle("Treasure Hunt");
        startGui.setScene(new Scene(startView.gridPane, 400, 200));
        startGui.show();
    }

    static void setupView(View gameView) {
        gameView.setupButtons(
                (o) -> game.makeMove(),
                (o) -> prepareGame(),
                (o) -> runAutoPlay(),
                (o) -> backToStartup());

        gameView.setupDirectionButtons(game, gameView);
    }

    private static void runAutoPlay() {
        Thread autoPlayThread =
                new Thread(
                        () -> {
                            while (game.getStatus() != GameStatusEnum.Complete) {
                                Platform.runLater(() -> game.makeMove());
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    return;
                                }
                            }
                        },
                        "autoplay");
        autoPlayThread.setDaemon(true);
        autoPlayThread.start();
    }

    static void prepareGame() {
        game = new Game(gridSizeForGame, gameView);
    }

    public static void backToStartup() {
        View.mainGame.close();
        View startView = new View();
        startView.startScreen(startGui, (Game) game);
        startGui.show();
    }
}
