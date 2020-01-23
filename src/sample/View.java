package sample;

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
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.function.Consumer;

class View
{
    static Stage mainGame;
    private static View instance = null;
    GridPane gridPane;
    int gridSize = Constants.DEFAULT_GRIDSIZE;
    Hashtable<IPoint, Node> _pointNodeMap = new Hashtable<>();

    // Constructor must be private for the Singleton pattern.
    View()
    {
        mainGame = new Stage();
        this.gridPane = new GridPane();
    }

    // setter for grid size
    private void setGridSize(int gridSize)
    {
        this.gridSize = gridSize;
    }

    // setting up gaps specific to every grid pane
    void setupTheGridPane() {
        this.gridPane.setHgap(8);
        this.gridPane.setVgap(8);
        setupGUI();
        SetupMatrixMap();
    }

    // sets up the labels from the grid size passed into the command line
    private void setupGUI()
    {
        for (int i = 0; i < this.gridSize; i++)
        {
            for (int j = 0; j < this.gridSize; j++)
            {
                this.gridPane.add(new Text(""), i, j);
            }
        }
        this.gridPane.setStyle(Constants.LIGHT_BLUE);
    }

    // setting up the start screen
    void startScreen(Stage startGUI, Game game)
    {
        // gap between elements in the start screen
        this.gridPane.setHgap(8);
        this.gridPane.setVgap(8);
        this.gridPane.setStyle(Constants.LIGHT_BLUE);

        // elements for the start screen gui stage
        Text handle = new Text("Welcome to the Random Bot Game!");
        Text argInquire = new Text("What is the Grid size? (size <= 15)");
        Button submitStart = new Button("Go!");
        CheckBox defaultBehaviors = new CheckBox("Defaults for the application");
        TextField gridSizeStart = new TextField();

        // force the field to be numeric only
        gridSizeStart.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    gridSizeStart.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // button action to start the actual game
        submitStart.setOnAction(actionEvent -> prepTheGame(defaultBehaviors, gridSizeStart, startGUI, game));
        // listener on the checkbox such that the defaults are applied, the text is cleared
        // the checkbox is also checked on the game running for whether to apply defaults or not
        defaultBehaviors.selectedProperty().addListener((observable, oldValue,
                                                         newValue) -> gridSizeStart.setText(""));
        // add the elements to the grid pane
        this.gridPane.add(handle,0,0);
        this.gridPane.add(argInquire,0, 1);
        this.gridPane.add(gridSizeStart,0,2);
        this.gridPane.add(defaultBehaviors,1,2);
        this.gridPane.add(submitStart,0,3);
    }

    private void prepTheGame(CheckBox defaults, TextField textInput, Stage startGUI, Game game)
    {
        int gridSize;
            // if defaults are checked then apply the default 5x5 size
        if (defaults.isSelected())
            {
                textInput.clear();
                gridSize = Constants.DEFAULT_GRIDSIZE;
            }
        else {
            gridSize = Integer.parseInt(textInput.getText());
            if (gridSize > Constants.MAX_GRIDSIZE)
                {
                    gridSize = Constants.MAX_GRIDSIZE;
                    alertMsgOnMax();
                }
            }
        startGUI.close();
        // apply the computed grid size to the grid pane used in main which
        // is the central grid pane resource for setting up the game gui
        Main.gridSizeForGame = gridSize;
        // start the actual game gui
        startGameGUI(gridSize, game);
    }

    // creates a pop up GUI stage alerting the user that the number provided exceeds the max so the max was used
    private void alertMsgOnMax()
    {
        Stage alert = new Stage();
        Text message = new Text("Applying the max size (15) as the actual grid size since input exceeded it.");
        Button ok = new Button("Ok");
        ok.setOnAction(actionEvent -> alert.close());
        GridPane msgGridPane = new GridPane();
        msgGridPane.add(message, 0, 0);
        msgGridPane.add(ok, 0, 1);
        alert.setScene(new Scene(msgGridPane, 400, 75));
        alert.show();
        alert.setAlwaysOnTop(true);
    }

    // startup of the game gui stage
    private void startGameGUI(int parseInt, Game game) {
        // creating the new game stage gui and view
        View gameView = new View();
        // setting the view grid size
        gameView.setGridSize(parseInt);
        // setting the main game view to a fresh game view
        Main.gameView = gameView;
        // establishes the actual View for the game which is managed in the View class

        // creation of the grid pane
        gameView.setupTheGridPane();

        // preparation of the game components for movement and treasure hunting
        Game.prepareGame();
        View.setupView(gameView);

        // size based on arg algorithm
        double size = (13.0 * Math.pow(parseInt,2)) + 50;
        // set the stage and start the show
        mainGame.setTitle("Treasure Hunt");
        mainGame.setScene(new Scene(gameView.gridPane, size, size));
        mainGame.show();
    }

    private void SetupMatrixMap()
    {
        for (Node node : gridPane.getChildren()) {
            int currentColumnIndex = GridPane.getColumnIndex(node);
            int currentRowIndex = GridPane.getRowIndex(node);

            if(node instanceof Text)
            {
                ((Text) node).setText(currentColumnIndex + " " + currentRowIndex + " empty");
                _pointNodeMap.put(new Point(currentColumnIndex, currentRowIndex), node);
            }
        }
    }

    // sets the text on the nodes to reflect the move just made
    public void adjustBotAndTreasureLocations(IPoint previousBotPosition, IPoint currentBotPosition, IPoint treasurePosition) {
        if(previousBotPosition != null)
        {
            Node previousNode = _pointNodeMap.get(previousBotPosition);
            ((Text)previousNode).setText(previousBotPosition.getX() + " " + previousBotPosition.getY() + " empty");
        }

        Node currentBotNode = _pointNodeMap.get(currentBotPosition);
        ((Text)currentBotNode).setText(currentBotPosition.getX() + " " + currentBotPosition.getY() + " bot");

        Node treasureNode = _pointNodeMap.get(treasurePosition);
        if(treasureNode != currentBotNode)
        {
            ((Text)treasureNode).setText(treasurePosition.getX() + " " + treasurePosition.getY() + " treasure");
        }
    }

    // setting up the button element components of the game gui
    void setupButtons(Consumer<Object> nextFunction, Consumer<Object> resetFunction, Consumer<Object> autoPlayFunction,
    Consumer<Object> leaveGame)
    {
        // four main buttons
        Button nextPlay = new Button("Next move");
        Button reset = new Button("Reset");
        Button autoPlay = new Button("AutoPlay");
        Button exit = new Button("Leave game");

        // so we have a button for each cardinal movement and then those would go to
        // functions in the Game class *moving over to there*
        nextPlay.setOnAction(actionEvent -> nextFunction.accept(null));
        reset.setOnAction(actionEvent -> resetFunction.accept(null));
        autoPlay.setOnAction(actionEvent -> autoPlayFunction.accept(null));
        exit.setOnAction(actionEvent -> leaveGame.accept(null));

        // adding the buttons to the grid pane
        gridPane.add(nextPlay,1, gridSize + 1);
        gridPane.add(reset,1, gridSize + 2);
        gridPane.add(autoPlay,1,gridSize + 3);
        gridPane.add(exit, 1, gridSize + 4);

    }
    void setupDirectionButtons(IGame game, View gameView)
      {
          // cardinal buttons
          Button upMovement = new Button("Move Up");
          Button downMovement = new Button("Move Down");
          Button leftMovement = new Button("Move Left");
          Button rightMovement = new Button("Move Right");
          upMovement.setOnAction(event -> game.MakeMove(MoveEnum.Up));
          downMovement.setOnAction(event -> game.MakeMove(MoveEnum.Down));
          leftMovement.setOnAction(event -> game.MakeMove(MoveEnum.Left));
          rightMovement.setOnAction(event -> game.MakeMove(MoveEnum.Right));

          // buttons to the grid pane
          gameView.gridPane.add(upMovement, 1, gridSize + 5);
          gameView.gridPane.add(downMovement, 1, gridSize + 7);
          gameView.gridPane.add(leftMovement, 0, gridSize + 6);
          gameView.gridPane.add(rightMovement, 2, gridSize + 6);
      }

    public static void setupEndGameGUI()
    {
        Stage endGameGUI = new Stage();
        GridPane endPane = new GridPane();
        endPane.setHgap(5);
        endPane.setVgap(5);
        Text endMsg = new Text("Congrats on finding the treasure! Give it another go or quit?");
        Button continueButton = new Button("New Game");
        Button exitButton = new Button("Exit");

        continueButton.setOnAction(event ->
        {
            Main.startGUI.close();
            endGameGUI.close();
            Main.backToStartup();
        });

        exitButton.setOnAction(event -> {
            Main.startGUI.close();
            endGameGUI.close();
            System.out.println("Game closed");
            System.exit(69);
        });

        endPane.add(endMsg, 0,0);
        endPane.add(continueButton, 0, 2);
        endPane.add(exitButton, 0, 3);

        endGameGUI.setScene(new Scene(endPane, 300, 300));
        endGameGUI.show();
    }

    public static void setupView(View gameView)
    {
        // setting up the buttons which go into the UI
        gameView.setupButtons(
                (o) -> Main.game.MakeMove(),
                (o) -> Game.prepareGame(),
                (o) -> Game.RunAutoPlay(),
                (o) -> Main.backToStartup());

        gameView.setupDirectionButtons(Main.game, gameView);
    }
}
