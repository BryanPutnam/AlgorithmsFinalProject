/* 
Author: Bryan Putnam
ID: 49235478
Course: CS 7350
*/

public class AdjNode {
    private int vertex;
    private AdjNode nextPtr;
    private int color;

    public AdjNode(int vertex) {
        this.vertex = vertex;
        nextPtr = null;
        color = 0;
    }

    /*
     * SETTER METHODS (nextPtr, degree, color)
     */

    public void setNextPtr(AdjNode node) {
        nextPtr = node;
    }

    public void setColor(int color) {
        this.color = color;
    }

    /*
     * GETTER METHODS (vertex, nextPtr, degree, color)
     */

    public int getVertex() {
        return vertex;
    }

    public AdjNode getNextPtr() {
        return nextPtr;
    }

    public int getColor() {
        return color;
    }

}