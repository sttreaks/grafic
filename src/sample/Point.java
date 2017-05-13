package sample;

/**
 * Created by macbookair on 07.03.17.
 */
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

public void calc (){
    Point point = new Point(100, 200);
    point.x();

}

}



