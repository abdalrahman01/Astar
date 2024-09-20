package src.area;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import lib.StdDraw;
public class Area implements Iterable<Polygon> {
    
    private final Point BoundaryLeftBottom; // border
    private final Point BoundaryRightTop;
    public ArrayList<Polygon> polygons; // all obstacles
    // private int size;
    private ArrayList<Point> accessiblePoints; // all accessible points, that are not in obstacles

    public Area(Scanner data) {
        accessiblePoints = new ArrayList<Point>();
        polygons = new ArrayList<Polygon>();

        if (data.hasNextLine()) {
            // read the first line, it contains the boundary
            String[] boundary = data.nextLine().split(" ");

            BoundaryLeftBottom = new Point(boundary[1]);
            BoundaryRightTop = new Point(boundary[2]);
        } else {
            // default boundary
            BoundaryLeftBottom = new Point(0, 0);
            BoundaryRightTop = new Point(1000, 1000);
        }
        while (data.hasNext()) {
            // read the next line, it contains the polygon
            try {
                data.nextLine(); // read the 'Polygon x'
                data.nextLine(); // The edges are...
                Edge[] edges = new Edge[4];
                for (int i = 0; i < 4; i++) {

                    edges[i] = readEdge(data.nextLine());
                }
                polygons.add(new Polygon(edges));
            } catch (Exception e) {
            }

        }
    }

    private Edge readEdge(String nextLine) {
        double x1, y1, x2, y2;
        String[] line = nextLine.split(" ");
        String[] point = line[0].split(",");

        x1 = Double.parseDouble(point[0].substring(1, point[0].length()));
        y1 = Double.parseDouble(point[1]);
        x2 = Double.parseDouble(point[2]);
        y2 = Double.parseDouble(point[3].substring(0, point[3].length() - 2));

        return new Edge(new Point(x1, y1), new Point(x2, y2));
    }

    public void drawArea() {

        StdDraw.setScale(BoundaryLeftBottom.x, BoundaryRightTop.x);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (Polygon polygon : polygons) {

            StdDraw.filledPolygon(polygon.Xs(), polygon.Ys());

        }
    }

    @Override
    public Iterator<Polygon> iterator() {
        return polygons.iterator();
    }

    public ArrayList<Point> allAccessiblePoints() {
        int n = size();
        for (int i = 0; i < n; i++) {
            Polygon pol = polygons.get(i);
            for (int j = 0; j < pol.points.length; j++) {
                if (!isPointInPolygons(pol.points[j])) {
                    accessiblePoints.add(pol.points[j]);
                }
            }

        }

        return accessiblePoints;
    }

    public boolean isPointInPolygons(Point point) {
        for (int j = 0; j < size(); j++) {
            if (polygons.get(j).isPointInside(point))
                return true;
        }
        return false;
    }

    public void drawEdges(ArrayList<Edge> edges) {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.0005);
        for (Edge edge : edges) {
            StdDraw.line(edge.start.x, edge.start.y, edge.end.x, edge.end.y);
        }
    }

    public int size() {
        return polygons.size();
    }

    public static void main(String[] args) {

    }

    public void drawPoint(Point point, int color ) {
        if (color  > 0)
            StdDraw.setPenColor(StdDraw.RED);
        else
            StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(0.02);
        StdDraw.point(point.x, point.y);
    }

    public void drawEdgesFromPoint(Point point) {

    }

    public void drawPath(Point[] points) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        for (int i = 1; i < points.length; i++) {
            StdDraw.line(points[i-1].x, points[i-1].y, points[i].x, points[i].y);
        }
    }

}
