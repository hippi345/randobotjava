package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import java.util.logging.Logger;

import static sample.Values.*;

public class Main extends Application {

    // Matt Comment
    // Since x and y are things, you might be better to make them into a class called
    // point or something, so that idea is abstracted up higher.

    // TODO below comment
    // Joel comment/question: do I need to move certain functions inside of the Point class
    // that are not yet done by the new refactoring?
    // What should the Point class accomplish in theory separate from the current org?
    //
    // Matt Response - 1/6
    // Think of it more as a container for this information.  It's easier mentally for
    // someone to look and be like "Okay, I know the position of the treasure is a point in
    // space, since it's a Point object."  Writing clean code is more about making it easier
    // for someone else to come in a read it, and have their ramp up in the logic and placement
    // in everything be basically zero.  I wrote more about this in Values.java.

    // Matt Comment
    // Last, I'd write comments above each function as to what they do, just so it's easier for people
    // to come in and be like "reset.. oh yeah okay it resets the game, yeah that makes sense"... looking
    // now, reset could be resetting anything.. placement of something, turns.. no idea.

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        final List<String> parameters = getParameters().getRaw();
        GridPane gridPane = new GridPane();

        // Matt Comment
        // This section is too long, and there's a lot of repeated code.  Try to break
        // this out into functions somehow to make it easier to read.
        // Like the first 2 are basically doing the same thing, just different because
        // one is 0 args and one is more than 1 arg.  I'm also not sure why you're doing any
        // stage setting work if these are error conditions, it should just be like "error"
        // then bounce.
        //
        // EDIT: 1/6
        // MUUUUCH better, so much cleaner just having this one != 1 piece.

        if (parameters.size() != 1)
        {
            System.out.println("Invalid args: exiting...");
            System.exit(1);
        }

        // TODO
        // Joel comment: any thoughts on the below GUI setup?
        // Matt Comment: 1/6
        // I'd at least like to have it in a private method called like setupGUI().
        // The book I'm reading was basically like "any time you have a branch in logic,
        // inside that branch should be a method."  So like here, it would be like "If I don't
        // have the right arguments, exit with an error, else, setup the gui."  Like at a high
        // level, that's what this should do, so it makes sense to write it that way.

        else
        {
            gridPane.setHgap(30);
            gridPane.setVgap(30);
            primaryStage.setTitle("Treasure Hunt");
            int gridSize = Integer.parseInt(parameters.get(0));
            for (int i = 0; i < gridSize; i++)
            {
                for (int j = 0; j < gridSize; j++)
                {
                    gridPane.add(new Text(i + " " + j + " empty"), i, j);
                }
            }
            // setup the buttons
            Button setBot = new Button("Place bot");
            Button setTreasure = new Button("Place treasure");
            Button nextPlay = new Button("Next move");
            Button reset = new Button("Reset");
            Button autoPlay = new Button("AutoPlay");

            setBot.setOnAction(event -> ValueSetters.setBot(gridSize, gridPane, setBot, autoPlay));
            setTreasure.setOnAction(event -> ValueSetters.setTreasure(gridSize, gridPane, setTreasure, autoPlay));
            nextPlay.setOnAction(event -> Player.nextPlay(Values.getTurnCount(), gridSize, gridPane));
            reset.setOnAction(event -> Player.reset(setBot, setTreasure, gridPane, gridSize));
            autoPlay.setOnAction(event -> Player.autoPlay(gridSize, gridPane));

            autoPlay.setDisable(true);

            gridPane.add(setBot,1, gridSize+1);
            gridPane.add(setTreasure,1, gridSize+2);
            gridPane.add(nextPlay,1, gridSize+3);
            gridPane.add(reset,1, gridSize+4);
            gridPane.add(autoPlay,1,gridSize+5);

            primaryStage.setScene(new Scene(gridPane, 450, 450));
            primaryStage.show();
        }
    }
}