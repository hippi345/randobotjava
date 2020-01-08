package sample.models;

import java.util.Random;

public class Point
{
    protected int x;
    protected int y;

    public Point()
    {
        this.x = this.y = 0;
    }

     public Point(int x, int y)
     {
         this.x = x;
         this.y = y;
     }

     public void RandomizeLocation(int bound)
     {
         Random random = new Random(System.currentTimeMillis());
         this.x = random.nextInt(bound);
         this.y = random.nextInt(bound);
     }

     public int getX()
     {
         return x;
     }

     public int getY()
     {
         return y;
     }
}
