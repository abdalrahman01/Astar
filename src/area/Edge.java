package src.area;

public class Edge implements Comparable<Point> {
    public Point start, end;
    private double length;
    public Edge(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Edge(Point start, Point end, double length) {
        this.start = start; 
        this.end = end;
        this.length = length;
    }

    /**
     * @return the length
     */
    public double getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Compare this edge to another point.
     * if return value is positive, this point is to the left of the edge.
     * if return value is negative, this point is to the right of the edge.
     * if return value is 0, this point is on the edge.
     */
    public int compareTo(Point p) {
        Point ab = end.minus(start); // can represent a vector from start point to end point
        Point ac = p.minus(start); // can represent a vector from start point to p.
        double zCrossProduct = ab.x * ac.y - ac.x * ab.y;

        return (int)(zCrossProduct);
    }

    public double weight() {
        return start.distance(end);
    }

    public boolean isCrossing(Edge that) {
        int a = compareTo(that.start);
        int b = compareTo(that.end);
        int c = that.compareTo(this.start);
        int d = that.compareTo(this.end);

        if (a == 0 || b == 0) // one point is in edge.
            return false;
        if (a > 0 && b > 0) // both points are to left of edge
            return false;
        if (a < 0 && b < 0) // both points are to left of edge.
            return false;
        if ((a < 0 && b > 0) || (a > 0 && b < 0)) {
            if ((c < 0 && d < 0) || (c > 0 && d > 0))
                return false;
            
        }
        // if (((a < 0 && b > 0) || (a > 0 && b < 0)) && ((c > 0 && d < 0) || (c < 0 && d > 0)))
        //     return false;

        return true;
    }


    public boolean equals(Edge that) {
        if (that == null)
            return false;
        return (this.start.equals(that.start) && this.end.equals(that.end) ||
                this.start.equals(that.end) && this.end.equals(that.start));
    }

    @Override
    public String toString() {
        return start + " ->" + end;
    }

    public static void main(String[] args) {
        Point start = new Point(0, 0);
        Point end = new Point(10, 0);
        Edge edge = new Edge(start, end);
        Point a = new Point(-3, -2);
        Point c = new Point(-3, 2);
        Point d = new Point(100, 0);
        System.out.println(edge.compareTo(a));
        System.out.println(edge.compareTo(c));
        System.out.println(edge.compareTo(d));
    }
}
