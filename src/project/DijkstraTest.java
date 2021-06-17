package project;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DijkstraTest {


    public static final WDGraph GRAPH_TEST = new WDGraph();

    // Question 2.1
    @Test
    public void importTest() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");
        System.out.println("Order : " + GRAPH_TEST.getOrder());
        System.out.println("Size : " + GRAPH_TEST.getSize());
        System.out.println(GRAPH_TEST.nodesToVisit);
    }

    // Question 2.2
    @Test
    public void printGraphTest() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");
        GRAPH_TEST.addWeightsToGraph("gtfs/");

        for (int i = 0; i < GRAPH_TEST.getGraphList().size(); i++)
        {
            for (int b = 0; b< GRAPH_TEST.getGraphList().get(i).length; b++)
            {
                System.out.print(GRAPH_TEST.getGraphList().get(i)[b] + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void given_positiveValues_when_verifyNonNegativeTest_then_true() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");
        Assert.assertTrue(DijkstraSP.verifyNonNegative(GRAPH_TEST));
    }


    @Test
    public void given_gtfsDirectory_when_getOrder_then_50() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");
        Assert.assertEquals(50, GRAPH_TEST.getOrder());
    }

    @Test
    public void given_SANL_when_getNeighbours_then_BAYF_COLS() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");

        List<String> neighbours = new ArrayList<>();
        neighbours.add("BAYF");
        neighbours.add("COLS");

        for (String neighbour: neighbours ) {
            Assert.assertTrue(GRAPH_TEST.getNeighbours("SANL").contains(neighbour));
        }
        System.out.println(GRAPH_TEST.getNeighbours("SANL"));
    }

    @Test
    public void print_LAKE_neighbors() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");
        System.out.println(GRAPH_TEST.getNeighbours("LAKE"));
    }

    @Test
    public void given_PCTR_when_getNeighbours_then_PITT_ANTC() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");

        List<String> neighbours = new ArrayList<>();
        neighbours.add("PITT");
        neighbours.add("ANTC");

        for (String neighbour: neighbours ) {
            Assert.assertTrue(GRAPH_TEST.getNeighbours("PCTR").contains(neighbour));
        }
        System.out.println(GRAPH_TEST.getNeighbours("PCTR"));
    }

    // Question 2.2
    @Test
    public void add_weights_to_graph_test() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");
        GRAPH_TEST.addWeightsToGraph( "gtfs/");
        for (int i = 0; i < GRAPH_TEST.getGraphList().size(); i++)
        {
            for (int b = 0; b< GRAPH_TEST.getGraphList().get(i).length; b++)
            {
                System.out.print(GRAPH_TEST.getGraphList().get(i)[b] + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void given_ORIN_and_ROCK_when_getCostOfEdge_then_not_0() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");
        GRAPH_TEST.addWeightsToGraph( "gtfs/");
        Assert.assertNotEquals(0.0, GRAPH_TEST.getCostOfEdge("ORIN","ROCK"),0);
    }


    // Question 3 : Dijkstra algorithm
    @Test
    public void given_FTVL_and_SHAY_when_distTo_then_print() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");
        GRAPH_TEST.addWeightsToGraph( "gtfs/");
        System.out.println(GRAPH_TEST.distTo("FTVL","SHAY"));
    }

    // Question 3 : Dijkstra algorithm
    @Test
    public void test_printSP() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");
        GRAPH_TEST.addWeightsToGraph( "gtfs/");
        GRAPH_TEST.printSP("PLZA","OAKL");
    }


// #############

    // Question 4. SHORTEST PATHS FOR GRAPH CLUSTERING
    @Test
    public void TESTgetListOfStopIds() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");
        GRAPH_TEST.addWeightsToGraph( "gtfs/");
        //GRAPH_TEST.getStopsList().forEach((key, value) -> System.out.println(key + " = " +  value[0] + ", " + value[1] + ", " + value[2]));
        System.out.println(GRAPH_TEST.getListOfStopIds());
    }


    @Test
    public void TESTgetBetweenness() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");
        GRAPH_TEST.addWeightsToGraph( "gtfs/");
        System.out.println(GRAPH_TEST.getBetweennessOfEdge("16TH", "24TH", GRAPH_TEST.getAllShortestPathFromAllNodesToAllNodes()));
        System.out.println(GRAPH_TEST.getBetweennessOfEdge("PLZA", "NBRK", GRAPH_TEST.getAllShortestPathFromAllNodesToAllNodes()));
        System.out.println(GRAPH_TEST.getBetweennessOfEdge("MCAR", "ROCK", GRAPH_TEST.getAllShortestPathFromAllNodesToAllNodes()));
        System.out.println(GRAPH_TEST.getBetweennessOfEdge("MCAR", "19TH", GRAPH_TEST.getAllShortestPathFromAllNodesToAllNodes()));
    }
//738
//282
//800
//1122

    @Test
    public void TESTgetBetweennessOfAllEdges() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");
        GRAPH_TEST.addWeightsToGraph( "gtfs/");
        GRAPH_TEST.getBetweennessOfAllEdges().forEach((key, value) -> System.out.println(key[0] + "-" + key[1] + " : " + value));
    }

    @Test
    public void TESTremoveEdgesWithHighestBetweenness() throws IOException {
        GRAPH_TEST.importGraph("gtfs/");
        GRAPH_TEST.addWeightsToGraph( "gtfs/");
        GRAPH_TEST.removeEdgesWithHighestBetweenness(10)
                .forEach((key, value) -> System.out.println(key[0] + "-" + key[1] + " : " + value));
    }
}
