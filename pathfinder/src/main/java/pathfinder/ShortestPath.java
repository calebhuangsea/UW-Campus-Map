package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * Find the shortest path in a graph between two nodes using Dijkstra's Algorithm
 */
public class ShortestPath {
    /**
     * find the shortest path between two nodes using Dijkstra's Algorithm
     * @param graph graph where we find to path in
     * @param start start node
     * @param end end node
     * @param <N> Node Type
     * @return return a Path containing the shortest path from start node to end node
     *          return a Path containing only the start node if start == end
     *          return null if no path is found between start node and end node
     *          return the first shortest path we find if there are more than one
     *          path with the same weight
     * @spec.requires graph != null; start != null; end != null; every edge added to the graph is positive
     */
    public static <N> Path<N> findShortestPath(Graph<N, Double> graph, N start, N end) {
        Path<N> path = new Path<N>(start);
        Queue<Path<N>> active = new PriorityQueue<Path<N>>();
        Set<N> finished = new HashSet<N>();
        active.add(path);
        while (!active.isEmpty()) {
            Path<N> minPath = active.remove();
            N minDest = minPath.getEnd();
            if (minDest.equals(end)) {
                return minPath;
            }
            if (finished.contains(minDest)) {
                continue;
            }
            List<Graph<N, Double>.Edge> children = graph.findChildren(minDest);
            for (Graph<N, Double>.Edge child : children) {
                if (!finished.contains(child.getDestination())) {
                    Path<N> newPath = minPath.extend(child.getDestination(), child.getLabel());
                    active.add(newPath);
                }
            }
            finished.add(minDest);

        }
        return null;
    }
}

