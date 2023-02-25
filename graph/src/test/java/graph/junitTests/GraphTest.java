package graph.junitTests;

import graph.Graph;
import graph.Graph.Edge;
import org.junit.Test;

import static org.junit.Assert.*;


public class GraphTest {

    Graph<String, String> graph = new Graph<>();
    Graph<String, String>.Edge edge = graph.new Edge("n1", "n2", "e1");

    //Duplicate node label in graph
    @Test
    public void testDuplicateNode() {
        Graph<String, String> graph = new Graph<String, String>();
        graph.addNode("n1");
        assertFalse(graph.addNode("n1"));
    }

    //Duplicate edge label between two nodes
    @Test
    public void testDuplicateEdge() {
        Graph<String, String> graph = new Graph<String, String>();
        graph.addNode("n1");
        graph.addNode("n2");
        graph.addNode("n3");
        graph.addEdge("n1", "n2", "e1");
        assertFalse(graph.addEdge("n1", "n2", "e1"));
    }

    //Node not exist in graph
    @Test(expected = IllegalArgumentException.class)
    public void testNodeNotExist() {
        Graph<String, String> graph = new Graph<String, String>();
        graph.findChildren("n1");
    }

    //get edge label
    @Test
    public void testGetLabel() {
        assertEquals("e1", edge.getLabel());
    }

    //get Parent node
    @Test
    public void testGetStart() {
        assertEquals("n1", edge.getStart());
    }

    //get child node
    @Test
    public void testGetDestination() {
        assertEquals("n2", edge.getDestination());
    }
}
