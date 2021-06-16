package project;

import java.io.IOException;
import java.util.*;

public class DijkstraSP {


    public static void main(String[] args) throws IOException {
        WDGraph GRAPH = new WDGraph();

        GRAPH.importGraph("gtfs/");
        DijkstraSP(GRAPH,"LAKE").get(0).forEach((key, value) -> System.out.println(key + " | " + value ));
    }


    public static boolean verifyNonNegative(WDGraph graph) {
        ArrayList<Object[]> graphList = graph.getGraphList();
        for (Object[] objects : graphList) {
            if ((Double) objects[2] < 0.0)
                return false;
        }
        return true;
    }

    public static HashMap<String, Double> createHashMapWithHightValuesExceptForOneNode(WDGraph graph, String OneNode){
        HashMap<String, Double> hashMap = new HashMap<>();
        for (String node : graph.nodesToVisit ){
            hashMap.put(node, 9999.9);
        }
        hashMap.put(OneNode, 0.0);
        return hashMap;
    }

    public static List<HashMap> DijkstraSP(WDGraph graph, String startingNode) throws IOException {

        graph.initNodesToVisit();

        if (!verifyNonNegative(graph)){
            System.out.println("One of the distances is negative");
            return null;
        }

        int order = graph.getOrder();
        HashMap<String, Double> distancesOfEachNodesFromStart = createHashMapWithHightValuesExceptForOneNode(graph, startingNode);
        HashMap<String, List<String>> pathOfEachNodesFromStart = createHashMapWithPaths(graph, startingNode);

        String currentNode = startingNode;
        graph.nodesToVisit.remove(currentNode);

        while (graph.nodesToVisit.size() > 0){
            Double valueOfCurrentNode = distancesOfEachNodesFromStart.get(currentNode);
            List<String> neighborsToVisit = graph.getAccessibleNeighbours(currentNode);
            //System.out.println(currentNode + " : " + neighborsToVisit);
            List<Double> distancesOfNeighbors = new ArrayList();
            for (String neighbor : neighborsToVisit){
                distancesOfNeighbors.add(graph.getCostOfEdge(currentNode,neighbor));
            }
            //System.out.println(distancesOfNeighbors);

            for (String neighbor : neighborsToVisit) {
                Double currentValueOfNeighbor = distancesOfEachNodesFromStart.get(neighbor);
                Double distanceBetweenNeighborAndCurrentNode = distancesOfNeighbors.get(neighborsToVisit.indexOf(neighbor));
                if (currentValueOfNeighbor > distanceBetweenNeighborAndCurrentNode + valueOfCurrentNode){
                    distancesOfEachNodesFromStart.put(neighbor, distanceBetweenNeighborAndCurrentNode + valueOfCurrentNode);
                    List<String> newPath = new ArrayList(pathOfEachNodesFromStart.get(currentNode));
                    newPath.add(neighbor);
                    pathOfEachNodesFromStart.put(neighbor, newPath);
                }
            }

            graph.nodesToVisit.remove(currentNode);

            // Find the next node
            String nextNode = "";
            // Initialization of the minimum distance
            double minimumDistance = 999.9;

            for (String neighbor : graph.nodesToVisit){
                if(distancesOfEachNodesFromStart.get(neighbor) < minimumDistance){
                    minimumDistance = distancesOfEachNodesFromStart.get(neighbor);
                    nextNode = neighbor;
                }
            }

            currentNode = nextNode;
        }
        return new ArrayList<>(Arrays.asList(pathOfEachNodesFromStart, distancesOfEachNodesFromStart));
    }

    private static HashMap<String, List<String>> createHashMapWithPaths(WDGraph graph, String startingNode) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (String node : graph.nodesToVisit){
            List<String> path = new ArrayList<>();
            path.add(startingNode);
            hashMap.put(node, path);
        }
        return hashMap;
    }
}