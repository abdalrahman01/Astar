package src;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

import lib.MaxPQ;
import lib.StdDraw;
import src.area.Area;
import src.area.Edge;
import src.area.Point;
import src.area.Polygon;
import src.graph.VisibilityGraph;

public class AStar {
    // public int[] reconstructPath()
    private int[] cameFrom; // cameFrom[n]: node before n, that is in the path (cheapest) from start to n  
    private double[] gScore; // gScore[n]: distance (bird) from start to n
    private double[] fScore; // fScore[n]: gScore[n] + h(n)
    private int V;
    private VisibilityGraph vG;
    private MaxPQ<Integer> openSet;
    private Polygon closedArea; 
    
    public AStar(VisibilityGraph vG) {
        this(vG, null);
    }

    public AStar(VisibilityGraph vG, Polygon closedArea) {
        this.closedArea = closedArea;

        this.vG = vG;
        V = vG.size();
        cameFrom = new int[V];
        gScore = new double[V];
        fScore = new double[V];

        for (int i = 0; i < V; i++) {
            gScore[i] = Double.POSITIVE_INFINITY;
            fScore[i] = Double.POSITIVE_INFINITY;
            cameFrom[i] = -1;                       // acts like a null
        }

        openSet = new MaxPQ<Integer>();

    }
    public double h(int n, int goal) {
        return vG.dist(n, goal);
    }
    public double d(int current, int neighbor) {
        return vG.dist(current, neighbor);
    }
    public int[] A_Start(int start, int goal) {
        
        if (start < 0 ||goal < 0){
            return null;
        }


        openSet.insert(-start);


        gScore[start] = 0;

        fScore[start] =  gScore[start] + h(start, goal); // gScore[start] = 0!!


        while(!openSet.isEmpty()){
            int current = -openSet.delMax();
            if(current == goal){
                return reconstructPath(cameFrom, current);
            }

            for(int neighbor : vG.adj(current)){

                double tentativeGScore = gScore[current] + d(current, neighbor);
                if(tentativeGScore < gScore[neighbor] && !isCrossingClosedArea(current, neighbor)){
                   recordNewPath(neighbor, current, tentativeGScore, goal);
                }
            }
        }

        return null;
    }

    private boolean isCrossingClosedArea(int current, int neighbor) {
        if (closedArea == null)
            return false;
        Point a = vG.getPoint(current);
        Point b = vG.getPoint(neighbor);
        Edge ab = new Edge(a, b);
        return closedArea.isEdgeCrossing(ab);
    }
    private int[] reconstructPath(int[] cameFrom, int current) {
        Stack<Integer> total_path = new Stack<Integer>();
        total_path.push(current);
        for (int i = 0; i < V; i++) {
            if (cameFrom[current] >= 0){
                current = cameFrom[current];
                total_path.push(current);
            }
        }
        int n = total_path.size();
        int[] total = new int[n];
        for (int i = 0; i < n; i++) {
            total[i] = total_path.pop();
        }

        return total;
    }
    private void recordNewPath(int neighbor, int current, double tentativeGScore, int goal) {
        cameFrom[neighbor] = current;
        gScore[neighbor] = tentativeGScore;
        fScore[neighbor] = gScore[neighbor] + h(neighbor, goal);
        if (!openSet.contains(-neighbor)) {
            openSet.insert(-neighbor);
        }
    }

 
    public static void main(String[] args) {
        Scanner data = new Scanner(System.in);
        Area area = new Area(data);
        VisibilityGraph vG = new VisibilityGraph(area);
        Point green = new Point(100, 900);
        Point red = new Point(700, 50);
        vG.drawArea();

        int a = vG.addPoint(green, 0);
        int b = vG.addPoint(red, 1);

        // Polygon closedArea = new Polygon(new Point[]{new Point(400, 50), new Point(200, 300), new Point(950, 300), new Point(700, 550)});
        // StdDraw.setPenColor(StdDraw.ORANGE);
        // StdDraw.setPenRadius(0.005);
        // StdDraw.polygon(closedArea.Xs(), closedArea.Ys());
        AStar aStar = new AStar(vG);
        // AStar aStar = new AStar(vG);
        int[] path = aStar.A_Start(b, a);
        vG.drawPath(path);
        System.out.println(Arrays.toString(path));

    }
}
