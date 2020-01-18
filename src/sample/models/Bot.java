package sample.models;

import sample.interfaces.IMoveablePoint;

import java.util.ArrayList;
import java.util.Random;

public class Bot extends Point implements IMoveablePoint
{
    private final boolean MOVE_INTELLIGENTLY = true;
    private ArrayList<MoveEnum> preferredMoves = new ArrayList<>();
    private ArrayList<String> movesMade = new ArrayList<>();
    private int movementBoundary;
    private Random randomNumberGenerator = new Random(System.currentTimeMillis());

    public Bot(int movementBoundary)
    {
        super();
        this.movementBoundary = movementBoundary;
    }

    // Implementation from IMoveablePoint
    // Self-checks that the move is possible.
    @Override
    public void Move(MoveEnum direction)
    {
        var possibleMoves = getPossibleMoves();
        if (possibleMoves.contains(direction))
        {
            super.MoveInDirection(direction);
        }
    }

    // Implementation from IMoveablePoint
    // Determines move based on config values.
    @Override
    public MoveEnum DetermineMovement()
    {
        MoveEnum move;

        if(MOVE_INTELLIGENTLY)
        {
            move = this.DetermineIntelligentMove();
        }
        else
        {
            move = this.DetermineRandomMove();
        }

        return move;
    }

    // Internal method to determine the next random move.
    private MoveEnum DetermineRandomMove()
    {
        ArrayList<MoveEnum> currentPossibleMoves = getPossibleMoves();
        int randomMovementNumber = randomNumberGenerator.nextInt(currentPossibleMoves.size());
        MoveEnum chosenMove = currentPossibleMoves.get(randomMovementNumber);

        return chosenMove;
    }

    // Internal method to determine the next move intelligently.
    private MoveEnum DetermineIntelligentMove()
    {
        // Determine which moves are possible, then add them to the list
        ArrayList<MoveEnum> possibleMoves = getPossibleMoves();
        getPreferredMoves();
        MoveEnum move = MoveEnum.Stay;
        if (preferredMoves.size() != 0)
        {
            int randomMovementNumber = this.randomNumberGenerator.nextInt(preferredMoves.size());
            MoveEnum chosenMove = preferredMoves.get(randomMovementNumber);
            if (possibleMoves.contains(chosenMove)) {
                move = chosenMove;
            }
        }
        else
        {
            int randomMovementNumber = randomNumberGenerator.nextInt(possibleMoves.size());
            MoveEnum chosenMove = possibleMoves.get(randomMovementNumber);
            move = chosenMove;
        }

        return move;
    }

    private ArrayList<MoveEnum> getPreferredMoves()
    {
        ArrayList<MoveEnum> preferredMoves = new ArrayList<MoveEnum>();
        // up preferred move
        if(this.y != 0)
        {
            if (movesMade.size() == 0)
            {
                preferredMoves.add(MoveEnum.Up);
            }
            for (String move : movesMade)
            {
                if (this.x != Integer.parseInt(move.split(" ")[0])
                        || (this.y-1) != Integer.parseInt(move.split(" ")[1]))
                {
                    preferredMoves.add(MoveEnum.Up);
                }
            }
        }
        // right preferred move
        if(this.x != this.movementBoundary - 1)
        {
            if (movesMade.size() == 0)
            {
                preferredMoves.add(MoveEnum.Right);
            }
            for (String move : movesMade)
            {
                if ((this.x+1) != Integer.parseInt(move.split(" ")[0])
                        || this.y != Integer.parseInt(move.split(" ")[1]))
                {
                    preferredMoves.add(MoveEnum.Right);
                }
            }
        }
        // down preferred move
        if(this.y != this.movementBoundary - 1)
        {
            if (movesMade.size() == 0)
            {
                preferredMoves.add(MoveEnum.Down);
            }
            for (String move : movesMade)
            {
                if (this.x != Integer.parseInt(move.split(" ")[0])
                        || (this.y+1) != Integer.parseInt(move.split(" ")[1]))
                {
                    preferredMoves.add(MoveEnum.Down);
                }
            }
        }
        // left preferred move
        if(this.x != 0)
        {
            if (movesMade.size() == 0)
            {
                preferredMoves.add(MoveEnum.Left);
            }
            for (String move : movesMade)
            {
                if ((this.x-1) != Integer.parseInt(move.split(" ")[0])
                        || this.y != Integer.parseInt(move.split(" ")[1]))
                {
                    preferredMoves.add(MoveEnum.Left);
                }
            }
        }
        return preferredMoves;
    }

    // Internal method to determine which moves are possible.
    private ArrayList<MoveEnum> getPossibleMoves()
    {
        ArrayList<MoveEnum> possibleMoves = new ArrayList<MoveEnum>();

        if(this.y != 0) possibleMoves.add(MoveEnum.Up);
        if(this.x != this.movementBoundary - 1) possibleMoves.add(MoveEnum.Right);
        if(this.y != this.movementBoundary - 1) possibleMoves.add(MoveEnum.Down);
        if(this.x != 0) possibleMoves.add(MoveEnum.Left);

        return possibleMoves;
    }
}
