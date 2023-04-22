/* 
Author: Bryan Putnam
ID: 49235478
Course: CS 7350
*/ 

import java.lang.Math; 
import java.util.Random;
import java.util.stream.IntStream;

public class Main {

    public static void main(String args[]) { // Input in this order: Vertices, Edges, Graph Type, Distro Type
        int vertices = Integer.parseInt(args[0]); 
        int edges = Integer.parseInt(args[1]); 
        int graphType = Integer.parseInt(args[2]); // 1=Complete | 2=Cycle  | 3=Random 
        int distroType = Integer.parseInt(args[3]); // 1=Uniform | 2=Skewed | 3=Normal

        Random skewRand = new Random(); 
        Random normalRand = new Random(); 

        getDistro(distroType, vertices, skewRand, normalRand); 
    }

    public static void getGraph(int graphType) { 
        switch(graphType) { 
            case 1: 
                //Complete
                break; 
            case 2: 
                //Cycle
                break; 
            case 3:
                //Random
                break;  
            default: 
                System.out.println("No such graph exists"); 
        }
    }

    public static void getDistro(int distroType, int vertices, Random skewRand, Random normalRandom) { 
        switch(distroType) { 
            case 1: 
                uniformInteger(vertices); // Returns the random Int
                break; 
            case 2: 
                skewedDouble(vertices, skewRand); // Returns the random Double
                break; 
            case 3:
                normalInteger(vertices, normalRandom); //Returns the random Int
                break;  
            default: 
                System.out.println("No such distrobution exists");
        }
    } 

    public static int uniformInteger(int vertices) { 
        int uniRand = (int)(Math.random() * vertices) + 1;
        return uniRand; 
    }

    public static int skewedDouble(int vertices, Random skewRand) { 
        double max = (double)(vertices); 
        double min = 1; 
        double bias = 5; 
        double skew = 5; 
        double range = max - min; 
        double mid = min + range / 2.0; 
        double unitGaussian = skewRand.nextGaussian(); 
        double biasFactor = Math.exp(bias); 
        double skewNum = mid + (range * (biasFactor/(biasFactor + Math.exp(-unitGaussian/skew))-0.5)); //StackOverflow //WHAT DIS MEAN?????
        return (int)(skewNum); 

    }

    public static int normalInteger(int vertices, Random normalRandom) { 
        int sum = IntStream.rangeClosed(1, vertices).sum();
        double mean = (sum / vertices); // mean of range
        double variance = IntStream.rangeClosed(1, vertices).mapToDouble(i -> Math.pow(i - mean, 2)).average().orElse(Double.NaN); //ChatGPT variance calculation //WHAT DIS MEAN x2
        double stdDev = Math.sqrt(variance); 
        double num = normalRandom.nextGaussian() * stdDev + mean; 
        return (int)(num); 
    }
}