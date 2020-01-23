package sample;

public class newThreadItems implements Runnable
{
    @Override
    public void run()
    {
        Main.startGUI.close();
    }
}
