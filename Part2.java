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

public class Part2 {
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
    private static int[] degreeOnDelete;
    private static double avgDegree;
    private static int maxDegreeWhenDeleted; 
    private static int maxColors; 
    private static int cliqueSize; 

    private static AdjList adjList;

    public static String run(String input) {
        parseInput(input);
        startTimer(); 
        orderVertices();
        endTimer(); 
        color();
        totalTime = (endTime - startTime); 
        return outputData();
    }

    public static void color(){
        System.out.println("Coloring graph...");
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
        System.out.println("Graph coloring done.");
    }

    public static void smallestLastOrdering() { // REQUIRED
        orderingName = "SMALLEST_LAST";
        order = new int[vertices]; 
        degreeOnDelete = new int[vertices];
        cliqueSize = vertices; 
        int numNotDeleted = vertices; 
        avgDegree = Arrays.stream(adjList.getDegreeList()).average().orElse(Double.NaN);
        maxDegreeWhenDeleted = -1; 
        int[] degreeListCopy = Arrays.copyOf(adjList.getDegreeList(), vertices);

        for(int i = 0; i < vertices; i++) { 
            if(numNotDeleted < cliqueSize && edges == (numNotDeleted * (numNotDeleted - 1)) / 2) { // is complete graph
                cliqueSize = numNotDeleted; 
            }

            int maxVertex = -1; 
            int max = -1;
            for(int j = 0; j < vertices; j++) { 
                if(degreeListCopy[j] != -1 && degreeListCopy[j] > max) { 
                    max = degreeListCopy[j]; 
                    maxVertex = j; 
                }
            }
            if(max > maxDegreeWhenDeleted) { //keep track of max degree when deleted 
                maxDegreeWhenDeleted = max; 
            }

            order[i] = maxVertex; 
            edges -=1; 
            numNotDeleted -=1; 
            degreeOnDelete[maxVertex] = degreeListCopy[maxVertex];
            deleteDegree(degreeListCopy, maxVertex); 
        }
    }

    public static void smallestOriginalLastOrdering() { // REQUIRED
        orderingName = "SMALLEST_ORIGINAL_LAST";
        order = new int[vertices]; 
        avgDegree = Arrays.stream(adjList.getDegreeList()).average().orElse(Double.NaN);
        int[] degreeListCopy = Arrays.copyOf(adjList.getDegreeList(), vertices);

        for(int i = 0; i < vertices; i++) { 
            int max = -1;
            int maxVertex = -1; 
            for(int j = 0; j < vertices; j++) { 
                if(degreeListCopy[j] != -1 && degreeListCopy[j] > max) { 
                    max = degreeListCopy[j]; 
                    maxVertex = j; 
                }
            }
            order[i] = maxVertex;  
            degreeListCopy[maxVertex] = -1; // mark as ordered    
        }
    }

    public static void uniformRandomOrdering() { // REQUIRED
        orderingName = "UNIFORM_RANDOM";
        order = new int[vertices]; 
        Arrays.fill(order, -1); 
        for(int i = 0; i < vertices; i++) { 
            int index = getUniformRandom();
            while(order[index] != -1) { 
                index = getUniformRandom(); 
            }
            order[index] = i; 
        }
    }

    public static void largestLastOrdering() { // YOUR CHOICE
        orderingName = "LARGEST_LAST";
        int[] degreeListCopy = Arrays.copyOf(adjList.getDegreeList(), vertices);
        order = new int[vertices];

        for(int i = 0; i < vertices; i++){
            int minDegree = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < vertices; j++){
                if (degreeListCopy[j] != -1 && degreeListCopy[j] < minDegree){
                    minDegree = degreeListCopy[j];
                    minIndex = j;
                }
            }
            order[i] = minIndex;
            deleteDegree(degreeListCopy, minIndex);
        }
    }

    public static void largestOriginalLastOrdering() { // YOUR CHOICE
        orderingName = "LARGEST_ORIGINAL_LAST";
        int[] degreeListCopy = Arrays.copyOf(adjList.getDegreeList(), vertices);
        order = new int[vertices];

        for(int i = 0; i < vertices; i++){
            int minDegree = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < vertices; j++){
                if (degreeListCopy[j] != -1 && degreeListCopy[j] < minDegree){
                    minDegree = degreeListCopy[j];
                    minIndex = j;
                }
            }
            order[i] = minIndex;
            degreeListCopy[minIndex] = -1; // mark as ordered
        }
        
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

    }

    /*
     * HELPER FUNCTIONS
     */

     public static int getUniformRandom() {
        int uniRand = (int) (Math.random() * vertices); // 0 - vertices-1
        return uniRand;
    }

    public static void deleteDegree(int[] degreeListCopy, int source) { 
        degreeListCopy[source] = -1;
        AdjNode destinationNode = adjList.getNodeList()[source];
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
        System.out.println("1) Smallest Last\n2) Smallest Original Last\n3) Uniform Random\n4) Largest Last\n5) Largest Original Last\n6) Outside to Inside");
        System.out.print("\nChoose an Ordering 1-6: ");
        String ordering = scanner.nextLine();
        scanner.close();
        System.out.println("Ordering vertices...");
        selectOrder(ordering);
        System.out.println("Ordering done.");
    }

    public static void selectOrder(String ordering){
        switch(Integer.parseInt(ordering)){
            case 1:
                smallestLastOrdering();
                break;
            case 2:
                smallestOriginalLastOrdering();
                break;
            case 3:
                uniformRandomOrdering();
                break;
            case 4:
                largestLastOrdering(); 
                break;
            case 5:
                largestOriginalLastOrdering();
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

    public static long startTimer() { 
        startTime = System.currentTimeMillis(); 
        return startTime; 
    } 
    
    public static long endTimer() { 
        endTime = System.currentTimeMillis(); 
        return endTime; 
    }   

    public static String outputData(){
        try{
            // Coloring & Summary Output File
            String outputFileName = getOutputFileName(false);
            String bothFileNames = outputFileName;
            File file = new File(outputFileName);
            PrintWriter fileWriter = new PrintWriter(file);

            printColoring(fileWriter);
            fileWriter.println("\nTotal Number of Colors Used: " + (Arrays.stream(colors).max().orElse(-1) + 1));
            fileWriter.println("Average Original Degree: " + avgDegree);
            fileWriter.println("Total time taken: " + totalTime);
            if (orderingName.equals("SMALLEST_LAST")){
                fileWriter.println("Maximum 'Degree when Deleted' Value (Smallest Last Ordering): " + maxDegreeWhenDeleted);
                fileWriter.println("Size of Terminal Clique (Smallest Last Ordering): " + cliqueSize); 
            }

            fileWriter.close();

            // Coloring Only Output File
            outputFileName = getOutputFileName(true);
            bothFileNames += " & " + outputFileName;
            file = new File(outputFileName);
            fileWriter = new PrintWriter(file);

            printColoring(fileWriter);

            fileWriter.close();

            return bothFileNames;

        } catch (Exception e){
            System.out.println("Error creating output file.");
            System.out.println(e.getMessage());
        }
        return "";
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
                 write.println(String.format("(%d, %d, %d, %d)", i, colors[i], adjList.getDegreeList()[i], degreeOnDelete[i]));
            }
            else {
                write.println(String.format("(%d, %d, %d)", i, colors[i], adjList.getDegreeList()[i]));
            }
        }
    }
}