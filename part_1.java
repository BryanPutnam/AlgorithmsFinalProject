/* 
Author: Bryan Putnam
ID: 49235478
Course: CS 7350

INFO: 

Contains code for Part I of project
*/

import java.io.File;
import java.io.PrintWriter;
import java.lang.Math;
import java.util.Scanner;
import java.util.stream.IntStream;

public class part_1 {

    static int vertices;
    static int edges;
    static String graphType;
    static String distroType;
    static AdjList adj_list;

    public static void main(String args[]) {
        //parseInput(args[0]);
        parseInput("input.txt");
        createGraph();
        outputGraph();
    }

    /*
     * GRAPHS CODE (Cycle, Complete, Random)
     */

    public static AdjList completeGraph() {
        adj_list = new AdjList(vertices);

        for (int i = 0; i < vertices; i++) {
            for (int j = i + 1; j < vertices; j++) {
                if (i != j) {
                    adj_list.addEdge(i, j);
                }
            }
        }
        return adj_list;
    }

    public static AdjList cycleGraph() {
        adj_list = new AdjList(vertices);

        for (int i = 0; i < vertices - 1; i++) {
            adj_list.addEdge(i, i + 1);
        }
        adj_list.addEdge(0, vertices - 1);

        return adj_list;
    }

    public static void randomGraph() {
        adj_list = new AdjList(vertices);
        if (tooManyEdges()) {
            System.out.println("Too many edges!");
        } else {
            createRandomGraph();
        }
    }

    public static void uniformDistro() {
        for (int i = 0; i < edges; i++) {
            int first = getUniformRandom();
            int second = getUniformRandom();
            while (first == second || adj_list.containsEdge(first, second)) {
                first = getUniformRandom(); 
                second = getUniformRandom();
            }
            adj_list.addEdge(first, second);
        }
    }

    public static void skewedDistro() {
        for (int i = 0; i < edges; i++) {
            int first = getSkewedRandom();
            int second = getSkewedRandom();
            while (first == second || adj_list.containsEdge(first, second)) {
                first = getSkewedRandom(); 
                second = getSkewedRandom();
            }
            adj_list.addEdge(first, second);
        }
    }

    public static void normalDistro() {
        for (int i = 0; i < edges; i++) {
            int first = getNormalRandom();
            int second = getNormalRandom();
            while (first == second || adj_list.containsEdge(first, second)) {
                first = getNormalRandom();
                second = getNormalRandom();
            }
            adj_list.addEdge(first, second);
        }
    }

    /*
     * RANDOM NUMBER DISTRIBUTIONS CODE (Uniform, Skewed, Normal)
     */

    public static int getUniformRandom() {
        int uniRand = (int) (Math.random() * vertices); // 0 - vertices-1
        return uniRand;
    }

    public static int getSkewedRandom() {
        double skew = 2;
        double unscaledSkewNum = Math.pow(Math.random(), skew);
        double skewNum = unscaledSkewNum * vertices; // scale from [0, vertices-1]
        return (int) (skewNum);
    }

    public static int getNormalRandom() {
        double num1 = Math.random();
        double num2 = Math.random();
        double mean = normMean();
        double variance = normVariance(mean);
        double stdDev = normStddev(mean, variance);

        double rand = Math.sqrt(-2.0 * Math.log(num1)) * Math.sin(2.0 * Math.PI * num2);
        double unscaledNormNum = mean + stdDev * rand;

        double min = mean - (stdDev * 4);
        double max1 = mean + (stdDev * 4);
        double max2 = vertices - 1;

        double normNum = ((unscaledNormNum - min) / (max1 - min)) * (max2); // [0, vertices)

        return (int) (normNum);
    }

    public static double normMean() {
        int sum = IntStream.rangeClosed(1, vertices).sum();
        double mean = (sum / vertices); // mean of range
        return mean;
    }

    public static double normVariance(double mean) {
        double variance = IntStream.rangeClosed(1, vertices).mapToDouble(i -> Math.pow(i - mean, 2)).average()
                .orElse(Double.NaN); // ChatGPT variance calculation
        return variance;
    }

    public static double normStddev(double mean, double variance) {
        double stdDev = Math.sqrt(variance);
        return stdDev;
    }

    /*
     * HELPER FUNCTIONS
     */

    public static void parseInput(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            vertices = Integer.parseInt(scanner.nextLine());
            edges = Integer.parseInt(scanner.nextLine());
            graphType = scanner.nextLine().toUpperCase();
            distroType = scanner.nextLine().toUpperCase();

            scanner.close();
        } catch (Exception e) {
            System.out.println("Error trying to parse '" + fileName + "':");
            System.out.println(e.getMessage());
        }
    }

    public static void createGraph() {
        switch (graphType) {
            case "COMPLETE":
                completeGraph();
                break;
            case "CYCLE":
                cycleGraph();
                break;
            case "RANDOM":
                randomGraph();
                break;
            default:
                System.out.println("Not a valid graph type (COMPLETE | CYCLE | RANDOM)");
        }
    }

    public static void createRandomGraph() {
        switch (distroType) {
            case "UNIFORM":
                uniformDistro();
                break;
            case "SKEWED":
                skewedDistro();
                break;
            case "YOURS":
            case "NORMAL":
                normalDistro();
                break;
            default:
                System.out.println("Not a valid distribution type (UNIFORM | SKEWED | NORMAL/YOURS)");
        }
    }

    public static Boolean tooManyEdges() {
        // returns true if edges is over the number of edges for a complete graph (all vertices connected together)
        return edges > (vertices * (vertices - 1) / 2);
    }

    public static void outputGraph() {
        try {
            String outputFileName = getOutputFileName();
            File file = new File(outputFileName);
            PrintWriter fileWriter = new PrintWriter(file);

            // Number of Vertices
            fileWriter.println(vertices);
            // Number of Edges
            fileWriter.println(adj_list.getEdges());
            // Graph Type
            fileWriter.println(graphType);
            // Distribution Type
            fileWriter.println(distroType);
            // Adj List
            fileWriter.println(adj_list.toString());

            fileWriter.close();

        } catch (Exception e) {
            System.out.println("Error creating output file.");
            System.out.println(e.getMessage());
        }
    }

    public static String getOutputFileName() {
        switch (graphType) {
            case "COMPLETE":
            case "CYCLE":
                return String.format("%s_%d_output.txt", graphType, vertices); // e.g: COMPLETE_100_output.txt
            case "RANDOM":
                return String.format("%s_%s_%d_output.txt", graphType, distroType, vertices); // e.g
                                                                                              // RANDOM_UNIFORM_100_output.txt
            default:
                System.out.println("this should not happen");
                return null;
        }
    }
}