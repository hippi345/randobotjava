package sample;

// TODO implement this out
// Matt Response - 1/6
// Think of it more as a container for this information.  It's easier mentally for
// someone to look and be like "Okay, I know the position of the treasure is a point in
// space, since it's a Point object."  Writing clean code is more about making it easier
// for someone else to come in a read it, and have their ramp up in the logic and placement
// in everything be basically zero.  I wrote more about this in Values.java.

// Joel Response: is this not being done in the Values class?
// I don't have any mentions of the locations in the Main class at this point so perhaps
// comment on where optimizations could be made in the Player, Values, and ValuesSetters classes
// in reference to the Points class we are speaking about

// Matt Comment:
// So what I was thinking was something like this:
//
// class Point
// {
//     private int x;
//     private int y;
//
//     Point()
//     {
//         x = 0;
//         y = 0;
//     }
//
//     Point(int x, int y)
//     {
//         x = x;
//         y = y;
//     }
//
//     public static Point WithRandomValues(int bound)
//     {
//         Random randomGenerator = new Random();
//         int x = randomGenerator.nextInt(bound);
//         int y = randomGenerator.nextInt(bound);
//
//         return new Point(x, y);
//     }
//
//     public int getX() {
//         return x;
//     }
//
//     public int getY() {
//         return y;
//     }
// }
//
// That way, when making a new bot and treasure, you could just be like
//
//  private Point botPosition = Point.WithRandomValues(gridWidth);
//  private Point treasurePosition = Point.WithRandomValues(gridWidth);
//
// Simple, clean, very easy to understand, and fewer variables.  Easy to create
// with random values, too.  Having a giant container names "Values" feels
// ambiguous, when I first opened the file, I was confused as to what would be
// in the file.

class Values
{
    static int botX;
    static int botY;
    static int treasureX;
    static int treasureY;
    static int turnCount = 0;
    private static boolean botSet = false;
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

    static boolean getBotSet() { return botSet; }
}
