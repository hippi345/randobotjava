package sample.interfaces;

import sample.models.MoveEnum;

public interface IMovablePoint {
    default void Move(MoveEnum direction)
    {

    }
    default void MoveRandomly()
    {

    }
}
