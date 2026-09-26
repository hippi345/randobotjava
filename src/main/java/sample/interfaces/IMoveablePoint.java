package sample.interfaces;

import sample.models.MoveEnum;

public interface IMoveablePoint extends IPoint {
    void randomizeLocation(int bound);

    void move(MoveEnum direction);

    MoveEnum determineMovement();
}
