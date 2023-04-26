/* 
Author: Bryan Putnam
ID: 49235478
Course: CS 7350
*/

public class AdjNode {
    private int vertex;
    private AdjNode nextPtr;
    private int degree;
    private int color;

    public AdjNode(int vertex) {
        this.vertex = vertex;
        nextPtr = null;
        degree = 0;
        color = 0;
    }

    /*
     * SETTER METHODS (nextPtr, degree, color)
     */

    public void setNextPtr(AdjNode node) {
        nextPtr = node;
    }

    public void setDegree(int degree) {
        this.degree = degree;
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

    public int getDegree() {
        return degree;
    }

    public int getColor() {
        return color;
    }

}