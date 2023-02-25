package graph;

import java.util.*;


/**
 * <b>Graph</b> represents a mutable, one-direction graph
 *
 * A graph have a set of nodes, every node has a distinct label value,
 * nodes can have edges connect to other nodes, every outgoing edge for one node has distinct label
 * An edge has direction, from parent node to child node,
 * A node can have no edge, which means it has no neighbor,
 * A node can have edge points to itself,
 * A graph can be empty.
 */
public class Graph<N, T> {

    /**
     * An adjacent list implemented by map to store node and its outgoing edges
     */
    private Map<N, Set<Edge>> nodeList;
    private final boolean DEBUG = false;

    // Abstract Function:
    // A Graph represents a directed graph having unique labeled nodes, nodes
    // connect to each other with edges.
    // A Graph looks like {node1: edge1, edge2; node2: edge1, edge2, edge3....}
    // A node can have multiple outgoing edges with unique labels.
    // A graph is empty if there is no node in this graph.

    // Representation invariant for every Graph g:
    // Every node in g has unique label
    // Every edge with same parent and child nodes has unique label
    // Every edge has parent node and child node
    // nodeList != null
    // Every key and value in NodeList != null

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert (nodeList != null): "nodeList is null";
        if (DEBUG) {
            Set<N> nodes = nodeList.keySet();
            for (N node : nodes) {
                assert (nodeList.get(node) != null) : "entry in nodeList is null";
            }
        }
    }

    /**
     * @spec.effects construct an empty Graph
     */
    public Graph() {
        nodeList = new HashMap<>();
        checkRep();
    }

    /**
     * returns all nodes in this graph
     * @return a set containing all nodes
     */
    public Set<N> allNodes() {
        checkRep();
        return nodeList.keySet();
    }

    /**
     * find outgoing edges
     * @param node label for the parent node we want to check
     * @return a List containing outgoing edges for given node
     * @spec.requires node can't be null
     * @throws IllegalArgumentException if given node doesn't exist in our graph
     */
    public List<Graph<N, T>.Edge> findChildren(N node) {
        checkRep();
        if (!nodeList.containsKey(node)) {
            throw new IllegalArgumentException("Node not found in this graph!");
        }
        List<Edge> edges = new ArrayList<>(nodeList.get(node));
        checkRep();
        return edges;
    }

    /**
     * add a node to our graph if no other node exist
     * @param node label for the node we want to add
     * @spec.effects add a new node to our graph
     * @spec.modifies this
     * @spec.requires node can't be null
     * @return true if added node, false otherwise
     */
    public boolean addNode(N node) {
        checkRep();
        if (nodeList.containsKey(node)) {
            return false;
        }
        nodeList.put(node, new HashSet<>(){});
        checkRep();
        return true;
    }

    /**
     * add a new edge between two node if no edge is unique in parent node and child node
     * @param parent label for the parent node of the new edge
     * @param child label for the child node of the new edge
     * @param label label for the new edge
     * @spec.effects add a new edge to our graph, create parent/child node if it doesn't exist
     * @spec.requires parent, child, label can't be null
     * @return true if added edge, false otherwise
     */
    public boolean addEdge(N parent, N child, T label) {
        checkRep();
        if (!nodeList.containsKey(parent)) {
            addNode(parent);
        }

        if (!nodeList.containsKey(child)) {
            addNode(child);
        }
        Edge edge = new Edge(parent, child, label);
        if (nodeList.get(parent).contains(edge)) {
            return false;
        }
        nodeList.get(parent).add(edge);
        checkRep();
        return true;
    }

    /**
     * <b>Edge</b> represents an immutable edge between two nodes,
     *
     * An edge has start node and destination node,
     * An edge points from parent(start) node to child(destination) node,
     * Edge's label is unique between two nodes,
     * An edge has its own label,
     * An edge can have the same start node and destination node,
     * An outgoing edge of a node is edge from node to its child,
     */
    public class Edge {
        private N start;
        private N destination;
        private T label;

        // Abstract Function:
        // An Edge represents an edge between two nodes
        // An Edge looks like [parent(start) ---Edge(label)--- child(destination)]
        // An edge must have a parent node and child node
        // An edge can have the same parent node and child node
        // An edge has a label

        // Rep Invariant
        // An Edge's parent node != null
        // An Edge's child node != null
        // An Edge's label != null

        /**
         * Throws an exception if the representation invariant is violated.
         */
        private void checkRep() {
            assert (start != null): "parent node is null";
            assert (destination != null): "child node is null";
            assert (label != null): "label is null";
        }

        /**
         * @param start start Node for this edge
         * @param destination Node for this edge
         * @param label label for this edge
         * @spec.effects construct a new edge
         * @spec.requires node can't be null
         */
        public Edge(N start, N destination, T label) {
            this.start = start;
            this.destination = destination;
            this.label = label;
            checkRep();
        }

        /**
         * get start node
         * @return return start node of this edge
         */
        public N getStart() {
            checkRep();
            return this.start;
        }

        /**
         * get Destination node
         * @return return destination node of this edge
         */
        public N getDestination() {
            checkRep();
            return this.destination;
        }

        /**
         * get label
         * @return return label of this edge
         */
        public T getLabel() {
            checkRep();
            return this.label;
        }

        /**
         * Standard equality operation.
         *
         * @param o the object to be compared for equality
         * @return true if and only if 'obj' is an instance of a Edge and 'this' and 'o' represent the
         * same edge with the same label, parent and child
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!(o instanceof Graph<?, ?>.Edge)) {
                return false;
            }
            Graph<?, ?>.Edge edge = (Graph<?, ?>.Edge) o;
            return start.equals(edge.start) && destination.equals(edge.destination) && label.equals(edge.label);
        }

        /**
         * Standard hashCode function.
         * @return an int that all objects equal to this will also return
         */
        @Override
        public int hashCode() {
            return Objects.hash(start, destination, label);
        }
    }
}
