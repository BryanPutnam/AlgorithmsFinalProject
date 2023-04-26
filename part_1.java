/* 
Author: Bryan Putnam
ID: 49235478
Course: CS 7350

INFO: 

Contains code for Part I of project
*/ 

import java.lang.Math; 
import java.util.Random;
import java.util.stream.IntStream;

public class part_1 {

    static int vertices;
    static int edges; 
    static int graphType; 
    static int distroType; 

    public static void main(String args[]) { // Input in this order: Vertices, Edges, Graph Type, Distro Type
        vertices = Integer.parseInt(args[0]); 
        edges = Integer.parseInt(args[1]); 
        graphType = Integer.parseInt(args[2]); // 1=Complete | 2=Cycle  | 3=Random 
        distroType = Integer.parseInt(args[3]); // 1=Uniform | 2=Skewed | 3=Normal  

        getDistro(); 
    }

    /*
        GETTER METHODS (getGraph, getDistro)
    */

    public static void getGraph() { 
        switch(graphType) { 
            case 1: 
                completeGraph();
                break; 
            case 2: 
                cycleGraph();
                break; 
            case 3:
                randomGraph();
                break;  
            default: 
                System.out.println("No such graph exists"); 
        }
    }

    public static void getDistro() { 
        switch(distroType) { 
            case 1: 
            uniformDistro(); // Returns the random Int
                break; 
            case 2: 
            skewedDistro(); // Returns the random Int
                break; 
            case 3:
            normalDistro(); //Returns the random Int
                break;  
            default: 
                System.out.println("No such distrobution exists");
        }
    } 

    /*
        DISTRIBUTIONS CODE (Uniform, Skewed, Normal)
    */

    public static int uniformDistro() { 
        int uniRand = (int)(Math.random() * vertices) + 1;
        return uniRand; 
    }

    public static int skewedDistro() {  
        double skew = 2; 
        double unscaledSkewNum = Math.pow(Math.random(), skew);
        double skewNum = unscaledSkewNum * (vertices - 1) + 1; // scale from 1 to vertices
        return (int)(skewNum);
    }

    public static int normalDistro() {

        double num1 = Math.random(); 
        double num2 = Math.random(); 
        double mean = normMean(); 
        double variance = normVariance(mean); 
        double stdDev = normStddev(mean, variance); 

        double rand = Math.sqrt(-2.0 * Math.log(num1)) * Math.sin(2.0 * Math.PI * num2);
        double unscaledNormNum = mean + stdDev * rand; 

        double min1 = mean - (stdDev * 4); 
        double max1 = mean + (stdDev * 4); 
        double min2 = 1; 
        double max2 = vertices; 

        double normNum = ((unscaledNormNum - min1) / (max1 - min1)) * (max2 - min2) + min2; 
        
        return (int)(normNum); 
    }

    public static double normMean() { 
        int sum = IntStream.rangeClosed(1, vertices).sum();
        double mean = (sum / vertices); // mean of range
        return mean; 
    }

    public static double normVariance(double mean) { 
        double variance = IntStream.rangeClosed(1, vertices).mapToDouble(i -> Math.pow(i - mean, 2)).average().orElse(Double.NaN); //ChatGPT variance calculation
        return variance; 
    }

    public static double normStddev(double mean, double variance) { 
        double stdDev = Math.sqrt(variance); 
        return stdDev; 
    }

    /*
        GRAPHS CODE (Cycle, Complete, Random)
    */

    public static void completeGraph() { 

    }

    public static void cycleGraph() { 

    }

    public static void randomGraph() { 

    }
}