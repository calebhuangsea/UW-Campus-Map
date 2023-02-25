package marvel;

import graph.Graph;

import java.util.*;

/**
 * MarvelPaths represents a social network of the marvel universe by reading
 * a csv file. The marvel social network is represented using a graph, where
 * each character is a node and their relationship is an edge between them.
 * MarvelPaths also allows user to find the shortest path(relationship) between two nodes(characters)
 */
public class MarvelPaths {

    //This is not an ADT

    /**
     * allows user to input two names of characters in the marvel universe and
     * will print the shortest relationship between them.
     * @param args unused parameter
     */
    public static void main(String[] args) {
        Graph<String, String> graph = loadGraph("marvel.csv");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("================WELCOME TO MARVEL UNIVERSE=================================================================");
            System.out.println("Please enter two characters separate by ',' in order to find shortest path between them, type QUIT to stop");
            String s = scanner.nextLine().trim();
            if (s.equals("QUIT")) {break;}
            String[] characters = s.split(",");
            if (characters.length != 2) {
                System.out.println("Please make sure you enter in the right format!");
                continue;
            }
            String start = characters[0].trim();
            String end = characters[1].trim();
            List<Graph<String, String>.Edge> path = findPath(start, end, graph);
            if (path == null) {
                System.out.println("Sorry, no relation found between " + start + "and " + end);
            } else if (path.isEmpty()) {
                System.out.println("Why do you want to find relation between the same person???");
            } else if (path.get(0).getLabel().equals("unknown")) {
                for (Graph.Edge edge : path) {
                    System.out.println("Oops! " + edge.getStart() + " is not a hero yet :(");
                }
            } else {
                System.out.println("RELATION from " + start + " to " + end + ":");
                for (Graph.Edge edge : path) {
                    System.out.println(edge.getStart() + " to " + edge.getDestination() + " in <<" + edge.getLabel() + ">>");
                }
            }
        }
    }

    /**
     * construct and return a graph with data read from a csv file
     * where each line in the csv file should only contain {node, edge}
     * @param filename given file we need to read and load from
     * @return a Graph with loaded information from the given file
     * @spec.requires filename is not null
     */
    public static Graph<String, String> loadGraph(String filename) {
        Graph<String, String> marvelGraph = new Graph<String, String>();
        List<Map<String, List<String>>> maps = MarvelParser.parseData(filename);
        Map<String, List<String>> characters = maps.get(0);
        Map<String, List<String>> books = maps.get(1);
        Set<String> characterSet = characters.keySet();
        for (String character : characterSet) {
            //get all books for this character
            List<String> characterToBooks = characters.get(character);
            marvelGraph.addNode(character);
            for (String book : characterToBooks) {
                //find all character that appear in this book
                List<String> bookToCharacters = books.get(book);
                for (String toCharacter : bookToCharacters) {
                    if (!toCharacter.equals(character)) {
                        marvelGraph.addEdge(character, toCharacter, book);
                    }
                }
            }
        }
        return marvelGraph;
    }

    /**
     * return a list of edges that represents the shortest and lexicographical least path
     * between two nodes. We use BFS to find the shortest path.
     * @param start the start node
     * @param end the end node
     * @param graph the graph where we want to find path in
     * @return a List contains a list of Edge that represents the shortest and lexicographical least path
     *      If start / end node doesn't exist: edge label in the list will be unknown with both start and end node
     *      are the unknown node label.
     *      If start == end: return an empty list
     *      If no path found between two nodes: return null
     *      If there is the shortest path between two nodes,
     *          return an shortest and lexicographical ordered list contains edge from start node to end node
     * @spec.requires start is not null, end is not null, graph is not null and graph is not empty
     */
    public static List<Graph<String,String>.Edge> findPath(String start, String end, Graph<String, String> graph) {
        List<Graph<String,String>.Edge> paths = new ArrayList<>();
        // if there are not exist node
        boolean notExist = false;
        Graph<String, String> graph1 = new Graph<>();
        if (!graph.allNodes().contains(start)) {
            paths.add(graph1.new Edge(start, start, "unknown"));
            notExist = true;
        }
        if (!graph.allNodes().contains(end)) {
            paths.add(graph1.new Edge(end, end, "unknown"));
            notExist = true;
        }
        // check if there are not exist nodes
        if (notExist) {
            return paths;
        }
        // visited node
        Queue<String> queue = new LinkedList<>();
        // edges from start to each node
        Map<String, List<Graph<String,String>.Edge>> m = new HashMap<>();
        queue.add(start);
        m.put(start, paths);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            // current node is the end and return
            if (current.equals(end)) {
                return m.get(end);
            }
            // get all children and sort
            List<Graph<String,String>.Edge> edges = graph.findChildren(current);
            EdgeComparator edgeComparator = new EdgeComparator();
            edges.sort(edgeComparator);
//            edges.sort(Comparator.comparing(Graph.Edge::getDestination).thenComparing(Graph.Edge::getLabel));
            for (Graph<String,String>.Edge edge : edges) {
                // if child node is not in m map
                if (!m.containsKey(edge.getDestination())) {
                    List<Graph<String,String>.Edge> edgesToCurrent = m.get(current);
                    List<Graph<String,String>.Edge> edgesToChild = new ArrayList<>(edgesToCurrent);
                    edgesToChild.add(edge);
                    m.put(edge.getDestination(), edgesToChild);
                    queue.add(edge.getDestination());
                }
            }
        }
        return null;
    }

    /**
     * Edge Comparator with Graph<String, String> type
     * Compare two edges' child nodes and labels
     */
    private static class EdgeComparator implements Comparator<Graph<String, String>.Edge> {
        @Override
        public int compare(Graph<String, String>.Edge e1, Graph<String, String>.Edge e2) {
            String destination1 = e1.getDestination();
            String destination2 = e2.getDestination();
            if (destination1.compareTo(destination2) > 0) {
                return 1;
            } else if (destination1.compareTo(destination2) < 0) {
                return -1;
            } else {
                return e1.getLabel().compareTo(e2.getLabel());
            }
        }
    }
}
