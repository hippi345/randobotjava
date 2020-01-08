package sample;

// Joel Note 1/7
// this is a class which a container for the movement attributes
// and a method to determine a random movement
// it could also be expanded for more intentional movement features in response to
// up, down, left, right movements
// Example: "up" button would indicate the following ->

/*
        this.moveXDir = false;
        this.moveYDir = true;
        this.movePositive = true;
        this.moveNegative = false;
*/

// TODO add manual movement buttons to move the bot with user control to get to the treasure

class Move
{
    private boolean moveXDir;
    private boolean moveYDir;
    private boolean movePositive;
    private boolean moveNegative;

    // zero arg constructor
    private Move()
    {
        this.moveXDir = false;
        this.moveYDir = false;
        this.movePositive = false;
        this.moveNegative = false;
    }

    // constructor with args for each
    private Move(boolean moveXDir, boolean moveYDir, boolean movePositive, boolean moveNegative)
    {
        this.moveXDir = moveXDir;
        this.moveYDir = moveYDir;
        this.movePositive = movePositive;
        this.moveNegative = moveNegative;
    }

    // getters for values that determine movement direction
    boolean getMoveXDir()
    {
        return this.moveXDir;
    }
    boolean getMoveYDir()
    {
        return this.moveYDir;
    }
    boolean getMovePositive()
    {
        return this.movePositive;
    }
    boolean getMoveNegative()
    {
        return this.moveNegative;
    }

    /// establishes a point within the bounds of the grid as decided by the argument passed in the command line
    static Move WithRandomValues()
    {
        Move moveToReturn = new Move();
        // values to determine what kind of movement to randomly make
        int moveVerticalOrHorizontal = (int) Math.round(Math.random()*2);
        int positiveOrNegative = (int) Math.round(Math.random()*2);

        // now we would conditionally set the values
        if (moveVerticalOrHorizontal > 1)
        {
            moveToReturn.moveYDir = true;
            moveToReturn.moveXDir = false;
        }
        else
        {
            moveToReturn.moveYDir = false;
            moveToReturn.moveXDir = true;
        }

        // conditionally setting the values for x or y movement
        if (positiveOrNegative > 1)
        {
            moveToReturn.movePositive = true;
            moveToReturn.moveNegative = false;
        }
        else
        {
            moveToReturn.movePositive = false;
            moveToReturn.moveNegative = true;
        }
        return moveToReturn;
    }
}
