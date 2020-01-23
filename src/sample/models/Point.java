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

     public Point(IPoint p)
     {
         this.x = p.getX();
         this.y = p.getY();
     }

     @Override
     public boolean equals(Object o)
     {
         if (this == o)
         {
             return true;
         }

         if(!(o instanceof Point))
         {
             return false;
         }
         Point p = (Point) o;
         return p.x == this.x && p.y == this.y;
     }

     @Override
     public int hashCode()
     {
         String hashString = this.x + "," + this.y;
         return hashString.hashCode();
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

    void MoveInDirection(MoveEnum direction)
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
