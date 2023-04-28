/* 
Author: Bryan Putnam
ID: 49235478
Course: CS 7350

INFO: 

Contains code for Part II of project
*/

import java.io.File;
import java.lang.System;
import java.util.Scanner; 

public class part_2 {
    private static int vertices; 
    private static int edges;
    private static String graphType;
    private static String distroType;
    private static int degree; 
    private static long startTime; 
    private static long endTime; 
    private static long totalTime; 

    private static AdjList adjList;
    
    public static void main(String[] args) {
        parseInput(args[0]);
    }

    public void smallestLastOrdering() { // REQUIRED
        startTimer(); 
        // CODE BLOCK
        endTimer(); 
        totalTime = (endTime - startTime); 
        
        
    }

    public void smallestOriginalDegree() { // REQUIRED
        startTimer(); 
        // CODE BLOCK
        endTimer(); 
        totalTime = (endTime - startTime);
    }

    public void uniformRandomOrdering() { // REQUIRED
        startTimer(); 
        // CODE BLOCK
        endTimer(); 
        totalTime = (endTime - startTime);
    }

    public void largestFirstOrdering() { // YOUR CHOICE
        startTimer(); 
        // CODE BLOCK
        endTimer(); 
        totalTime = (endTime - startTime);
    }

    public void reverseDegreeOrdering() { // YOUR CHOICE
        startTimer(); 
        // CODE BLOCK
        endTimer(); 
        totalTime = (endTime - startTime);
    }

    public void minimumDegreeOrdering() { // YOUR CHOICE + EXTRA CREDIT
        startTimer(); 
        // CODE BLOCK
        endTimer(); 
        totalTime = (endTime - startTime);
    }

    /*
     * HELPER FUNCTIONS
     */

    public static void parseInput(String fileName){
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            vertices = Integer.parseInt(scanner.nextLine());
            edges = Integer.parseInt(scanner.nextLine());
            graphType = scanner.nextLine().toUpperCase();
            distroType = scanner.nextLine().toUpperCase();

            parseGraphData(scanner);

            scanner.close();
        } catch (Exception e) {
            System.out.println("Error trying to parse '" + fileName + "':");
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static void parseGraphData(Scanner scanner){
        adjList = new AdjList(vertices);
        // Read in each line and add edges to adjList
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] inputs = line.split(" ");

            // If this vertex has any edges (line has more than just the source vertex in it)
            // or line is not whitespace
            if (inputs.length > 1){
                int source = Integer.parseInt(inputs[0]);
                for(int i = 1; i < inputs.length; i++){
                    int destination = Integer.parseInt(inputs[i]);
                    // Add edge if not already present in adjList
                    if (!adjList.containsEdge(source, destination)){
                        System.out.println("Adding edge: {" + source + ", " + destination + "}");
                        adjList.addEdge(source, destination);
                    }
                }
            }
        }
    }

    public long startTimer() { 
        startTime = System.currentTimeMillis(); 
        return startTime; 
    } 
    
    public long endTimer() { 
        endTime = System.currentTimeMillis(); 
        return endTime; 
    }   

}