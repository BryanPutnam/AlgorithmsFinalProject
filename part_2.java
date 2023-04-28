/* 
Author: Bryan Putnam
ID: 49235478
Course: CS 7350

INFO: 

Contains code for Part II of project
*/

import java.lang.System; 

public class part_2 {
    int vertices; 
    int degree; 
    long startTime; 
    long endTime; 
    long totalTime; 
    
    public part_2() { 

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

    public long startTimer() { 
        startTime = System.currentTimeMillis(); 
        return startTime; 
    } 
    
    public long endTimer() { 
        endTime = System.currentTimeMillis(); 
        return endTime; 
    }   

}