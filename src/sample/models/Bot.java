package sample.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Bot extends Point
{
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
            executeMove(direction);
        }
    }

    // random bot movement
    public void MoveRandomly()
    {
        // Determine which moves are possible, then add them to the list
        getPreferredMoves();
        Random randomNumberGenerator = new Random(System.currentTimeMillis());
        if (preferredMoves.size() != 0)
        {
            int randomMovementNumber = randomNumberGenerator.nextInt(preferredMoves.size());
            MoveEnum chosenMove = preferredMoves.get(randomMovementNumber);
            if (possibleMoves.contains(chosenMove)) {
                moveTheBot(chosenMove);
            }
            preferredMoves.clear();
        }
        else
            {
                int randomMovementNumber = randomNumberGenerator.nextInt(possibleMoves.size());
                MoveEnum chosenMove = possibleMoves.get(randomMovementNumber);
                moveTheBot(chosenMove);
            }
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
                movesMade.add(this.x + " " + this.y);
                break;
            case Right:
                ++this.x;
                movesMade.add(this.x + " " + this.y);
                break;
            case Down:
                ++this.y;
                movesMade.add(this.x + " " + this.y);
                break;
            case Left:
                --this.x;
                movesMade.add(this.x + " " + this.y);
                break;
        }
    }
}
