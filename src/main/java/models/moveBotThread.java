package main.java.models;

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
