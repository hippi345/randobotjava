package sample.models;

import sample.interfaces.IMoveablePoint;

public class moveBotThread extends Thread
{
    private IMoveablePoint bot;

    public moveBotThread(Bot bot)
    {
        this.bot = bot;
    }

    public void start()
    {
        MoveEnum move = bot.DetermineMovement();
        bot.Move(move);
    }
}
