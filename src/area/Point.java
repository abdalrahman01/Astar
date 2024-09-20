package src.area;


/**
 * Point
 */

public class Point {

    public double x, y; 
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point(String point) {
        String[] xy = point.substring(1,point.length()-1).split(",");
        x =Double.parseDouble(xy[0]);
        y =Double.parseDouble(xy[1]);
    }
    public Point minus(Point point){
        return new Point(x - point.x, y - point.y);
    }

    public boolean equals(Point that) {
        return this.x == that.x && this.y == that.y;
    }
    public double distance(Point p) {
        return Math.sqrt((x-p.x)*(x-p.x) + (y-p.y)*(y-p.y));
    }
    @Override
    public String toString() {
        return "(" + x + "," + y  +")";
    }

}