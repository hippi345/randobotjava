package com.randobotjava.models;

import com.randobotjava.models.Bot;

public class moveBotThread extends Thread
{
    private Bot bot;

    public moveBotThread(Bot bot)
    {
        this.bot = bot;
    }

    public void start()
    {
        bot.MoveRandomly();
    }
}
