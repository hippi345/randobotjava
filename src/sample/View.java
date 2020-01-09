package sample;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sample.models.Bot;
import sample.models.Treasure;

class View
{
    GridPane gridPane;

    View()
    {
        this.gridPane = new GridPane();
    }

    void setupTheGridPane(View view, int gridPaneSize, Bot bot, Treasure treasure, Game game) throws InterruptedException {
        this.gridPane.setHgap(8);
        this.gridPane.setVgap(8);
        setupGUILabelsFromInput(gridPaneSize, this.gridPane);
        setTheLabelTexts(this.gridPane, bot, treasure);
        setupButtons(game, this.gridPane, gridPaneSize, view);
    }

    // sets up the labels from the grid size passed into the command line
    void setupGUILabelsFromInput(int gridSize, GridPane gridPane)
    {
        for (int i = 0; i < gridSize; i++)
        {
            for (int j = 0; j < gridSize; j++)
            {
                gridPane.add(new Text(""),i,j);
            }
        }
    }

    void setTheLabelTexts(GridPane gridPane, Bot bot, Treasure treasure) throws InterruptedException {
        for (Node node : gridPane.getChildren()) {
            int currentColumnIndex = GridPane.getColumnIndex(node);
            int currentRowIndex = GridPane.getRowIndex(node);

            if(node instanceof Text)
            {
                String textToSet  = "";

                if(bot.getX() == currentColumnIndex && bot.getY() == currentRowIndex)
                {
                    textToSet = bot.getX() + " " + bot.getY() + " " + " bot";
                }
                else if(treasure.getX() == currentColumnIndex && treasure.getY() == currentRowIndex)
                {
                    textToSet = treasure.getX() + " " + treasure.getY() + " " + " treasure";
                }
                else
                {
                    textToSet = currentColumnIndex + " " + currentRowIndex + " empty";
                }

                ((Text) node).setText(textToSet);
            }
        }
        // added sleep so the UI can catch up with the moves
    }

    // sets up the buttons and adds them to the grid pane
    private void setupButtons(Game game, GridPane gridPane, int gridSize, View gameView)
    {
        // four main buttons
        Button nextPlay = new Button("Next move");
        Button reset = new Button("Reset");
        Button autoPlay = new Button("AutoPlay");

        nextPlay.setOnAction(event -> {
            try {
                game.executeMove(gridPane, gameView);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        reset.setOnAction(event -> {
            try {
                Main.prepareGame(gridSize, gridPane, gameView);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        autoPlay.setOnAction(event -> {
            try {
                Main.RunAutoPlay(game, gridPane, gameView);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // adding the buttons to the grid pane
        gridPane.add(nextPlay,1, gridSize + 1);
        gridPane.add(reset,1, gridSize + 2);
        gridPane.add(autoPlay,1,gridSize + 3);
    }
}
