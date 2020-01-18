package sample.models;

import sample.interfaces.IMoveablePoint;
import sample.interfaces.IPoint;

import java.util.Random;

public class Point implements IPoint
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

     public boolean equals(Point p)
     {
         if(p == null)
             return false;
         return p.x == this.x && p.y == this.y;
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

     protected void MoveTo(int x, int y)
     {
         this.x = x;
         this.y = y;
     }

    protected void MoveInDirection(MoveEnum direction)
    {
        switch (direction)
        {
            case Up:
                --this.y;
                break;
            case Right:
                ++this.x;
                break;
            case Down:
                ++this.y;
                break;
            case Left:
                --this.x;
                break;
        }
    }
}
