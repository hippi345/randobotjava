package sample;

class ValuesForGameRun
{
    // coordinates for the bot and treasure accessed across multiple classes
    static int botX;
    static int botY;
    static int treasureX;
    static int treasureY;
    // turn count for auto-play and in Main where we make the buttons
    static int turnCount = 0;
    // values for whether the bot is set or the treasure is set
    // the values for these are set in the setters class for game components but used in the Game class
    // for determining whether to use auto-play or not
    private static boolean botSet = false;
    static boolean treasureSet = false;

    // getters and setters for attributes needed across multiple classes
    static void setBotX(int x)
    {
        botX = x;
    }

    static int getBotX()
    {
        return botX;
    }

    static void setBotY(int y)
    {
        botY = y;
    }

    static int getBotY()
    {
        return botY;
    }

    static int getTurnCount()
    {
        return turnCount;
    }

    static void botIsSet()
    {
        botSet = true;
    }

    static boolean getTreasureSet()
    {
        return treasureSet;
    }

    static boolean getBotSet() { return botSet; }
}
