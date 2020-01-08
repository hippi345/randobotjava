package sample.models;

import java.util.Random;

// Joel Note 1/7 Adding in the Point Class
public class Point
{
    protected int x;
    protected int y;

    // Joel Note 1/7
    // I did not have a situation where the empty constructor would be called so I removed
    // that constructor
    // perhaps in future features or implementations there will be a need for it but for now there is not
    public Point()
    {
        this.x = this.y = 0;
    }

    // using this for assignment
     public Point(int x, int y)
     {
         this.x = x;
         this.y = y;
     }

     /// establishes a point within the bounds of the grid as decided by the argument passed in the command line
    // Matt 1/7
    // why pass in bound if you're not using it?  I'd pass it in and use it.
    // You don't want to couple it to another class, you want it to be completely isolated.
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
