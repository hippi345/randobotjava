package sample.models;

import sample.interfaces.IMovablePoint;

import java.util.Random;

public class Bot extends Point implements IMovablePoint
{
    // renamed to boundary as bound seemed vague
    private int movementBoundary;
    public Bot(int movementBoundary)
    {
        super();
        this.movementBoundary = movementBoundary;
    }

    // Interface method
    public void Move()
    {
        // Here we're going to denote a move like this:
        //      0 is a move up
        //      1 is a move to the right
        //      2 is a move down
        //      3 is a move to the left

        while(true)
        {
            Random randomNumberGenerator = new Random(System.currentTimeMillis());

            // nextInt returns a value between 0 (inclusive) and n (EXCLUSIVE)
            // https://www.tutorialspoint.com/java/util/random_nextint_inc_exc.htm
            int moveNumber = randomNumberGenerator.nextInt(4);

            // realized hasMoved is to get us out of the while loop. lit.
            boolean hasMoved = false;

            switch (moveNumber)
            {
                case 0 :
                    if(this.y != 0)
                    {
                        --this.y;
                        hasMoved = true;
                    }

                    break;

                case 1 :
                    if(this.x != this.movementBoundary - 1)
                    {
                        ++this.x;
                        // hasMoved = true;
                    }

                    break;

                case 2 :
                    if(this.y != this.movementBoundary - 1)
                    {
                        ++this.y;
                        hasMoved = true;
                    }

                    break;

                case 3 :
                    if(this.x != 0)
                    {
                        --this.x;
                        // hasMoved = true;
                    }

                    break;
            }

            if(hasMoved) break;
        }
    }
}
