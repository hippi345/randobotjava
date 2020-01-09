package sample.models;

import java.util.Random;

public class Point
{
    int x;
    int y;

    Point()
    {
        x = y = 0;
    }

     public Point(int x, int y)
     {
         this.x = x;
         this.y = y;
     }

     public void RandomizeLocation(int bound)
     {
         Random random = new Random(System.currentTimeMillis());
         x = random.nextInt(bound);
         y = random.nextInt(bound);
     }

     public int getX()
     {
         return this.x;
     }

     public int getY()
     {
         return this.y;
     }
}
