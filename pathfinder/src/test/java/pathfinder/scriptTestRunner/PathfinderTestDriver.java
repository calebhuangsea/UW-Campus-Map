/*
 * Copyright (C) 2022 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.scriptTestRunner;

import graph.Graph;
import marvel.MarvelPaths;
import org.junit.Rule;
import org.junit.rules.Timeout;
import pathfinder.ShortestPath;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested
    private final PrintWriter output;
    private final BufferedReader input;
    private final Map<String, Graph<String, Double>> graphs = new HashMap<>();

    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        // TODO: Implement this, reading commands from `r` and writing output to `w`.
        // See GraphTestDriver as an example.
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    // Leave this method public
    public void runTests() throws IOException {
        // TODO: Implement this.
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }


    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new Graph<String, Double>());
        output.println("created graph " + graphName);
    }

    private void findPath(List<String> arguments) {
        if(arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }
        String graphName = arguments.get(0);
        String start = arguments.get(1);
        String end = arguments.get(2);
        findPath(graphName, start, end);
    }

    private void findPath(String graphName, String start, String end) {
        Graph<String, Double> graph = graphs.get(graphName);
        boolean unknown = false;
        if (!graph.allNodes().contains(start)) {
            output.println("unknown: " + start);
            unknown = true;
        }
        if (!graph.allNodes().contains(end)) {
            output.println("unknown: " + end);
            unknown = true;
        }
        if (unknown) {return;}
        Path<String> shortestPath = ShortestPath.findShortestPath(graph, start, end);
        output.println("path from " + start + " to " + end + ":");
        if (shortestPath == null) {
            output.println("no path found");
        } else if (shortestPath.getStart().equals(shortestPath.getEnd())) {
            output.println("total cost: 0.000");
        } else {
            double sum = 0.00;
            Iterator<Path<String>.Segment> iterator = shortestPath.iterator();
            while (iterator.hasNext()) {
                Path<String>.Segment next = iterator.next();
                sum += next.getCost();
                output.println(next.getStart() + " to " + next.getEnd() + " with weight " + String.format("%.3f", next.getCost()));
            }
            output.println("total cost: " + String.format("%.3f", sum));
        }
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Graph<String, Double> graph = graphs.get(graphName);
        graph.addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);
        double label = Double.parseDouble(edgeLabel);
        addEdge(graphName, parentName, childName, label);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {
        Graph<String, Double> graph = graphs.get(graphName);
        graph.addEdge(parentName, childName, edgeLabel);

        output.println("added edge " + String.format("%.3f", edgeLabel) + " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        Graph<String, Double> graph = graphs.get(graphName);
        TreeSet<String> nodes = new TreeSet<>(graph.allNodes());
        output.print(graphName + " contains:");
        for (String node : nodes) {
            output.print(" " + node);
        }
        output.println("");
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        Graph<String, Double> graph = graphs.get(graphName);
        List<Graph<String, Double>.Edge> edges = graph.findChildren(parentName);
        EdgeComparator edgeComparator = new EdgeComparator();
        edges.sort(edgeComparator);
        output.print("the children of " + parentName + " in " + graphName + " are:");
        for (Graph<String, Double>.Edge edge : edges) {
            output.print(" "  + edge.getDestination() + "(" + edge.getLabel() + ")");
        }
        output.println();
    }

    /**
     * Edge Comparator with Graph<String, String> type
     * Compare two edges' child nodes and labels
     */
    private static class EdgeComparator implements Comparator<Graph<String, Double>.Edge> {
        @Override
        public int compare(Graph<String, Double>.Edge e1, Graph<String, Double>.Edge e2) {
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

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
