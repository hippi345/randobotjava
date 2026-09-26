package sample;

import java.util.Hashtable;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.interfaces.IGame;
import sample.interfaces.IPoint;
import sample.models.MoveEnum;
import sample.models.Point;

class View implements GameBoardListener {
    static Stage mainGame;
    GridPane gridPane;
    int gridSize = Constants.DEFAULT_GRID_SIZE;
    Hashtable<IPoint, Node> pointNodeMap = new Hashtable<>();

    View() {
        mainGame = new Stage();
        this.gridPane = new GridPane();
    }

    private void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    void setupTheGridPane() {
        this.gridPane.setHgap(8);
        this.gridPane.setVgap(8);
        setupGui();
        setupMatrixMap();
    }

    private void setupGui() {
        for (int i = 0; i < this.gridSize; i++) {
            for (int j = 0; j < this.gridSize; j++) {
                this.gridPane.add(new Text(""), i, j);
            }
        }
        this.gridPane.setStyle(Constants.LIGHT_BLUE);
    }

    void startScreen(Stage startGui, Game game) {
        this.gridPane.setHgap(8);
        this.gridPane.setVgap(8);
        this.gridPane.setStyle(Constants.LIGHT_BLUE);

        Text handle = new Text("Welcome to the Random Bot Game!");
        Text argInquire = new Text("What is the Grid size? (size <= 15)");
        Button submitStart = new Button("Go!");
        CheckBox defaultBehaviors = new CheckBox("Defaults for the application");
        TextField gridSizeStart = new TextField();

        gridSizeStart
                .textProperty()
                .addListener(
                        new ChangeListener<String>() {
                            @Override
                            public void changed(
                                    ObservableValue<? extends String> observable,
                                    String oldValue,
                                    String newValue) {
                                if (!newValue.matches("\\d*")) {
                                    gridSizeStart.setText(newValue.replaceAll("[^\\d]", ""));
                                }
                            }
                        });

        submitStart.setOnAction(
                actionEvent -> prepTheGame(defaultBehaviors, gridSizeStart, startGui, game));
        defaultBehaviors
                .selectedProperty()
                .addListener((observable, oldValue, newValue) -> gridSizeStart.setText(""));

        this.gridPane.add(handle, 0, 0);
        this.gridPane.add(argInquire, 0, 1);
        this.gridPane.add(gridSizeStart, 0, 2);
        this.gridPane.add(defaultBehaviors, 1, 2);
        this.gridPane.add(submitStart, 0, 3);
    }

    private void prepTheGame(CheckBox defaults, TextField textInput, Stage startGui, Game game) {
        int resolvedGridSize;
        if (defaults.isSelected() || textInput.getText().isBlank()) {
            textInput.clear();
            resolvedGridSize = Constants.DEFAULT_GRID_SIZE;
        } else {
            resolvedGridSize = Integer.parseInt(textInput.getText());
            if (resolvedGridSize < 1) {
                resolvedGridSize = Constants.DEFAULT_GRID_SIZE;
            } else if (resolvedGridSize > Constants.MAX_GRID_SIZE) {
                resolvedGridSize = Constants.MAX_GRID_SIZE;
                alertMsgOnMax();
            }
        }
        startGui.close();
        Main.gridSizeForGame = resolvedGridSize;
        startGameGui(resolvedGridSize, game);
    }

    private void alertMsgOnMax() {
        Stage alert = new Stage();
        Text message =
                new Text(
                        "Applying the max size (15) as the actual grid size since input exceeded it.");
        Button ok = new Button("Ok");
        ok.setOnAction(actionEvent -> alert.close());
        GridPane msgGridPane = new GridPane();
        msgGridPane.add(message, 0, 0);
        msgGridPane.add(ok, 0, 1);
        alert.setScene(new Scene(msgGridPane, 400, 75));
        alert.show();
        alert.setAlwaysOnTop(true);
    }

    private void startGameGui(int parseInt, Game game) {
        View gameView = new View();
        gameView.setGridSize(parseInt);
        Main.gameView = gameView;

        gameView.setupTheGridPane();

        Main.prepareGame();
        Main.setupView(gameView);

        double size = (13.0 * Math.pow(parseInt, 2)) + 50;
        mainGame.setTitle("Treasure Hunt");
        mainGame.setScene(new Scene(gameView.gridPane, size, size));
        mainGame.show();
    }

    private void setupMatrixMap() {
        for (Node node : gridPane.getChildren()) {
            int currentColumnIndex = GridPane.getColumnIndex(node);
            int currentRowIndex = GridPane.getRowIndex(node);

            if (node instanceof Text) {
                ((Text) node).setText(currentColumnIndex + " " + currentRowIndex + " empty");
                pointNodeMap.put(new Point(currentColumnIndex, currentRowIndex), node);
            }
        }
    }

    @Override
    public void onGameComplete() {
        setupEndGameGui();
    }

    @Override
    public void onPositionsChanged(
            IPoint previousBotPosition, IPoint currentBotPosition, IPoint treasurePosition) {
        if (previousBotPosition != null) {
            Node previousNode = pointNodeMap.get(previousBotPosition);
            if (previousNode instanceof Text) {
                ((Text) previousNode)
                        .setText(
                                previousBotPosition.getX()
                                        + " "
                                        + previousBotPosition.getY()
                                        + " empty");
            }
        }

        Node currentBotNode = pointNodeMap.get(currentBotPosition);
        if (currentBotNode instanceof Text) {
            ((Text) currentBotNode)
                    .setText(currentBotPosition.getX() + " " + currentBotPosition.getY() + " bot");
        }

        Node treasureNode = pointNodeMap.get(treasurePosition);
        if (treasureNode != currentBotNode && treasureNode instanceof Text) {
            ((Text) treasureNode)
                    .setText(treasurePosition.getX() + " " + treasurePosition.getY() + " treasure");
        }
    }

    void setupButtons(
            Consumer<Object> nextFunction,
            Consumer<Object> resetFunction,
            Consumer<Object> autoPlayFunction,
            Consumer<Object> leaveGame) {
        Button nextPlay = new Button("Next move");
        Button reset = new Button("Reset");
        Button autoPlay = new Button("AutoPlay");
        Button exit = new Button("Leave game");

        nextPlay.setOnAction(actionEvent -> nextFunction.accept(null));
        reset.setOnAction(actionEvent -> resetFunction.accept(null));
        autoPlay.setOnAction(actionEvent -> autoPlayFunction.accept(null));
        exit.setOnAction(actionEvent -> leaveGame.accept(null));

        gridPane.add(nextPlay, 1, gridSize + 1);
        gridPane.add(reset, 1, gridSize + 2);
        gridPane.add(autoPlay, 1, gridSize + 3);
        gridPane.add(exit, 1, gridSize + 4);
    }

    void setupDirectionButtons(IGame game, View gameView) {
        Button upMovement = new Button("Move Up");
        Button downMovement = new Button("Move Down");
        Button leftMovement = new Button("Move Left");
        Button rightMovement = new Button("Move Right");
        upMovement.setOnAction(event -> game.makeMove(MoveEnum.Up));
        downMovement.setOnAction(event -> game.makeMove(MoveEnum.Down));
        leftMovement.setOnAction(event -> game.makeMove(MoveEnum.Left));
        rightMovement.setOnAction(event -> game.makeMove(MoveEnum.Right));

        gameView.gridPane.add(upMovement, 1, gridSize + 5);
        gameView.gridPane.add(downMovement, 1, gridSize + 7);
        gameView.gridPane.add(leftMovement, 0, gridSize + 6);
        gameView.gridPane.add(rightMovement, 2, gridSize + 6);
    }

    static void setupEndGameGui() {
        Stage endGameGui = new Stage();
        GridPane endPane = new GridPane();
        endPane.setHgap(5);
        endPane.setVgap(5);
        Text endMsg = new Text("Congrats on finding the treasure! Give it another go or quit?");
        Button continueButton = new Button("New Game");
        Button exitButton = new Button("Exit");

        continueButton.setOnAction(
                event -> {
                    Main.startGui.close();
                    endGameGui.close();
                    Main.backToStartup();
                });

        exitButton.setOnAction(
                event -> {
                    Main.startGui.close();
                    endGameGui.close();
                    System.out.println("Game closed");
                    Platform.exit();
                });

        endPane.add(endMsg, 0, 0);
        endPane.add(continueButton, 0, 2);
        endPane.add(exitButton, 0, 3);

        endGameGui.setScene(new Scene(endPane, 300, 300));
        endGameGui.show();
    }
}
