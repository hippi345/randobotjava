package sample.models;

import sample.Main;
import sample.View;

public class GUIThread extends Thread
{

    public GUIThread(Bot bot, Treasure treasure, View view)
    {
    }

    public void start(Bot bot, Treasure treasure, View view)
    {
        Main.gameView.adjustBotAndTreasureLocations(bot, treasure);
    }
}
