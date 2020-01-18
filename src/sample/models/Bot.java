package sample.models;

import java.util.ArrayList;
import java.util.Random;

public class Bot extends Point
{
    private ArrayList<String> movesMade = new ArrayList<>();
    private int movementBoundary;
    private Random randomNumberGenerator = new Random(System.currentTimeMillis());

    public Bot(int movementBoundary)
    {
        super();
        this.movementBoundary = movementBoundary;
    }

    public void Move(MoveEnum direction)
    {
        // Determine which moves are possible, then add them to the list
        ArrayList<MoveEnum> possibleMoves = getPossibleMoves();
        if (possibleMoves.contains(direction))
        {
            executeMove(direction);
        }
    }

    // random bot movement
    public void MoveRandomly()
    {
        // Determine which moves are possible, then add them to the list
        ArrayList<MoveEnum> possibleMoves = getPossibleMoves();
        ArrayList<MoveEnum> preferredMoves = getPreferredMoves();
        if (preferredMoves.size() != 0)
        {
            int randomMovementNumber = this.randomNumberGenerator.nextInt(preferredMoves.size());
            MoveEnum chosenMove = preferredMoves.get(randomMovementNumber);
            if (possibleMoves.contains(chosenMove))
            {
                executeMove(chosenMove);
            }
        }
        else
            {
                int randomMovementNumber = randomNumberGenerator.nextInt(possibleMoves.size());
                MoveEnum chosenMove = possibleMoves.get(randomMovementNumber);
                executeMove(chosenMove);
            }
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
