package sample.interfaces;

import sample.models.GameStatusEnum;
import sample.models.MoveEnum;

public interface IGame {
    void makeMove();

    void makeMove(MoveEnum move);

    GameStatusEnum getStatus();

    IPoint getBot();

    IPoint getTreasure();
}
