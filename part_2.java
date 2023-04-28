/* 
Author: Bryan Putnam
ID: 49235478
Course: CS 7350

INFO: 

Contains code for Part II of project
*/

import java.io.File;
import java.io.PrintWriter;
import java.lang.System;
import java.util.Arrays;
import java.util.Scanner; 

public class part_2 {
    private static int vertices; 
    private static int edges;
    private static String graphType;
    private static String distroType;
    private static String orderingName;
    private static long startTime; 
    private static long endTime; 
    private static long totalTime; 
    private static int[] order; 
    private static int[] colors;
    private static double avgDegree;

    private static AdjList adjList;
    
    public static void main(String[] args) {
        parseInput(args[0]);
        orderVertices();
        color();
        outputData();
    }

    public static void color(){
        colors = new int[vertices];
        Arrays.fill(colors, -1);
        Boolean[] availableColors = new Boolean[vertices];
        Arrays.fill(availableColors, true);

        for(int i : order){
            AdjNode destinationNode = adjList.getNodeList()[i];
            while (destinationNode != null){
                if(colors[destinationNode.getVertex()] != -1) { // color has been assigned
                    availableColors[colors[destinationNode.getVertex()]] = false; // color at this connected vertex is not available for use
                }
                destinationNode = destinationNode.getNextPtr();
            }

            // iterate to find smallest available color
            int currColor = 0;
            while(!availableColors[currColor] && currColor < vertices){
                currColor++;
            }
            //Set vertex to available color
            colors[i] = currColor;
            
            // Set all colors back to available for next vertex coloring iteration
            Arrays.fill(availableColors, true);
        }
    }

    public static void smallestLastOrdering() { // REQUIRED
        orderingName = "SMALLEST_LAST";
        order = new int[vertices]; 

        // TODO
        
    }

    public static void smallestOriginalOrdering() { // REQUIRED
        orderingName = "SMALLEST_ORIGINAL";
        // TODO
        
    }

    public static void uniformRandomOrdering() { // REQUIRED
        orderingName = "UNIFORM_RANDOM";
        // TODO
    }

    public static void largestLastOrdering() { // YOUR CHOICE
        orderingName = "LARGEST_LAST";
        int[] degreeListCopy = Arrays.copyOf(adjList.getDegreeList(), vertices);
        order = new int[vertices];

        for(int i = 0; i < vertices; i++){
            int minDegree = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < vertices; j++){
                if (degreeListCopy[j] != -1 && degreeListCopy[j] <= minDegree){
                    minDegree = degreeListCopy[j];
                    minIndex = j;
                }
            }
            order[i] = minIndex;
            deleteDegree(degreeListCopy, minIndex);
        }
        System.out.println(Arrays.toString(order));
    }

    public static void largestOriginalOrdering() { // YOUR CHOICE
        orderingName = "LARGEST_ORIGINAL";
        int[] degreeListCopy = Arrays.copyOf(adjList.getDegreeList(), vertices);
        order = new int[vertices];

        for(int i = 0; i < vertices; i++){
            int minDegree = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < vertices; j++){
                if (degreeListCopy[j] != -1 && degreeListCopy[j] <= minDegree){
                    minDegree = degreeListCopy[j];
                    minIndex = j;
                }
            }
            order[i] = minIndex;
            degreeListCopy[minIndex] = -1; // mark as ordered
        }
        System.out.println(Arrays.toString(order));
        
    }

    public static void outsideToInsideOrdering() { // YOUR CHOICE + EXTRA CREDIT
        orderingName = "OUTSIDE_TO_INSIDE";
        int low = 0;
        int high = vertices -1;
        int index = 0;
        order = new int[vertices];

        while(low <= high){
            if (index % 2 == 0) {
                order[index] = low;
                low ++;
            }
            else {
                order[index] = high;
                high--;
            }
            index++;
        }
        System.out.println(Arrays.toString(order));

    }

    /*
     * HELPER FUNCTIONS
     */

    public static void deleteDegree(int[] degreeListCopy, int source) { 
        degreeListCopy[source] = -1;
        AdjNode destinationNode = adjList.getNodeList()[source];
        System.out.println("Source: " + source);
        // decrease degree for connected vertices
        while(destinationNode != null && degreeListCopy[destinationNode.getVertex()] != -1){
            degreeListCopy[destinationNode.getVertex()] -= 1;
            destinationNode = destinationNode.getNextPtr();
        }
    }

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
        avgDegree = Arrays.stream(adjList.getDegreeList()).average().orElse(Double.NaN);
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
                        adjList.addEdge(source, destination);
                    }
                }
            }
        }
    }

    public static void orderVertices(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1) Smallest Last\n2) Smallest Original\n3) Uniform Random\n4) Largest Last\n5) Largest Original\n6) Towards Middle");
        System.out.print("\nChoose an Ordering 1-6: ");
        String ordering = scanner.nextLine();
        scanner.close();
        selectOrder(ordering);
    }

    public static void selectOrder(String ordering){
        switch(Integer.parseInt(ordering)){
            case 1:
                smallestLastOrdering();
                break;
            case 2:
                smallestOriginalOrdering();
                break;
            case 3:
                uniformRandomOrdering();
                break;
            case 4:
                largestLastOrdering(); 
                break;
            case 5:
                largestOriginalOrdering();
                break;
            case 6:
                outsideToInsideOrdering();
                break;
            default:
                System.out.println("'" + ordering + "' is not a valid option.");
                orderVertices();
                break;
        }
    }

    // TODO: timing
    public static long startTimer() { 
        startTime = System.currentTimeMillis(); 
        return startTime; 
    } 
    
    public static long endTimer() { 
        endTime = System.currentTimeMillis(); 
        return endTime; 
    }   

    public static void outputData(){
        try{
            // Coloring & Summary Output File
            String outputFileName = getOutputFileName(false);
            File file = new File(outputFileName);
            PrintWriter fileWriter = new PrintWriter(file);

            printColoring(fileWriter);
            fileWriter.println("\nTotal Number of Colors Used: "); // TODO
            fileWriter.println("Average Original Degree: " + avgDegree);
            if (orderingName.equals("SMALLEST_LAST")){
                fileWriter.println("Maximum 'Degree when Deleted' Value (Smallest Last Ordering): "); // TODO
                fileWriter.println("Size of Terminal Clique (Smallest Last Ordering): "); // TODO
            }

            fileWriter.close();

            // Coloring Only Output File
            outputFileName = getOutputFileName(true);
            file = new File(outputFileName);
            fileWriter = new PrintWriter(file);

            printColoring(fileWriter);

            fileWriter.close();

        } catch (Exception e){
            System.out.println("Error creating output file.");
            System.out.println(e.getMessage());
        }
    }

    public static String getOutputFileName(Boolean colorOnly){
        if (colorOnly){
            return orderingName + "_" + vertices + "_" + "coloring_only_output.txt";
        }
        return orderingName + "_" + vertices + "_" + "output.txt";
    }

    public static void printColoring(PrintWriter write){
        write.println("Format: (Vertex, Color, Original Degree, Degree When Deleted - If Applicable)");
        for(int i : order){
            if (orderingName.equals("SMALLEST_LAST")){
                 write.println(String.format("(%d, %d, %d, %d)", i, colors[i], adjList.getDegreeList()[i], 0)); // TODO: degree when deleted
            }
            else {
                write.println(String.format("(%d, %d, %d)", i, colors[i], adjList.getDegreeList()[i]));
            }
        }
    }

}