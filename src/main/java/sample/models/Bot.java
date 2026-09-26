package sample.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import sample.interfaces.IMoveablePoint;

public class Bot extends Point implements IMoveablePoint {
    private static final boolean MOVE_INTELLIGENTLY = true;
    private final HashSet<Point> visitedPoints = new HashSet<>();
    private final int movementBoundary;
    private final Random randomNumberGenerator = new Random();

    public Bot(int movementBoundary) {
        super();
        this.movementBoundary = movementBoundary;
    }

    @Override
    public void move(MoveEnum direction) {
        ArrayList<MoveEnum> possibleMoves = getPossibleMoves();
        if (possibleMoves.contains(direction)) {
            super.moveInDirection(direction);
            this.visitedPoints.add(new Point(this.x, this.y));
        }
    }

    @Override
    public MoveEnum determineMovement() {
        if (MOVE_INTELLIGENTLY) {
            return determineIntelligentMove();
        }
        return determineRandomMove();
    }

    @Override
    public void randomizeLocation(int bound) {
        super.randomizeLocation(bound);
        visitedPoints.clear();
        visitedPoints.add(new Point(this.x, this.y));
    }

    private MoveEnum determineRandomMove() {
        ArrayList<MoveEnum> currentPossibleMoves = getPossibleMoves();
        int randomMovementNumber = randomNumberGenerator.nextInt(currentPossibleMoves.size());
        return currentPossibleMoves.get(randomMovementNumber);
    }

    private MoveEnum determineIntelligentMove() {
        ArrayList<MoveEnum> possibleMoves = getPossibleMoves();
        if (possibleMoves.isEmpty()) {
            return MoveEnum.Stay;
        }
        ArrayList<MoveEnum> preferredMoves = getPreferredMoves(possibleMoves);
        ArrayList<MoveEnum> pool = preferredMoves.isEmpty() ? possibleMoves : preferredMoves;
        return pool.get(randomNumberGenerator.nextInt(pool.size()));
    }

    private ArrayList<MoveEnum> getPreferredMoves(Iterable<MoveEnum> possibleMoves) {
        ArrayList<MoveEnum> preferredMoves = new ArrayList<>();

        for (MoveEnum move : possibleMoves) {
            switch (move) {
                case Up:
                    if (!hasVisitedPoint(this.x, this.y - 1)) {
                        preferredMoves.add(move);
                    }
                    break;
                case Right:
                    if (!hasVisitedPoint(this.x + 1, this.y)) {
                        preferredMoves.add(move);
                    }
                    break;
                case Down:
                    if (!hasVisitedPoint(this.x, this.y + 1)) {
                        preferredMoves.add(move);
                    }
                    break;
                case Left:
                    if (!hasVisitedPoint(this.x - 1, this.y)) {
                        preferredMoves.add(move);
                    }
                    break;
                default:
                    break;
            }
        }

        return preferredMoves;
    }

    private ArrayList<MoveEnum> getPossibleMoves() {
        ArrayList<MoveEnum> possibleMoves = new ArrayList<>();

        if (this.y != 0) {
            possibleMoves.add(MoveEnum.Up);
        }
        if (this.x != this.movementBoundary - 1) {
            possibleMoves.add(MoveEnum.Right);
        }
        if (this.y != this.movementBoundary - 1) {
            possibleMoves.add(MoveEnum.Down);
        }
        if (this.x != 0) {
            possibleMoves.add(MoveEnum.Left);
        }

        return possibleMoves;
    }

    private boolean hasVisitedPoint(int x, int y) {
        return visitedPoints.contains(new Point(x, y));
    }
}
