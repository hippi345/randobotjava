package sample.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Bot extends Point
{
    private int movementBoundary;

    public Bot(int movementBoundary)
    {
        super();
        this.movementBoundary = movementBoundary;
    }

    // made the interface instead to require a moveEnum move
    // and a no argument moveRandomly
    public void Move(MoveEnum direction)
    {
        // Determine which moves are possible, then add them to the list
        var possibleMoves = getPossibleMoves();
        if (possibleMoves.contains(direction))
        {
            executeMove(direction);
        }
    }

    // random bot movement
    public void MoveRandomly()
    {
        // Determine which moves are possible, then add them to the list
        var possibleMoves = getPossibleMoves();
        Random randomNumberGenerator = new Random(System.currentTimeMillis());
        int randomMovementNumber = randomNumberGenerator.nextInt(possibleMoves.size());
        MoveEnum chosenMove = possibleMoves.get(randomMovementNumber);
        executeMove(chosenMove);
    }

    private ArrayList<MoveEnum> getPossibleMoves()
    {
        ArrayList<MoveEnum> possibleMoves = new ArrayList<MoveEnum>();

        if(this.y != 0) possibleMoves.add(MoveEnum.Up);
        if(this.x != this.movementBoundary - 1) possibleMoves.add(MoveEnum.Right);
        if(this.y != this.movementBoundary - 1) possibleMoves.add(MoveEnum.Down);
        if(this.x != 0) possibleMoves.add(MoveEnum.Left);

        return possibleMoves;
    }

    private void executeMove(MoveEnum direction)
    {
        switch (direction)
        {
            case Up:
                --this.y;
                break;
            case Right:
                ++this.x;
                break;
            case Down:
                ++this.y;
                break;
            case Left:
                --this.x;
                break;
        }
    }
}
