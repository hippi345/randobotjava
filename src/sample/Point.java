package sample;

// Joel Note 1/7 Adding in the Point Class
class Point
{
    private int x;
    private int y;

    // Joel Note 1/7
    // I did not have a situation where the empty constructor would be called so I removed
    // that constructor
    // perhaps in future features or implementations there will be a need for it but for now there is not

    // using this for assignment
     private Point(int x, int y)
     {
         this.x = x;
         this.y = y;
     }

     /// establishes a point within the bounds of the grid as decided by the argument passed in the command line
     static Point WithRandomValues(int bound)
     {
         float random = (float) Math.random();
         int x = (int) random * (Main.gridSize-1);
         int y = (int) random * (Main.gridSize-1);
         return new Point(x, y);
     }

     int getX() {
         return x;
     }

     int getY() {
         return y;
     }
}
