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

    public void Move()
    {

    }

    // Interface method
    // For now, we're only moving randomly.  We might extend this in the future to
    // move in a more intelligent way.

    // here its easy to add a new type of manual movement in the same type of design except the direction depends on
    // button pressed and we could use an ENUM or something like moveRandomly to do manual movements
    // So "nextMove" would call moveRandomly along autoplay
    // but the cardinals would call a "moveControlled" or something like that
    // we would move moveRandomly out of the constructor and maybe setup input args which select which one to do?

    public void Move(MoveEnum direction)
    {
        ArrayList<MoveEnum> possibleMoves = new ArrayList<>();

        // Determine which moves are possible, then add them to the list
        if(this.y != 0) possibleMoves.add(MoveEnum.Up);
        if(this.x != this.movementBoundary - 1) possibleMoves.add(MoveEnum.Right);
        if(this.y != this.movementBoundary - 1) possibleMoves.add(MoveEnum.Down);
        if(this.x != 0) possibleMoves.add(MoveEnum.Left);

        if (possibleMoves.contains(direction))
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

    public void MoveRandomly()
    {

        ArrayList<MoveEnum> possibleMoves = new ArrayList<>();

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
