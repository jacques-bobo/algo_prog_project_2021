package project;

import java.util.*;

public class BFSShortestPath {
	public static ArrayList<String> doBFSShortestPath(WDGraph graph, String source, String dest) {

		if (source.equals(dest))
			return null;

		HashMap<String, Boolean> visited = new HashMap<>();
		Queue<String> queue = new LinkedList<>();
		Stack<String> pathStack = new Stack<>();

		queue.add(source);
		visited.put(source, true);

		while (!queue.isEmpty()) {

			String u = queue.poll();
			pathStack.add(u);
			ArrayList<String> adjList = (ArrayList<String>) graph.getNeighbours(u);

			for (String v : adjList) {
				if (!visited.containsKey(v)) {
					queue.add(v);
					visited.put(v, true);
				}
			}
		}

		//To find the path
		ArrayList<String> shortestPathList = new ArrayList<>();
		String node, currentSrc = dest;

		while (!pathStack.peek().equals(dest)) {
			pathStack.pop();
		}

		while (!pathStack.isEmpty()) {
			node = pathStack.pop();
			if (graph.getNeighbours(node).contains(currentSrc)) {
				shortestPathList.add(currentSrc);
				currentSrc = node;
			}
			if (node.equals(source)) {
				shortestPathList.add(source);
			}
		}
		Collections.reverse(shortestPathList);
		return shortestPathList;
	}
}