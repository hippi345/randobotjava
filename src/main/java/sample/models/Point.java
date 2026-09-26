package sample.models;

import java.util.Random;
import sample.interfaces.IPoint;

public class Point implements IPoint {
    int x;
    int y;

    public Point() {
        x = y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(IPoint p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !(o instanceof Point)) {
            return false;
        }

        Point p = (Point) o;

        return p.x == this.x && p.y == this.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    public void randomizeLocation(int bound) {
        Random random = new Random();
        x = random.nextInt(bound);
        y = random.nextInt(bound);
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    public void relocate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected void moveInDirection(MoveEnum direction) {
        switch (direction) {
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
            default:
                break;
        }
    }
}
