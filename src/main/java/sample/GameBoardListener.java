package sample;

import sample.interfaces.IPoint;

/** Notifies the UI when bot or treasure positions change. */
interface GameBoardListener {
    void onPositionsChanged(
            IPoint previousBotPosition, IPoint currentBotPosition, IPoint treasurePosition);

    default void onGameComplete() {}
}
