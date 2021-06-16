package project;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class BFSTest {
    public static final WDGraph GRAPH_TEST = new WDGraph();

    @Test
    public void importTest() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");
        System.out.println("Order : " + GRAPH_TEST.getOrder());
        System.out.println("Size : " + GRAPH_TEST.getSize());
        System.out.println(GRAPH_TEST.nodesToVisit);
    }

    @Test
    public void bfsTest() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");

        String source = "LAKE";
        String dest = "WCRK";

        ArrayList<String> shortestPathList =  BFSShortestPath.doBFSShortestPath(GRAPH_TEST, source, dest);

        System.out.print("[ ");
        shortestPathList.forEach(node -> System.out.println(node + ","));
        System.out.print("]");

    }
}
