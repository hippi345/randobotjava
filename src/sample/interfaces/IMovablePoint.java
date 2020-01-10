package sample.interfaces;

// Joel Notes 1/8
// oh my god I just realized how this is clean

import sample.models.MoveEnum;

public interface IMovablePoint {
    default void Move(MoveEnum direction)
    {

    }
    default void MoveRandomly()
    {

    }
}
