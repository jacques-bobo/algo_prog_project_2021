package kQuickestPaths;

import org.junit.Test;

import java.io.IOException;

public class KQuickestPathsTest {
    DiGraph GRAPH_TEST = new DiGraph();

    @Test
    public void importSimpleTest() throws IOException {
        GRAPH_TEST.importSimpleGraph("Simple_KQuickest_Graph.txt");
        System.out.println("Order : " + GRAPH_TEST.getOrder());
        System.out.println("Size : " + GRAPH_TEST.getSize());
        System.out.println(GRAPH_TEST.nodes);
    }

    @Test
    public void initCapacitiesTest() throws IOException {
        GRAPH_TEST.importSimpleGraph("Simple_KQuickest_Graph.txt");
        GRAPH_TEST.initCapacities();
        GRAPH_TEST.getCapacities().forEach((key, value) -> System.out.println("["+key[0]+","+key[1]+","+key[2]+"]" + " : " + value));
        Object[] objectTest = new Object[]{5,6,1.0};
        System.out.println(GRAPH_TEST.getCapacities().get(objectTest));
    }
}
