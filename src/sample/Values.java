package sample;

// TODO
// Joel comment: is this the right idea for a values class or could this be further cleaned up?
// I did not know if Java has easy getter/setter like C# or if you aware of anything further that could be done
// here from either a clean up or conceptual standpoint.

import java.util.Random;

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
