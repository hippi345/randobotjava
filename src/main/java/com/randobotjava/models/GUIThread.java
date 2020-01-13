package com.randobotjava.models;

import com.randobotjava.models.Bot;
import com.randobotjava.Main;
import com.randobotjava.View;

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
