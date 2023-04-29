import java.util.Scanner;

public class GraphColoring {
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);

        System.out.println("----------- Part 1 ----------");
        System.out.print("Name of Input File: ");
        String inputFile = scanner.nextLine();

        System.out.println("Part 1: Creating graph...");
        String outputFile = Part1.run(inputFile);
        System.out.println("Graph created. Output file: " + outputFile);
        
        System.out.println("\n----------- Part 2 ----------");
        outputFile = Part2.run(outputFile);
        System.out.println("Coloring data output files: " + outputFile);
    }
}