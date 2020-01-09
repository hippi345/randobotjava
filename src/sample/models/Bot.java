package sample.models;

import sample.interfaces.IMovablePoint;

import java.util.ArrayList;
import java.util.Random;

public class Bot extends Point implements IMovablePoint
{

    private int movementBoundary;
    public Bot(int movementBoundary)
    {
        super();
        this.movementBoundary = movementBoundary;
    }

    // Interface method
    // For now, we're only moving randomly.  We might extend this in the future to
    // move in a more intelligent way.
    public void Move()
    {
        MoveRandomly();
    }

    private void MoveRandomly()
    {
        // Changed this logic, a loop seems stupid now that I reflect.  We can automatically capture
        // what moves are possible based on where we currently stand, and from there we can add them
        // to a list.  Then we can randomly pick from the list, and execute that move.
        ArrayList<MoveEnum> possibleMoves = new ArrayList<MoveEnum>();

        // Determine which moves are possible, then add them to the list
        if(this.y != 0) possibleMoves.add(MoveEnum.Up);
        if(this.x != this.movementBoundary - 1) possibleMoves.add(MoveEnum.Right);
        if(this.y != this.movementBoundary - 1) possibleMoves.add(MoveEnum.Down);
        if(this.x != 0) possibleMoves.add(MoveEnum.Left);

        Random randomNumberGenerator = new Random(System.currentTimeMillis());

        // nextInt returns a value between 0 (inclusive) and n (EXCLUSIVE)
        // https://www.tutorialspoint.com/java/util/random_nextint_inc_exc.htm
        int randomMovementNumber = randomNumberGenerator.nextInt(possibleMoves.size());

        MoveEnum chosenMove = possibleMoves.get(randomMovementNumber);

        switch (chosenMove)
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
