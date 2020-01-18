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
            super.MoveInDirection(direction);
        }
    }

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

    private MoveEnum DetermineRandomMove()
    {
        ArrayList<MoveEnum> currentPossibleMoves = getPossibleMoves();
        Random randomNumberGenerator = new Random(System.currentTimeMillis());
        int randomMovementNumber = randomNumberGenerator.nextInt(currentPossibleMoves.size());
        MoveEnum chosenMove = currentPossibleMoves.get(randomMovementNumber);

        return chosenMove;
    }

    // random bot movement
    private MoveEnum DetermineIntelligentMove()
    {
        // Determine which moves are possible, then add them to the list
        ArrayList<MoveEnum> possibleMoves = getPossibleMoves();
        getPreferredMoves();
        Random randomNumberGenerator = new Random(System.currentTimeMillis());
        MoveEnum move = MoveEnum.Stay;
        if (preferredMoves.size() != 0)
        {
            int randomMovementNumber = randomNumberGenerator.nextInt(preferredMoves.size());
            MoveEnum chosenMove = preferredMoves.get(randomMovementNumber);
            if (possibleMoves.contains(chosenMove)) {
                move = chosenMove;
            }
            preferredMoves.clear();
        }
        else
        {
            int randomMovementNumber = randomNumberGenerator.nextInt(possibleMoves.size());
            MoveEnum chosenMove = possibleMoves.get(randomMovementNumber);
            move = chosenMove;
        }

        return move;
    }

    private void getPreferredMoves()
    {
        if(this.y != 0)
        {
            if (movesMade.size() == 0)
            {
                preferredMoves.add(MoveEnum.Up);
            }
            for (String move : movesMade)
            {
                // extracting x and y
                int xMove = Integer.parseInt(move.split(" ")[0]);
                int yMove = Integer.parseInt(move.split(" ")[1]);
                int proposedY = y-1;
                if (this.x != xMove || proposedY != yMove)
                {
                    preferredMoves.add(MoveEnum.Up);
                }
            }
        }

        if(this.x != this.movementBoundary - 1)
        {
            if (movesMade.size() == 0)
            {
                preferredMoves.add(MoveEnum.Right);
            }
            for (String move : movesMade)
            {
                // extracting x and y
                int xMove = Integer.parseInt(move.split(" ")[0]);
                int yMove = Integer.parseInt(move.split(" ")[1]);
                int proposedX = x+1;
                if (proposedX != xMove || this.y != yMove)
                {
                    preferredMoves.add(MoveEnum.Right);
                }
            }
        }
        if(this.y != this.movementBoundary - 1)
        {
            if (movesMade.size() == 0)
            {
                preferredMoves.add(MoveEnum.Down);
            }
            for (String move : movesMade)
            {
                // extracting x and y
                int xMove = Integer.parseInt(move.split(" ")[0]);
                int yMove = Integer.parseInt(move.split(" ")[1]);
                int proposedY = y+1;
                if (this.x != xMove || proposedY != yMove)
                {
                    preferredMoves.add(MoveEnum.Down);
                }
            }
        }
        if(this.x != 0)
        {
            if (movesMade.size() == 0)
            {
                preferredMoves.add(MoveEnum.Left);
            }
            for (String move : movesMade)
            {
                // extracting x and y
                int xMove = Integer.parseInt(move.split(" ")[0]);
                int yMove = Integer.parseInt(move.split(" ")[1]);
                int proposedX = x-1;
                if (proposedX != xMove || this.y != yMove)
                {
                    preferredMoves.add(MoveEnum.Left);
                }
            }
        }
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
}
