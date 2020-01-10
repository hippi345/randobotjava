package sample.models;

import sample.interfaces.IMovablePoint;

import java.util.ArrayList;
import java.util.Random;

public class Bot extends Point implements IMovablePoint
{
    private ArrayList<MoveEnum> possibleMoves = new ArrayList<>();
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
        getPossibleMoves();
        if (possibleMoves.contains(direction))
        {
            moveTheBot(direction);
        }
    }

    // random bot movement
    public void MoveRandomly()
    {
        // Determine which moves are possible, then add them to the list
        getPossibleMoves();
        Random randomNumberGenerator = new Random(System.currentTimeMillis());
        int randomMovementNumber = randomNumberGenerator.nextInt(possibleMoves.size());
        MoveEnum chosenMove = possibleMoves.get(randomMovementNumber);
        moveTheBot(chosenMove);
    }

    private void getPossibleMoves()
    {
        if(this.y != 0) possibleMoves.add(MoveEnum.Up);
        if(this.x != this.movementBoundary - 1) possibleMoves.add(MoveEnum.Right);
        if(this.y != this.movementBoundary - 1) possibleMoves.add(MoveEnum.Down);
        if(this.x != 0) possibleMoves.add(MoveEnum.Left);
    }

    public void moveTheBot(MoveEnum direction)
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
