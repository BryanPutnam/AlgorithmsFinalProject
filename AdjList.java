/* 
Author: Bryan Putnam
ID: 49235478
Course: CS 7350
*/

public class AdjList { 
    private int numVertices;
    private AdjNode[] nodeList;
    private int[] degreeList;
    private int numEdges;

    public AdjList(int v) {
        numVertices = v;
        nodeList = new AdjNode[numVertices];
        degreeList = new int[numVertices];
        numEdges = 0;
    }

    public void addEdge(int source, int destination) {
        AdjNode destNode = new AdjNode(destination);
        destNode.setNextPtr(nodeList[source]);
        nodeList[source] = destNode;
        degreeList[source] += 1;

        AdjNode sourceNode = new AdjNode(source);
        sourceNode.setNextPtr(nodeList[destination]);
        nodeList[destination] = sourceNode;
        degreeList[destination] += 1;
    }

    public Boolean containsEdge(int source, int destination) {
        AdjNode currNode = nodeList[source];
        while (currNode != null) {
            if (currNode.getVertex() == destination) {
                return true; // Found vertice in list of connections
            }   
        }
        return false; // Not found or list is empty
    }
}