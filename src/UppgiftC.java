package src;

import java.util.Arrays;
import java.util.Scanner;

import src.area.Area;
import src.area.Point;
import src.graph.VisibilityGraph;

public class UppgiftC {
    public static void firstOne() {
        Scanner data = new Scanner(System.in);
        Area area = new Area(data);
        VisibilityGraph vG = new VisibilityGraph(area);
        Point green = new Point(100, 900);
        Point red = new Point(700, 50);
        vG.drawArea();
        int a = vG.addPoint(green, 0);
        int b = vG.addPoint(red, 1);
        vG.drawEdges(vG.edges());
        AStar aStar = new AStar(vG);
        int[] path = aStar.A_Start(b, a);
        vG.drawPath(path);
        System.out.println(Arrays.toString(path));

    }
    // public static void secondOne
    public static void main(String[] args) {
        firstOne();
    }
}
