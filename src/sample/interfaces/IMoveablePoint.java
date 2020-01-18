package sample.interfaces;

import sample.models.MoveEnum;

public interface IMoveablePoint extends IPoint {
    void RandomizeLocation(int bound);
    void Move(MoveEnum direction);
    MoveEnum DetermineMovement();
}
