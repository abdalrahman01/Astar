package src.area;

import java.util.Iterator;

import lib.StdDraw;


public class Polygon implements Iterable<Edge> {
    final int sides;
    public Edge[] edges;
    public Point[] points;

    public Polygon(int sides) {
        this.sides = sides;
        edges = new Edge[sides];
        points = new Point[sides];
    }

    public Polygon(Edge[] edges) {
        this.sides = edges.length;

        this.edges = new Edge[sides];
        points = new Point[sides];

        for (int i = 0; i < sides; i++) {
            this.edges[i] = edges[i];
            points[i] = edges[i].start;
        }
    }

    public Polygon(Point[] points) {
        sides = points.length;
        edges = new Edge[sides];

        this.points = new Point[sides];
        for (int i = 0; i < sides; i++) {
            edges[i] = new Edge(points[i], points[(i + 1) % sides]);
            this.points[i] = points[i];
        }
    }

    // add sides to the polygon
    public void addEdge(Edge edge) {

        for (int i = 0; i < sides; i++) {
            if (edges[i] == null) {
                edges[i] = edge;
                points[i] = edge.start;
                break;
            }
        }
    }

    public double[] Xs() {
        double[] startsX = new double[sides];
        for (int i = 0; i < sides; i++) {
            startsX[i] = edges[i].start.x;
        }
        return startsX;
    }

    public double[] Ys() {
        double[] startsY = new double[sides];
        for (int i = 0; i < sides; i++) {
            startsY[i] = edges[i].start.y;
        }
        return startsY;
    }

    public boolean isPointInside(Point p) {
        for (Edge edge : edges) {
            if (edge.compareTo(p) <= 0)
                // point is to the right of the edge
                return false;
        }
        return true;

    }

    public boolean isEdgeCrossing(Edge edge) {
        for (Edge e : edges) {
            if (e.isCrossing(edge))
                return true;
            
            
        }
        for (Edge tempE : tempEdges()) {
            if (sides < 4) 
                break;
            if (tempE == null)
                continue;
            if ((edge.start.equals(tempE.start) && edge.end.equals(tempE.end)) || (edge.start.equals(tempE.end) && edge.end.equals(tempE.start)))
                return true;
        }
        return false;
    }

    private Edge[] tempEdges() {
        Edge[] tempEdges = new Edge[sides];
        int index = 0; // index of the tempEdges array
        for (int i = 0; (i < sides); i++) {
            Edge edg1 = new Edge(edges[i].start, edges[(i + 2) % sides].start);
            Edge edg2 = new Edge(edges[i].start, edges[(i + 2) % sides].end);

            // if edg1 not in edges, add it to tempEdges and increment index
            if (!isEdgeInEdges(edg1) && !isEdgeInTempEdges(edg1, tempEdges))
                tempEdges[index++] = edg1;

            if (!isEdgeInEdges(edg2) && !isEdgeInTempEdges(edg2, tempEdges))
                tempEdges[index++] = edg2;
        }

        return tempEdges;

    }

    private boolean isEdgeInTempEdges(Edge edg, Edge[] tempEdges) {
        for (Edge edge : tempEdges) {
            if (edge != null && edge.equals(edg))
                return true;
        }
        return false;
    }

    private boolean isEdgeInEdges(Edge edg) {
        for (Edge edge : edges) {
            if (edge.equals(edg))
                return true;
        }
        return false;
    }

    // return iterator of edges
    public Iterator<Edge> iterator() {
        return new Iterator<Edge>() {
            int index = 0;

            public boolean hasNext() {
                return index < sides;
            }

            public Edge next() {
                return edges[index++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // iterable points
    public Iterable<Point> points() {
        return new Iterable<Point>() {
            public Iterator<Point> iterator() {
                return new Iterator<Point>() {
                    int index = 0;

                    public boolean hasNext() {
                        return index < sides;
                    }

                    public Point next() {
                        return points[index++];
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    public static void main(String[] args) {
        StdDraw.setScale(-100, 100);
        Point a = new Point(-40, 0);
        Point b = new Point(0, 40);
        Point c = new Point(40, 0);
        Point d = new Point(20, -40);
        Point e = new Point(-20, -40);
        Point[] points = new Point[] { a, b, c };
        Polygon poly = new Polygon(points);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.002);
        StdDraw.polygon(poly.Xs(), poly.Ys());
        StdDraw.setPenColor(StdDraw.PINK);
        for (Edge edge : poly.tempEdges()) {
            if (edge == null)
                continue;
        StdDraw.line(edge.start.x, edge.start.y, edge.end.x, edge.end.y);
        }

        Edge f1 = new Edge(new Point(-40, 20), new Point(60, 20));
        Edge f2 = new Edge(new Point(0, 0), new Point(40, 40));
        Edge f3 = new Edge(a, b);
        Edge f4 = new Edge(d, e);
        Edge f5 = new Edge(new Point(0, -40), new Point(0, -80));
        Edge[] fs = new Edge[] { f1, f2, f3, f4, f5 };
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.003);

        for (Edge f : fs) {
            if (!poly.isEdgeCrossing(f))
                StdDraw.setPenColor(StdDraw.GREEN);
            else
                StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(f.start.x, f.start.y, f.end.x, f.end.y);
        }
        System.out.println(poly.isEdgeCrossing(f1));
        System.out.println(poly.isEdgeCrossing(f2));
        System.out.println(poly.isEdgeCrossing(f3));
        System.out.println(poly.isEdgeCrossing(f4));

    }

}
