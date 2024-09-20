package src.graph;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import src.area.Area;
import src.area.Edge;
import src.area.Point;

public class VisibilityGraph {
    private ArrayList<Point> Points;
    private ArrayList<Edge> edges;
    private Area area;
    private Graph G;

    public VisibilityGraph(Area area) {
        edges = new ArrayList<Edge>();
        this.area = area;
        Points = area.allAccessiblePoints();
        G = new Graph(Points.size() + 2); // +2 for the two extra points

        addEveryEdge();
        // area.drawArea();
        // area.drawEdges(edges);
    }

    /**
     * 
     * @param point
     * @param color if color < 0 => color is green, else color is red
     */
    public int addPoint(Point point, int color) {
        if (area.isPointInPolygons(point))
            return -1;

        area.drawPoint(point, color);
        Points.add(point);

        int index = Points.indexOf(point);

        ArrayList<Edge> newEdges = new ArrayList<Edge>();
        for (int i = 0; i < Points.size() - 1; i++) {
            Edge temp = createEdge(i, index);

            if (temp != null)
                newEdges.add(temp);
        }

        // area.drawEdges(newEdges);
        // area.drawPoint(point, 0);
        return index;
    }

    public Edge createEdge(int i, int j) {
        Edge temp = new Edge(Points.get(j), Points.get(i));
        if (!isEdgeCrossingPolygons(temp)) {
            edges.add(temp);
            G.addEdge(i, j);
            return temp;
        }
        return null;
    }

    private void addEveryEdge() {
        int n = Points.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                createEdge(i, j);
            }

        }

    }

    /**
     * check if edge crosses any polygon
     * 
     * @param edge
     * @return true if edge crosses
     */
    private boolean isEdgeCrossingPolygons(Edge edge) {
        for (int i = 0; i < area.polygons.size(); i++) {
            if (area.polygons.get(i).isEdgeCrossing(edge))
                return true;
        }

        return false;
    }

    // return size of the graph
    public int size() {
        return G.V();
    }

   
    public double dist(int start, int goal) {
        return Points.get(start).distance(Points.get(goal));
    }

    public Iterable<Integer> adj(int current) {
        return G.adj(current);
    }
    public void drawArea(){
        area.drawArea();
    }
    public void drawEdges(ArrayList<Edge> edges){
        area.drawEdges(edges);
    }
    public void drawPath(int[] path) {
        if (path == null)
            return;
        int n = path.length;
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = Points.get(path[i]);
        }
        area.drawPath(points);

    }

    public Point getPoint(int index) {
        return Points.get(index);
    }

    public static void main(String[] args) {
        Scanner data = new Scanner(System.in);
        Area area = new Area(data);
        VisibilityGraph vG = new VisibilityGraph(area);
        Point green = new Point(100, 200);
        Point red = new Point(900, 800);
        int a = vG.addPoint(green, 0);
        int b = vG.addPoint(red, 1);

    }

    public ArrayList<Edge> edges() {
        return edges;
    }

}
