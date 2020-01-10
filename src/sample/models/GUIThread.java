package sample.models;

import sample.View;

public class GUIThread extends Thread
{
    private Bot bot;
    private Treasure treasure;
    private View view;

    public GUIThread(Bot bot, Treasure treasure, View view)
    {
        this.bot = bot;
        this.treasure = treasure;
        this.view = view;
    }

    public void start(Bot bot, Treasure treasure, View view)
    {
        View.getInstance().adjustBotAndTreasureLocations(bot, treasure);
    }
}
