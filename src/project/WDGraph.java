package project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class WDGraph {

    private int order; // order of the graph (number of nodes)
    private int size; // size of the graph (the number of edges)
    private ArrayList<Object[]> graphList = new ArrayList<>(); // List of edges : [... , [stop_id_1 , stop_id_2 , cost], ...]
    public HashSet<String> nodesToVisit = new HashSet<>();
    private HashMap<String, Object[]> stopsList = new HashMap(); // List of stops : [... , "stop_id": [stop-name, latitude, longitude], ...]

    // Constructor
    public WDGraph() {
        order = 0;
        size = 0;
    }

    // Getters
    public ArrayList<Object[]> getGraphList() {
        return graphList;
    }
    public HashMap<String, Object[]> getStopsList() { return stopsList; }
    public int getOrder(){
        return this.order;
    }
    public int getSize(){
        return this.size;
    }

    public void initNodesToVisit(){
        HashSet<String> nodes = new HashSet<>();
        for (Object[] edge : this.graphList) {
            nodes.add((String) edge[0]);
            nodes.add((String) edge[1]);
        }
        this.nodesToVisit = nodes;
    }

    public void importGraph(String pathName) throws IOException {
        Stream<String> stopTimesStream = Files.lines(Paths.get(pathName + "stop_times.txt"));
        //WDGraph graph = new WDGraph();

        ArrayList<Object[]> stopTimesList = new ArrayList<>();
        stopTimesStream.skip(1)
                .forEach(line -> {
                    stopTimesList.add(new Object[]{
                            line.split(",")[3],
                            Integer.parseInt(line.split(",")[4])
                    });
        });

        ArrayList<Object[]> importedGraph = new ArrayList<>();
        String previousStop = "";
        int previousStopSequence = 0;
        for(Object[] line:  stopTimesList){
            // New potential edge
            final Object[] newEdge = new Object[]{previousStop, line[0], 0.0};

            // If start of a new subway route
            if ((Integer) line[1] < previousStopSequence || line[1].equals(0)){
                previousStop = (String) line[0];
                previousStopSequence = (int) line[1];
            }
            // Else if no edge like this one exists already
            else if (importedGraph.stream().noneMatch(a -> Arrays.equals(a, newEdge))){
                importedGraph.add(new Object[]{previousStop, line[0], 0.0});
                previousStop = (String) line[0];
                previousStopSequence = (int) line[1];
            }
            else {
                previousStop = (String) line[0];
                previousStopSequence = (int) line[1];
            }
        }

        this.size = importedGraph.size();

        // To find the order of the graph
        Set<String> nodes = new HashSet<>();
        for (Object[] edge : importedGraph) {
            nodes.add((String) edge[0]);
            nodes.add((String) edge[1]);
        }
        this.order = nodes.size();
//
//        ArrayList<Double[]> importedGraphDouble = importedGraph.stream()
//                .map(x -> Arrays.stream(x).mapToDouble(Double::new).toArray())
//                .collect(Collectors.toCollection(ArrayList::new));

        this.graphList = importedGraph;
        initNodesToVisit();
    }



    public void addWeightsToGraph(String gtfsDirectoryPath) throws IOException {
        Stream<String> stopsStream = Files.lines(Paths.get(gtfsDirectoryPath + "stops.txt"));

        HashMap<String, Object[]> stopsList = new HashMap<>();
        stopsStream.skip(1)
                .map(line -> line.replace("\"",""))
                .forEach(line -> {
                    stopsList.put(line.split(",")[0],
                            new Object[]{
                            line.split(",")[2],
                            Double.parseDouble(line.split(",")[4]),
                            Double.parseDouble(line.split(",")[5])
                    });
                });
        this.stopsList = stopsList;

        this.graphList.stream()
                .map(edge -> {
            edge[2] = calculateDistanceBetweenPoints(
                    (Double) this.stopsList.get((String) edge[0])[1],
                    (Double) this.stopsList.get((String) edge[0])[2],
                    (Double) this.stopsList.get((String) edge[1])[1],
                    (Double) this.stopsList.get((String) edge[1])[2]
            );
            return edge; })
                .collect(Collectors
                .toCollection(ArrayList::new));
    }

    private Double calculateDistanceBetweenPoints(
            Double x1,
            Double y1,
            Double x2,
            Double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }


    public static List<String> stringArrayListFromStringSet(Set<String> set){
        List<String> list = new ArrayList<>();
        for(String element : set){
            list.add(element);
        }
        return list;
    }

    public List<String> getNeighbours(String node){
        Set<String> neighbours = new HashSet<>();
        for (Object[] edge : graphList) {
            if ( edge[0].equals(node) && nodesToVisit.contains((String) edge[1]))
                neighbours.add((String) edge[1]);
            if (edge[1].equals(node) && nodesToVisit.contains((String) edge[0]))
                neighbours.add((String) edge[0]);
        }
        return stringArrayListFromStringSet(neighbours);
    }

    public List<String> getAccessibleNeighbours(String node){
        Set<String> neighbours = new HashSet<>();
        for (Object[] edge : graphList) {
            if (edge[0].equals(node) && nodesToVisit.contains((String) edge[1]))
                neighbours.add((String) edge[1]);
        }
        return stringArrayListFromStringSet(neighbours);
    }

    public double getCostOfEdge(String node1, String node2){
        for (Object[] edge : graphList)
        {
            if (edge[0].equals(node1) && edge[1].equals(node2))
                return (double) edge[2];
            else if (edge[1].equals(node1) && edge[0].equals(node2))
                return (double) edge[2];
        }
        return 0.0;
    }

    public Double distTo(String start, String dest) throws IOException {
        return (Double) DijkstraSP.DijkstraSP(this, start).get(1).get(dest);
    }

    public void printSP(String start, String dest) throws IOException {
        System.out.println(DijkstraSP.DijkstraSP(this,start).get(0).get(dest));
    }

}
