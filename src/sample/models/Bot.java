package sample.models;

import sample.interfaces.IMoveablePoint;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Bot extends Point implements IMoveablePoint
{
    private HashSet<Point> visitedPoints = new HashSet<>();
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
        ArrayList<MoveEnum> possibleMoves = getPossibleMoves();
        if (possibleMoves.contains(direction))
        {
            super.MoveInDirection(direction);
            this.visitedPoints.add(new Point(this.x, this.y));
        }
    }

    // Implementation from IMoveablePoint
    // Determines move based on config values.
    @Override
    public MoveEnum DetermineMovement()
    {
        MoveEnum move;

        boolean MOVE_INTELLIGENTLY = true;
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
        return currentPossibleMoves.get(randomMovementNumber);
    }

    // Internal method to determine the next move intelligently.
    private MoveEnum DetermineIntelligentMove()
    {
        // Determine which moves are possible, then add them to the list
        ArrayList<MoveEnum> possibleMoves = getPossibleMoves();
        ArrayList<MoveEnum> preferredMoves = getPreferredMoves(possibleMoves);
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
            move = possibleMoves.get(randomMovementNumber);
        }
        return move;
    }

    private ArrayList<MoveEnum> getPreferredMoves(Iterable<MoveEnum> possibleMoves)
    {
        ArrayList<MoveEnum> preferredMoves = new ArrayList<>();
        for (MoveEnum move : possibleMoves)
        {
            switch (move)
            {
                case Up:
                    if(!hasVisitedPoint(this.x, this.y - 1))
                        preferredMoves.add(move);
                    break;
                case Right:
                    if(!hasVisitedPoint(this.x + 1, this.y))
                        preferredMoves.add(move);
                    break;
                case Down:
                    if(!hasVisitedPoint(this.x, this.y + 1))
                        preferredMoves.add(move);
                    break;
                case Left:
                    if(!hasVisitedPoint(this.x - 1, this.y))
                        preferredMoves.add(move);
                    break;
            }
        }
        return preferredMoves;
    }

    // Internal method to determine which moves are possible.
    private ArrayList<MoveEnum> getPossibleMoves()
    {
        ArrayList<MoveEnum> possibleMoves = new ArrayList<>();
        if(this.y != 0) possibleMoves.add(MoveEnum.Up);
        if(this.x != this.movementBoundary - 1) possibleMoves.add(MoveEnum.Right);
        if(this.y != this.movementBoundary - 1) possibleMoves.add(MoveEnum.Down);
        if(this.x != 0) possibleMoves.add(MoveEnum.Left);
        return possibleMoves;
    }
    private boolean hasVisitedPoint(int x, int y)
    {
        return visitedPoints.contains(new Point(x, y));
    }
}
