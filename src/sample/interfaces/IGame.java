package sample.interfaces;

import sample.models.Bot;
import sample.models.MoveEnum;
import sample.models.Treasure;

public interface IGame {
    void Move();
    void Move(MoveEnum requestedMove);
    Bot getBot();
    Treasure getTreasure();
}
