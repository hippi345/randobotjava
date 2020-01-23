package sample.interfaces;

import sample.models.Bot;
import sample.models.GameStatusEnum;
import sample.models.MoveEnum;
import sample.models.Treasure;

public interface IGame {
    void MakeMove();
    void MakeMove(MoveEnum move);
    GameStatusEnum GetStatus();

    // TODO: Remote these explicit get calls when we move to a "paint all objects" design.
    IPoint getBot();
    IPoint getTreasure();
}
