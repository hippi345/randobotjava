package main.java.models;

import main.java.Main;
import main.java.View;

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
