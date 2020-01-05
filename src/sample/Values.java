package sample;

// TODO
// Joel comment: is this the right idea for a values class or could this be further cleaned up?
// I did not know if Java has easy getter/setter like C# or if you aware of anything further that could be done
// here from either a clean up or conceptual standpoint.

class Values
{
    static int botX;
    static int botY;
    static int treasureX;
    static int treasureY;
    static int turnCount = 0;
    static boolean botSet = false;
    static boolean treasureSet = false;

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

    static void setBotSet()
    {
        botSet = true;
    }

    static boolean getTreasureSet()
    {
        return treasureSet;
    }
}
